/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.DomainSpecifications;
import org.seedstack.business.internal.assembler.dsl.resolver.DtoInfoResolver;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;
import org.seedstack.business.internal.assembler.dsl.resolver.impl.AnnotationResolver;
import org.seedstack.business.internal.utils.BusinessReflectUtils;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.seed.core.utils.SeedCheckUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class BaseAggAssemblerWithRepoProviderImpl<A extends AggregateRoot<?>> {

    protected final DtoInfoResolver dtoInfoResolver = new AnnotationResolver();
    protected final AssemblerDslContext context;

    /**
     * Constructor.
     *
     * @param context contains the internal registry used to get domain objects
     */
    public BaseAggAssemblerWithRepoProviderImpl(AssemblerDslContext context) {
        this.context = context;
    }

    protected Object resolveId(Object dto, Class<? extends AggregateRoot<?>> aggregateRootClass) {
        SeedCheckUtils.checkIfNotNull(dto);
        SeedCheckUtils.checkIfNotNull(aggregateRootClass);

        ParameterHolder parameterHolder = dtoInfoResolver.resolveId(dto);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        return paramsToIds(aggregateRootClass, parameterHolder, -1);
    }

    protected Tuple resolveIds(Object dto, List<Class<? extends AggregateRoot<?>>> aggregateRootClasses) {
        SeedCheckUtils.checkIfNotNull(dto);
        SeedCheckUtils.checkIfNotNull(aggregateRootClasses);

        ParameterHolder parameterHolder = dtoInfoResolver.resolveId(dto);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        List<Object> ids = new ArrayList<Object>();
        int aggregateIndex = 0;
        for (Object aggregateRootClass : aggregateRootClasses) {
            //noinspection unchecked
            ids.add(paramsToIds((Class<? extends AggregateRoot<?>>) aggregateRootClass, parameterHolder, aggregateIndex));
            aggregateIndex++;
        }

        return Tuples.create(ids);
    }

    private Object paramsToIds(Class<? extends AggregateRoot<?>> aggregateRootClass, ParameterHolder parameterHolder, int aggregateIndex) {
        Object id;

        //noinspection unchecked
        Class<? extends DomainObject> aggregateIdClass = (Class<? extends DomainObject>) BusinessReflectUtils.getAggregateIdClass(aggregateRootClass);

        Object element = parameterHolder.uniqueElementForAggregateRoot(aggregateIndex);
        if (element != null && aggregateIdClass.isAssignableFrom(element.getClass())) {
            // The first parameter is already the id we are looking for
            id = element;
        } else {
            if (!DomainSpecifications.VALUE_OBJECT.isSatisfiedBy(aggregateIdClass)) {
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

    protected A fromFactory(Class<? extends AggregateRoot<?>> aggregateClass, Object dto) {
        GenericFactory<A> genericFactory = (GenericFactory<A>) context.genericFactoryOf(aggregateClass);
        ParameterHolder parameterHolder = dtoInfoResolver.resolveAggregate(dto);
        A aggregateRoot = (A) getAggregateFromFactory(genericFactory, dto.getClass(), aggregateClass, parameterHolder.parameters());
        return assembleWithDto(aggregateRoot, dto);
    }

    /**
     * Assemble one aggregate root from a dto.
     *
     * @param aggregateRoots the aggregate root to assemble
     * @return the assembled aggregate root
     */
    protected A assembleWithDto(A aggregateRoots, Object dto) {
        Assembler assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.getClass(), dto.getClass());
        //noinspection unchecked
        assembler.mergeAggregateWithDto(aggregateRoots, dto);
        return aggregateRoots;
    }

    protected Object getAggregateFromFactory(GenericFactory<?> factory, Class<?> dtoClass, Class<? extends AggregateRoot<?>> aggregateClass, Object[] parameters) {
        SeedCheckUtils.checkIfNotNull(factory);
        SeedCheckUtils.checkIfNotNull(aggregateClass);
        SeedCheckUtils.checkIfNotNull(parameters);

        if (Factory.class.isAssignableFrom(factory.getClass())) {
            Factory<?> defaultFactory = (Factory<?>) factory;
            if (parameters.length == 0) {
                return defaultFactory.create();
            } else {
                return defaultFactory.create(parameters);
            }
        } else {
            // Find the method in the factory which match the signature determined with the previously extracted parameters
            Method factoryMethod = MethodMatcher.findMatchingMethod(factory.getClass(), aggregateClass, parameters);
            if (factoryMethod == null) {
                throw new IllegalStateException(factory.getClass().getSimpleName() +
                        " - Enable to find a method matching the parameters " +
                        Arrays.toString(parameters));
            }

            // Invoke the factory to create the aggregate root
            try {
                //noinspection unchecked
                if (parameters.length == 0) {
                    return factoryMethod.invoke(factory);
                } else {
                    return factoryMethod.invoke(factory, parameters);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e.getCause() != null ? e.getCause() : e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e.getCause() != null ? e.getCause() : e);
            }
        }
    }
}
