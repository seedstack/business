/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.resolver.DtoInfoResolver;
import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;
import org.seedstack.business.core.interfaces.assembler.resolver.AnnotationResolver;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.seed.core.utils.SeedCheckUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class BaseAggAssemblerWithRepoProviderImpl {

    protected final DtoInfoResolver dtoInfoResolver = new AnnotationResolver();
    protected final AssemblerContext assemblerContext;
    protected final InternalRegistry registry;

    /**
     * Constructor.
     *
     * @param registry the internal registry used to get domain objects
     * @param assemblerContext the assembler context which share the data given to the DSL.
     */
    public BaseAggAssemblerWithRepoProviderImpl(InternalRegistry registry, AssemblerContext assemblerContext) {
        this.registry = registry;
        this.assemblerContext = assemblerContext;
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

    protected Tuple resolveIds(Object dto, Tuple aggregateRootClasses) {
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
        Class<? extends DomainObject> aggregateIdClass = (Class<? extends DomainObject>) BusinessUtils.getAggregateIdClass(aggregateRootClass);

        Object element = parameterHolder.uniqueElementForAggregateRoot(aggregateIndex);
        if (element != null && aggregateIdClass.isAssignableFrom(element.getClass())) {
            // The first parameter is already the id we are looking for
            id = element;
        } else {
            if (!ValueObject.class.isAssignableFrom(aggregateIdClass)) {
                throw new IllegalStateException("The " + aggregateRootClass.getCanonicalName() + "'s id is not a value object, so you don't have to specify the index in @MatchingEntityId(index = 0)");
            }
            // Get the "magic" factory for the aggregate id class
            Factory<?> factory = registry.defaultFactoryOf(aggregateIdClass);
            // Create the id based on the id constructor matching the given parameters
            // TODO <pith> : check the case when one of the parameters is null
            id = factory.create(parameterHolder.parametersOfAggregateRoot(aggregateIndex));
        }
        if (id == null) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }
        return id;
    }

    protected Object getAggregateFromFactory(GenericFactory<?> factory, Class<? extends AggregateRoot<?>> aggregateClass, Object[] parameters) {
        SeedCheckUtils.checkIfNotNull(factory);
        SeedCheckUtils.checkIfNotNull(aggregateClass);
        SeedCheckUtils.checkIfNotNull(parameters);

        if (parameters.length == 0) {
            throw new IllegalArgumentException(assemblerContext.getDto().getClass() + " - No factory parameters found in the DTO. Please check the @MatchingFactoryParameter annotation.");
        }

        if (Factory.class.isAssignableFrom(factory.getClass())) {
            Factory<?> defaultFactory = (Factory<?>) factory;
            return defaultFactory.create(parameters);
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
                return factoryMethod.invoke(factory, parameters);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e);
            }
        }
    }
}
