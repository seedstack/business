/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static com.google.inject.util.Types.newParameterizedType;
import static org.seedstack.business.internal.utils.BusinessUtils.resolveDefaultQualifier;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultRepositoryCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRepositoryCollector.class);
    private static final String DEFAULT_REPOSITORY_KEY = "defaultRepository";
    private final Application application;
    private final Map<Key<?>, Class<?>> bindings;
    private final Collection<Class<? extends Repository>> defaultRepositoryImplementations;

    DefaultRepositoryCollector(Application application,
            Map<Key<?>, Class<?>> bindings,
            Collection<Class<? extends Repository>> defaultRepositoryImplementations) {
        this.application = application;
        this.bindings = bindings;
        this.defaultRepositoryImplementations = defaultRepositoryImplementations;
    }

    /**
     * Prepares the binding strategies which bind default repositories. The specificity here is that
     * it could have multiple implementations of default repository, i.e. one per persistence.
     *
     * @param aggregateClasses the aggregates classes to collect repositories from.
     * @return a binding strategy
     */
    Collection<BindingStrategy> collectFromAggregates(Collection<Class<? extends AggregateRoot>> aggregateClasses) {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<>();
        Map<Type[], Key<?>> allGenerics = new HashMap<>();

        for (Class<? extends AggregateRoot<?>> aggregateClass : BusinessUtils.includeSuperClasses(aggregateClasses)) {
            Type[] generics = getTypes(aggregateClass);
            TypeLiteral<?> genericInterface = TypeLiteral.get(newParameterizedType(Repository.class, generics));
            allGenerics.put(generics, resolveDefaultQualifier(
                    bindings,
                    application.getConfiguration(aggregateClass),
                    DEFAULT_REPOSITORY_KEY,
                    aggregateClass,
                    genericInterface
                    ).orElse(null)
            );
        }

        // Create a binding strategy for each default repository implementation
        for (Class<? extends Repository> defaultRepoImpl : defaultRepositoryImplementations) {
            bindingStrategies.add(new GenericBindingStrategy<>(
                    Repository.class,
                    defaultRepoImpl,
                    allGenerics)
            );
        }

        return bindingStrategies;
    }

    Collection<BindingStrategy> collectFromInterfaces(Collection<Class<? extends Repository>> repositoryInterfaces) {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<>();
        for (Class<? extends Repository> repositoryInterface : repositoryInterfaces) {
            if (bindings.containsKey(Key.get(repositoryInterface))) {
                LOGGER.info("Skipping implementation generation for repository {}: an explicit implementation already "
                        + "exists", repositoryInterface.getName());
            } else {
                bindingStrategies.add(collectFromInterface(
                        repositoryInterface,
                        BusinessUtils.resolveGenerics(Repository.class, repositoryInterface)
                ));
            }
        }
        return bindingStrategies;
    }

    private <T extends Repository> BindingStrategy collectFromInterface(Class<T> repoInterface, Type[] generics) {
        DefaultRepositoryGenerator<T> defaultRepositoryGenerator = new DefaultRepositoryGenerator<>(repoInterface);
        Class<?> aggregateClass = (Class<?>) generics[0];
        return new DefaultRepositoryStrategy<>(
                repoInterface,
                generics,
                defaultRepositoryImplementations.stream()
                        .map(defaultRepositoryGenerator::generate)
                        .collect(Collectors.toList()),
                resolveDefaultQualifier(
                        bindings,
                        application.getConfiguration(aggregateClass),
                        DEFAULT_REPOSITORY_KEY,
                        aggregateClass,
                        TypeLiteral.get(repoInterface)
                ).orElse(null)
        );
    }

    @SuppressWarnings("unchecked")
    private Type[] getTypes(Class aggregateClass) {
        Class<?> aggregateIdClass = BusinessUtils.resolveAggregateIdClass(aggregateClass);
        return new Type[]{aggregateClass, aggregateIdClass};
    }
}
