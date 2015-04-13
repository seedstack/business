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

import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.resolver.DtoInfoResolver;
import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;
import org.seedstack.business.core.interfaces.assembler.resolver.AnnotationResolver;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.seed.core.utils.SeedCheckUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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

    /**
     * Assemble one or a tuple of aggregate root from a dto.
     *
     * @param aggregateRoots the aggregate root(s) to assemble
     * @param <T> type of aggregate root(s). It could be a {@code Tuple} or an {@code AggregateRoot}
     * @return the assembled aggregate root(s)
     */
    protected  <T> T assembleWithDto(T aggregateRoots) {
        Assembler assembler = registry.assemblerOf(assemblerContext.getAggregateClass(), assemblerContext.getDto().getClass());
        //noinspection unchecked
        assembler.mergeAggregateWithDto(aggregateRoots, assemblerContext.getDto());
        return aggregateRoots;
    }

    /**
     * Loads an aggregate roots from a repository.
     *
     * @param key the aggregate roots identity
     * @param <A> the aggregate root type
     * @return the loaded aggregate root
     */
    protected <A> A loadFromRepo(Object key) {
        Repository repository = assemblerContext.getRepository();
        //noinspection unchecked
        return (A) repository.load(key);
    }

    protected Object resolveId(Object dto, Class<? extends AggregateRoot<?>> aggregateRootClass) {
        Object id;

        ParameterHolder parameterHolder = dtoInfoResolver.resolveId(dto);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        //noinspection unchecked
        Class<? extends DomainObject> aggregateIdClass = (Class<? extends DomainObject>) BusinessUtils.getAggregateIdClass(aggregateRootClass);
        // TODO <pith> : check the case when one of the parameters is null
        if (parameterHolder.first() != null && aggregateIdClass.isAssignableFrom(parameterHolder.first().getClass())) {
            // The first parameter is already the id we are looking for
            id = parameterHolder.first();
        } else {
            // Get the "magic" factory for the aggregate id class
            Factory<?> factory = registry.defaultFactoryOf(aggregateIdClass);
            // Create the id based on the id constructor matching the given parameters
            id = factory.create(parameterHolder.parameters());
            // TODO <pith> : what if there is an actual factory for this value object ?
        }

        if (id == null) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }

        return id;
    }

    protected Object getAggregateFromFactory(GenericFactory<?> factory, Class<? extends AggregateRoot<?>> aggregateClass) {
        SeedCheckUtils.checkIfNotNull(factory);
        SeedCheckUtils.checkIfNotNull(aggregateClass);

        // Extract the factory parameters from the DTO (using @MatchingFactoryParameter)
        ParameterHolder parameterHolder = dtoInfoResolver.resolveAggregate(assemblerContext.getDto());
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No factory parameters found in the DTO. Please check the @MatchingFactoryParameter annotation.");
        }

        // Find the method in the factory which match the signature determined with the previously extracted parameters
        Method factoryMethod = MethodMatcher.findMatchingMethod(factory.getClass(), aggregateClass, parameterHolder.parameters());
        if (factoryMethod == null) {
            throw new IllegalStateException(factory.getClass().getSimpleName() +
                    ": Enable to find a method matching the parameter [" +
                    Arrays.toString(parameterHolder.parameters()) + "]");
        }

        // Invoke the factory to create the aggregate root
        try {
            //noinspection unchecked
            return factoryMethod.invoke(factory, parameterHolder.parameters());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Failed to call " + factoryMethod.getName(), e);
        }
    }
}
