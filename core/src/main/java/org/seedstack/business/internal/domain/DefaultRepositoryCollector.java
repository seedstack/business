/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;

class DefaultRepositoryCollector {
    private static final String DEFAULT_REPOSITORY_KEY = "defaultRepository";
    private final Collection<Class<? extends Repository>> defaultRepositoryImplementations;
    private final Application application;

    DefaultRepositoryCollector(Collection<Class<? extends Repository>> defaultRepositoryImplementations,
            Application application) {
        this.defaultRepositoryImplementations = defaultRepositoryImplementations;
        this.application = application;
    }

    /**
     * Prepares the binding strategies which bind default repositories. The specificity here is that
     * it could have multiple implementations of default repository, i.e. one per persistence.
     *
     * @param aggregateClasses the aggregates classes to collect repositories from.
     * @return a binding strategy
     */
    Collection<BindingStrategy> collect(Collection<Class<?>> aggregateClasses) {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

        // Extract the type variables which will be passed to the constructor
        Map<Type[], Key<?>> generics = new HashMap<>();
        for (Class<?> aggregateClass : BusinessUtils.includeSuperClasses(aggregateClasses)) {
            Class<?> aggregateKey = TypeToken.of(aggregateClass)
                    .resolveType(AggregateRoot.class.getTypeParameters()[0])
                    .getRawType();
            Type[] params = {aggregateClass, aggregateKey};

            TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(Repository.class, params));
            Key<?> defaultKey = BusinessUtils.defaultQualifier(application, DEFAULT_REPOSITORY_KEY, aggregateClass,
                    genericInterface);

            generics.put(params, defaultKey);
        }

        // Create a binding strategy for each default repository implementation
        for (Class<? extends Repository> defaultRepoIml : defaultRepositoryImplementations) {
            bindingStrategies.add(new GenericBindingStrategy<>(Repository.class, defaultRepoIml, generics));
        }
        return bindingStrategies;
    }
}
