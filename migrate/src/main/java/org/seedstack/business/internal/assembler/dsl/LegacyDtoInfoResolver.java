/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import static org.seedstack.shed.reflect.ReflectUtils.makeAccessible;

import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Priority;
import javax.inject.Inject;
import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.spi.BaseDtoInfoResolver;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.business.spi.DtoInfoResolverPriority;
import org.seedstack.shed.cache.Cache;
import org.seedstack.shed.cache.CacheParameters;
import org.seedstack.shed.reflect.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link DtoInfoResolver} based on the {@link MatchingEntityId} and
 * {@link MatchingFactoryParameter} annotation.
 *
 * @see MatchingEntityId
 * @see MatchingFactoryParameter
 */
@Priority(DtoInfoResolverPriority.MATCHING_ANNOTATIONS)
class LegacyDtoInfoResolver extends BaseDtoInfoResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyDtoInfoResolver.class);
    private static final Class<? extends Annotation> MATCHING_ENTITY_ID = MatchingEntityId.class;
    private static final Class<? extends Annotation> MATCHING_FACT_PARAM = MatchingFactoryParameter.class;
    private static final Cache<Class<?>, DtoInfo<?>> dtoInfo = Cache.create(
            new CacheParameters<Class<?>, DtoInfo<?>>()
                    .setInitialSize(256)
                    .setMaxSize(1024)
                    .setLoadingFunction(LegacyDtoInfoResolver::resolveDtoInfo)
    );

    @Inject
    LegacyDtoInfoResolver(DomainRegistry domainRegistry) {
        super(domainRegistry);
    }

    private static <D> DtoInfo<D> resolveDtoInfo(Class<D> dtoClass) {
        final ParameterHolder<D> idParameterHolder = new ParameterHolder<>((Class<D>) dtoClass);
        final ParameterHolder<D> aggregateParameterHolder = new ParameterHolder<>((Class<D>) dtoClass);
        final AtomicBoolean supported = new AtomicBoolean(false);

        LOGGER.debug("Resolving DTO information on {}", dtoClass);

        Classes.from(dtoClass)
                .traversingSuperclasses()
                .methods()
                .forEach(method -> {
                    makeAccessible(method);

                    MatchingEntityId idAnnotation = method.getAnnotation(MatchingEntityId.class);
                    if (idAnnotation != null) {
                        if (idAnnotation.typeIndex() >= 0) {
                            if (idAnnotation.index() >= 0) {
                                idParameterHolder.addTupleParameter(MATCHING_ENTITY_ID,
                                        idAnnotation.typeIndex(), idAnnotation.index(), method);
                            } else {
                                idParameterHolder.addTupleValue(MATCHING_ENTITY_ID, idAnnotation.typeIndex(),
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

                    MatchingFactoryParameter factoryAnnotation = method
                            .getAnnotation(MatchingFactoryParameter.class);
                    if (factoryAnnotation != null) {
                        if (factoryAnnotation.typeIndex() >= 0) {
                            if (factoryAnnotation.index() >= 0) {
                                aggregateParameterHolder.addTupleParameter(MATCHING_FACT_PARAM,
                                        factoryAnnotation.typeIndex(), factoryAnnotation.index(), method);
                            } else {
                                aggregateParameterHolder.addTupleValue(MATCHING_FACT_PARAM,
                                        factoryAnnotation.typeIndex(), method);
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
    }

    @Override
    public <D> boolean supports(D dto) {
        return getDtoInfo(dto).supported;
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

    @Override
    public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass) {
        return createFromFactory(aggregateRootClass, getAggregateParameterHolder(dto).parameters(dto));
    }

    @Override
    public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass, int position) {
        return createFromFactory(aggregateRootClass,
                getAggregateParameterHolder(dto).parametersOfAggregateRoot(dto, position));
    }

    private <D, I> ParameterHolder<D> getIdParameterHolder(D dto, Class<I> aggregateIdClass) {
        ParameterHolder<D> parameterHolder = getDtoInfo(dto).idParameterHolder;
        if (parameterHolder.isEmpty()) {
            throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_CAN_BE_RESOLVED_FROM_DTO)
                    .put("dtoClass", dto.getClass()
                            .getName())
                    .put("aggregateIdClass", aggregateIdClass);
        }
        return parameterHolder;
    }

    private <D> ParameterHolder<D> getAggregateParameterHolder(D dto) {
        return getDtoInfo(dto).aggregateParameterHolder;
    }

    @SuppressWarnings("unchecked")
    private <D> DtoInfo<D> getDtoInfo(D dto) {
        return (DtoInfo<D>) dtoInfo.get(dto.getClass());
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
