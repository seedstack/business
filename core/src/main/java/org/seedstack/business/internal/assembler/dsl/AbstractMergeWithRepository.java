/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.Producible;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.assembler.dsl.resolver.DtoInfoResolver;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;
import org.seedstack.business.internal.assembler.dsl.resolver.impl.AnnotationResolver;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.seed.SeedException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;


abstract class AbstractMergeWithRepository {
    private final Context context;
    private final DtoInfoResolver dtoInfoResolver = new AnnotationResolver();

    AbstractMergeWithRepository(Context context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    <A extends AggregateRoot<ID>, ID> ID createIdentifier(ParameterHolder parameterHolder, Class<A> aggregateRootClass) {
        checkNotNull(parameterHolder);
        checkNotNull(aggregateRootClass);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }
        return (ID) paramsToIds((Class<? extends AggregateRoot<Object>>) aggregateRootClass, parameterHolder, -1);
    }

    @SuppressWarnings("unchecked")
    Object[] createIdentifiers(ParameterHolder parameterHolder, Class<? extends AggregateRoot<?>>... aggregateRootClasses) {
        checkNotNull(parameterHolder);
        checkNotNull(aggregateRootClasses);
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("No id found in the DTO. Please check the @MatchingEntityId annotation.");
        }
        Object[] identifiers = new Object[aggregateRootClasses.length];
        for (int i = 0; i < aggregateRootClasses.length; i++) {
            identifiers[i] = paramsToIds((Class<? extends AggregateRoot<Object>>) aggregateRootClasses[i], parameterHolder, i);
        }
        return identifiers;
    }

    private Object paramsToIds(Class<? extends AggregateRoot<Object>> aggregateRootClass, ParameterHolder parameterHolder, int aggregateIndex) {
        Class<?> aggregateIdClass = BusinessUtils.getAggregateIdClass(aggregateRootClass);
        Object id;

        Object element = parameterHolder.uniqueElementForAggregateRoot(aggregateIndex);
        if (element != null && aggregateIdClass.isAssignableFrom(element.getClass())) {
            // The first parameter is already the id we are looking for
            id = element;
        } else {
            // Otherwise we create a value object through a factory from all parameters
            if (!Producible.class.isAssignableFrom(aggregateIdClass)) {
                // TODO seedstack exception
                throw new IllegalStateException("Aggregate " + aggregateRootClass.getName() + " identifier is not producible by a factory. Don't specify any index in @MatchingEntityId annotation.");
            }
            Factory<?> factory = context.factoryOf(Producible.class.asSubclass(aggregateIdClass));
            id = factory.create(parameterHolder.parametersOfAggregateRoot(aggregateIndex));
            // TODO <pith> : check the case when one of the parameters is null
        }
        if (id == null) {
            throw new IllegalArgumentException("No identifier could be resolved from the DTO. Please check the @MatchingEntityId annotation.");
        }
        return id;
    }

    <A extends AggregateRoot<?>> A createFromFactory(Class<A> aggregateClass, Object[] parameters) {
        // TODO: cache
        return getAggregateFromFactory(context.factoryOf(aggregateClass), aggregateClass, parameters);
    }

    @SuppressWarnings("unchecked")
    <A extends AggregateRoot<ID>, ID, D> void mergeAggregateFromDto(A aggregateRoot, D dto) {
        // TODO: cache
        Assembler<A, D> assembler = context.assemblerOf((Class<A>) aggregateRoot.getClass(), (Class<D>) dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoot, dto);
    }

    @SuppressWarnings("unchecked")
    <A extends AggregateRoot<?>> A getAggregateFromFactory(Factory<A> factory, Class<A> aggregateClass, Object[] parameters) {
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
                    return (A) factoryMethod.invoke(factory);
                } else {
                    return (A) factoryMethod.invoke(factory, parameters);
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

    Context getContext() {
        return context;
    }

    DtoInfoResolver getDtoInfoResolver() {
        return dtoInfoResolver;
    }
}
