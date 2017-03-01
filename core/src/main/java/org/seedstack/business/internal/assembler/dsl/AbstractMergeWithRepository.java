/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainObject;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.internal.Tuples;
import org.seedstack.business.internal.assembler.dsl.resolver.DtoInfoResolver;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;
import org.seedstack.business.internal.assembler.dsl.resolver.impl.AnnotationResolver;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.seed.SeedException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractMergeWithRepository<A extends AggregateRoot<?>> {
    final DtoInfoResolver dtoInfoResolver = new AnnotationResolver();
    protected final AssemblerDslContext context;

    /**
     * Constructor.
     *
     * @param context contains the internal registry used to get domain objects
     */
    AbstractMergeWithRepository(AssemblerDslContext context) {
        this.context = context;
    }

    Object resolveId(Object dto, Class<? extends AggregateRoot<?>> aggregateRootClass) {
        checkNotNull(dto);
        checkNotNull(aggregateRootClass);

        ParameterHolder parameterHolder = dtoInfoResolver.resolveId(dto);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        return paramsToIds(aggregateRootClass, parameterHolder, -1);
    }

    @SuppressWarnings("unchecked")
    Tuple resolveIds(Object dto, List<Class<? extends AggregateRoot<?>>> aggregateRootClasses) {
        checkNotNull(dto);
        checkNotNull(aggregateRootClasses);

        ParameterHolder parameterHolder = dtoInfoResolver.resolveId(dto);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        List<Object> ids = new ArrayList<>();
        int aggregateIndex = 0;
        for (Object aggregateRootClass : aggregateRootClasses) {
            ids.add(paramsToIds((Class<? extends AggregateRoot<?>>) aggregateRootClass, parameterHolder, aggregateIndex));
            aggregateIndex++;
        }

        return Tuples.create(ids);
    }

    private Object paramsToIds(Class<? extends AggregateRoot<?>> aggregateRootClass, ParameterHolder parameterHolder, int aggregateIndex) {
        Object id;
        @SuppressWarnings("unchecked")
        Class<? extends DomainObject> aggregateIdClass = (Class<? extends DomainObject>) BusinessUtils.getAggregateIdClass(aggregateRootClass);

        Object element = parameterHolder.uniqueElementForAggregateRoot(aggregateIndex);
        if (element != null && aggregateIdClass.isAssignableFrom(element.getClass())) {
            // The first parameter is already the id we are looking for
            id = element;
        } else {
            if (!BusinessSpecifications.VALUE_OBJECT.isSatisfiedBy(aggregateIdClass)) {
                throw new IllegalStateException("The " + aggregateRootClass.getCanonicalName() + "'s id is not a value object, so you don't have to specify the index in @MatchingEntityId(index = 0)");
            }
            // Get the "magic" factory for the aggregate id class
            Factory<?> factory = context.defaultFactoryOf(aggregateIdClass);
            // Create the id based on the id constructor matching the given parameters
            // TODO <pith> : check the case when one of the parameters is null
            id = factory.create(parameterHolder.parametersOfAggregateRoot(aggregateIndex));
        }
        if (id == null) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    A fromFactory(Class<? extends AggregateRoot<?>> aggregateClass, Object dto) {
        Factory<A> factory = (Factory<A>) context.genericFactoryOf(aggregateClass);
        ParameterHolder parameterHolder = dtoInfoResolver.resolveAggregate(dto);
        A aggregateRoot = (A) getAggregateFromFactory(factory, aggregateClass, parameterHolder.parameters());
        return assembleWithDto(aggregateRoot, dto);
    }

    /**
     * Assemble one aggregate root from a dto.
     *
     * @param aggregateRoots the aggregate root to assemble
     * @return the assembled aggregate root
     */
    @SuppressWarnings("unchecked")
    A assembleWithDto(A aggregateRoots, Object dto) {
        Assembler assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.getClass(), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoots, dto);
        return aggregateRoots;
    }

    Object getAggregateFromFactory(Factory<?> factory, Class<? extends AggregateRoot<?>> aggregateClass, Object[] parameters) {
        checkNotNull(factory);
        checkNotNull(aggregateClass);
        checkNotNull(parameters);

        // Find the method in the factory which match the signature determined with the previously extracted parameters
        Method factoryMethod;
        boolean useDefaultFactory = false;
        try {
            factoryMethod = MethodMatcher.findMatchingMethod(factory.getClass(), aggregateClass, parameters);
            if (factoryMethod == null) {
                useDefaultFactory = true;
            }
        } catch (Exception e) {
            throw SeedException.wrap(e, BusinessErrorCode.UNABLE_TO_FIND_FACTORY_METHOD)
                    .put("aggregateClass", aggregateClass.getName())
                    .put("parameters", Arrays.toString(parameters));
        }

        // Invoke the factory to create the aggregate root
        try {
            if (useDefaultFactory) {
                return factory.create(parameters);
            } else {
                if (parameters.length == 0) {
                    return factoryMethod.invoke(factory);
                } else {
                    return factoryMethod.invoke(factory, parameters);
                }
            }
        } catch (Exception e) {
            throw SeedException.wrap(e, BusinessErrorCode.UNABLE_TO_INVOKE_FACTORY_METHOD)
                    .put("aggregateClass", aggregateClass.getName())
                    .put("factoryClass", factory.getClass().getName())
                    .put("factoryMethod", Optional.ofNullable(factoryMethod).map(Method::getName).orElse("create"))
                    .put("parameters", Arrays.toString(parameters));
        }
    }
}
