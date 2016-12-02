/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;
import org.seedstack.shed.ClassLoaders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


class DefaultRepositoryCollector {
    private static final String DEFAULT_REPOSITORY_KEY = "defaultRepository";
    private final Collection<Class<?>> aggregateClasses;
    private final Collection<Class<? extends Repository>> defaultRepositoryImplementations;
    private final Application application;

    DefaultRepositoryCollector(Collection<Class<?>> aggregateClasses, Collection<Class<? extends Repository>> defaultRepositoryImplementations, Application application) {
        this.aggregateClasses = aggregateClasses;
        this.defaultRepositoryImplementations = defaultRepositoryImplementations;
        this.application = application;
    }

    /**
     * Prepares the binding strategies which bind default repositories. The specificity here is that it could have
     * multiple implementations of default repository, i.e. one per persistence.
     *
     * @return a binding strategy
     */
    Collection<BindingStrategy> collect() {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

        // Extract the type variables which will be passed to the constructor
        Map<Type[], Key<?>> generics = new HashMap<>();
        for (Class<?> aggregateClass : includeSuperClasses(aggregateClasses)) {
            Class<?> aggregateKey = TypeToken.of(aggregateClass).resolveType(AggregateRoot.class.getTypeParameters()[0]).getRawType();
            Type[] params = {aggregateClass, aggregateKey};

            TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(Repository.class, params));
            Key<?> defaultKey = defaultRepositoryQualifier(aggregateClass, genericInterface);

            generics.put(params, defaultKey);
        }

        // Create a binding strategy for each default repository implementation
        for (Class<? extends Repository> defaultRepoIml : defaultRepositoryImplementations) {
            bindingStrategies.add(new GenericBindingStrategy<>(Repository.class, defaultRepoIml, generics));
        }
        return bindingStrategies;
    }

    Set<Class<?>> includeSuperClasses(Collection<Class<?>> aggregateClasses) {
        Set<Class<?>> results = new HashSet<>();
        for (Class<?> aggregateClass : aggregateClasses) {
            Class<?> classToAdd = aggregateClass;
            while (classToAdd != null) {
                results.add(classToAdd);

                classToAdd = classToAdd.getSuperclass();
                if (BaseAggregateRoot.class.equals(classToAdd) || !BaseAggregateRoot.class.isAssignableFrom(classToAdd)) {
                    break;
                }
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    Key<?> defaultRepositoryQualifier(Class<?> aggregateClass, TypeLiteral<?> genericInterface) {
        Key<?> defaultKey = null;

        ClassConfiguration<?> configuration = this.application.getConfiguration(aggregateClass);

        if (configuration != null && !configuration.isEmpty()) {
            String qualifierName = configuration.get(DEFAULT_REPOSITORY_KEY);
            if (qualifierName != null && !"".equals(qualifierName)) {
                try {
                    ClassLoader classLoader = ClassLoaders.findMostCompleteClassLoader(DefaultRepositoryCollector.class);
                    Class<?> qualifierClass = classLoader.loadClass(qualifierName);
                    if (Annotation.class.isAssignableFrom(qualifierClass)) {
                        defaultKey = Key.get(genericInterface, (Class<? extends Annotation>) qualifierClass);
                    } else {
                        throw SeedException.createNew(BusinessErrorCode.CLASS_IS_NOT_AN_ANNOTATION)
                                .put("aggregateClass", aggregateClass.getName())
                                .put("qualifierClass", qualifierName);
                    }
                } catch (ClassNotFoundException e) {
                    defaultKey = Key.get(genericInterface, Names.named(qualifierName));
                }
            }
        }
        return defaultKey;
    }
}
