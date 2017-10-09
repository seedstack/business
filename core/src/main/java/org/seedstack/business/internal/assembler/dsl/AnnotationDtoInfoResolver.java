/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import static org.seedstack.shed.reflect.ReflectUtils.makeAccessible;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Priority;
import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.FactoryArgument;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.spi.BaseDtoInfoResolver;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.business.spi.DtoInfoResolverPriority;
import org.seedstack.shed.reflect.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link DtoInfoResolver} based on the {@link AggregateId} and
 * {@link FactoryArgument} annotation.
 *
 * @see AggregateId
 * @see FactoryArgument
 */
@Priority(DtoInfoResolverPriority.MATCHING_ANNOTATIONS)
class AnnotationDtoInfoResolver extends BaseDtoInfoResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDtoInfoResolver.class);
    private static final Class<? extends Annotation> MATCHING_ENTITY_ID = AggregateId.class;
    private static final Class<? extends Annotation> MATCHING_FACT_PARAM = FactoryArgument.class;
    // This unbounded cache of DTO info can only grow up to the number of DTO classes in the system
    private static final ConcurrentMap<Class<?>, DtoInfo<?>> cache = new ConcurrentHashMap<>();

    @Override
    public <D> boolean supports(D dto) {
        return getCachedInfo(dto).supported;
    }

    @Override
    public <D, I> I resolveId(D dto, Class<I> aggregateIdClass) {
        ParameterHolder<D> parameterHolder = getIdParameterHolder(dto, aggregateIdClass);
        return createIdentifier(aggregateIdClass, parameterHolder.uniqueElement(dto),
                parameterHolder.parameters(dto));
    }

    @Override
    public <D, I> I resolveId(D dto, Class<I> aggregateIdClass, int position) {
        ParameterHolder<D> parameterHolder = getIdParameterHolder(dto, aggregateIdClass);
        return createIdentifier(aggregateIdClass, parameterHolder.uniqueElementForAggregate(dto, position),
                parameterHolder.parametersOfAggregateRoot(dto, position));
    }

    private <D, I> ParameterHolder<D> getIdParameterHolder(D dto, Class<I> aggregateIdClass) {
        ParameterHolder<D> parameterHolder = getCachedInfo(dto).idParameterHolder;
        if (parameterHolder.isEmpty()) {
            throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_CAN_BE_RESOLVED_FROM_DTO)
                    .put("dtoClass", dto.getClass()
                            .getName())
                    .put("aggregateIdClass", aggregateIdClass);
        }
        return parameterHolder;
    }

    @Override
    public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass) {
        return createFromFactory(aggregateRootClass, getAggregateParameterHolder(dto).parameters(dto));
    }

    @Override
    public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass, int position) {
        return createFromFactory(aggregateRootClass,
                getAggregateParameterHolder(dto).parametersOfAggregateRoot(dto, position));
    }

    private <D> ParameterHolder<D> getAggregateParameterHolder(D dto) {
        return getCachedInfo(dto).aggregateParameterHolder;
    }

    @SuppressWarnings("unchecked")
    private <D> DtoInfo<D> getCachedInfo(D dto) {
        return (DtoInfo<D>) cache.computeIfAbsent(dto.getClass(), dtoClass -> {
            final ParameterHolder<D> idParameterHolder = new ParameterHolder<>((Class<D>) dtoClass);
            final ParameterHolder<D> aggregateParameterHolder = new ParameterHolder<>((Class<D>) dtoClass);
            final AtomicBoolean supported = new AtomicBoolean(false);

            LOGGER.debug("Resolving DTO information on {}", dtoClass);

            Classes.from(dtoClass)
                    .traversingSuperclasses()
                    .methods()
                    .forEach(method -> {
                        makeAccessible(method);

                        AggregateId idAnnotation = method.getAnnotation(AggregateId.class);
                        if (idAnnotation != null) {
                            if (idAnnotation.aggregateIndex() >= 0) {
                                if (idAnnotation.index() >= 0) {
                                    idParameterHolder.addTupleParameter(MATCHING_ENTITY_ID,
                                            idAnnotation.aggregateIndex(), idAnnotation.index(), method);
                                } else {
                                    idParameterHolder.addTupleValue(MATCHING_ENTITY_ID, idAnnotation.aggregateIndex(),
                                            method);
                                }
                            } else {
                                if (idAnnotation.index() >= 0) {
                                    idParameterHolder.addParameter(MATCHING_ENTITY_ID, idAnnotation.index(), method);
                                } else {
                                    idParameterHolder.addValue(MATCHING_ENTITY_ID, method);
                                }
                            }
                            supported.set(true);
                        }

                        FactoryArgument factoryAnnotation = method.getAnnotation(FactoryArgument.class);
                        if (factoryAnnotation != null) {
                            if (factoryAnnotation.aggregateIndex() >= 0) {
                                if (factoryAnnotation.index() >= 0) {
                                    aggregateParameterHolder.addTupleParameter(MATCHING_FACT_PARAM,
                                            factoryAnnotation.aggregateIndex(), factoryAnnotation.index(), method);
                                } else {
                                    aggregateParameterHolder.addTupleValue(MATCHING_FACT_PARAM,
                                            factoryAnnotation.aggregateIndex(), method);
                                }
                            } else {
                                if (factoryAnnotation.index() >= 0) {
                                    aggregateParameterHolder.addParameter(MATCHING_FACT_PARAM,
                                            factoryAnnotation.index(), method);
                                } else {
                                    aggregateParameterHolder.addValue(MATCHING_FACT_PARAM, method);
                                }
                            }
                            supported.set(true);
                        }
                    });
            if (supported.get()) {
                return new DtoInfo<>(true, idParameterHolder.freeze(), aggregateParameterHolder.freeze());
            } else {
                return new DtoInfo<>(false, null, null);
            }
        });
    }

    private static class DtoInfo<D> {
        final boolean supported;
        final ParameterHolder<D> idParameterHolder;
        final ParameterHolder<D> aggregateParameterHolder;

        private DtoInfo(boolean supported, ParameterHolder<D> idParameterHolder,
                ParameterHolder<D> aggregateParameterHolder) {
            this.supported = supported;
            this.idParameterHolder = idParameterHolder;
            this.aggregateParameterHolder = aggregateParameterHolder;
        }
    }
}
