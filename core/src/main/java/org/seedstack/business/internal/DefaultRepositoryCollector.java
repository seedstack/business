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
import org.apache.commons.configuration.Configuration;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.strategy.GenericBindingStrategy;
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.seedstack.seed.Application;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
class DefaultRepositoryCollector {

    private final Collection<Class<?>> aggregateClasses;
    private final Collection<Class<?>> defaultRepositoryImpls;
    private final Application application;
    private ClassLoader classLoader;

    public DefaultRepositoryCollector(Collection<Class<?>> aggregateClasses, Collection<Class<?>> defaultRepositoryImpls, Application application) {
        this.aggregateClasses = aggregateClasses;
        this.defaultRepositoryImpls = defaultRepositoryImpls;
        this.application = application;
    }

    /**
     * Prepares the binding strategies which bind default repositories. The specificity here is that it could have
     * multiple implementations of default repository, i.e. one per persistence.
     *
     * @return a binding strategy
     */
    public Collection<BindingStrategy> collect() {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<BindingStrategy>();

        // Extract the type variables which will be passed to the constructor
        Map<Type[], Key<?>> generics = new HashMap<Type[], Key<?>>();
        for (Class<?> aggregateClass : includeSuperClasses(aggregateClasses)) {
            Class<?> aggregateKey = TypeToken.of(aggregateClass).resolveType(AggregateRoot.class.getTypeParameters()[0]).getRawType();
            Type[] params = {aggregateClass, aggregateKey};

            TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(Repository.class, params));
            Key<?> defaultKey = defaultRepositoryQualifier(aggregateClass, genericInterface);

            generics.put(params, defaultKey);
        }

        // Create a binding strategy for each default repository implementation
        for (Class<?> defaultRepoIml : defaultRepositoryImpls) {
            bindingStrategies.add(new GenericBindingStrategy<Repository>(Repository.class, defaultRepoIml, generics));
        }
        return bindingStrategies;
    }

    Set<Class<?>> includeSuperClasses(Collection<Class<?>> aggregateClasses) {
        Set<Class<?>> results = new HashSet<Class<?>>();
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

    Key<?> defaultRepositoryQualifier(Class<?> aggregateClass, TypeLiteral<?> genericInterface) {
        Key<?> defaultKey = null;

        Configuration configuration = this.application.getConfiguration(aggregateClass);

        if (configuration != null && !configuration.isEmpty()) {
            String qualifierName = configuration.getString("default-repository");
            if (qualifierName != null && !"".equals(qualifierName)) {
                try {
                    classLoader = SeedReflectionUtils.findMostCompleteClassLoader();
                    Class<?> qualifierClass = classLoader.loadClass(qualifierName);
                    if (Annotation.class.isAssignableFrom(qualifierClass)) {
                        defaultKey = Key.get(genericInterface, (Class<? extends Annotation>) qualifierClass);
                    } else {
                        throw SeedException.createNew(BusinessCoreErrorCodes.CLASS_IS_NOT_AN_ANNOTATION).put("class", qualifierName);
                    }
                } catch (ClassNotFoundException e) {
                    defaultKey = Key.get(genericInterface, Names.named(qualifierName));
                }
            }
        }
        return defaultKey;
    }
}
