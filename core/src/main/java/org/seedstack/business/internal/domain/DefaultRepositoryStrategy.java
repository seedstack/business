/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static java.lang.reflect.Modifier.isAbstract;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.util.Types;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericGuiceFactory;
import org.seedstack.seed.core.internal.guice.GenericGuiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultRepositoryStrategy<T extends Repository> implements BindingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRepositoryStrategy.class);
    private static final Class<?> FACTORY_CLASS = GenericGuiceFactory.class;
    private final Class<T> repositoryInterface;
    private final Type[] generics;
    private final Collection<Class<? extends T>> implementations;
    private final Key<? extends T> defaultKey;

    DefaultRepositoryStrategy(Class<T> repositoryInterface, Type[] generics,
            Collection<Class<? extends T>> implementations, Key<? extends T> defaultKey) {
        this.repositoryInterface = repositoryInterface;
        this.generics = generics;
        this.implementations = implementations;
        this.defaultKey = defaultKey;
    }

    @Override
    public void resolve(Binder binder) {
        for (Class<? extends T> impl : implementations) {
            if (Arrays.stream(impl.getMethods()).anyMatch(m -> isAbstract(m.getModifiers()))) {
                LOGGER.warn("Skipping default repository implementation {}: abstract methods are still present",
                        impl.getName());
            } else {
                Key<T> key = BusinessUtils.getQualifier(impl)
                        .map(qualifier -> Key.get(repositoryInterface, qualifier))
                        .orElseThrow(() -> new IllegalStateException("Missing qualifier on implementation" + impl));

                if (defaultKey != null) {
                    binder.bind(repositoryInterface).to(defaultKey);
                }

                Provider<T> provider = new GenericGuiceProvider<>(impl, generics);
                binder.requestInjection(provider);
                binder.bind(key).toProvider(provider);

                FactoryModuleBuilder guiceFactoryBuilder = new FactoryModuleBuilder();
                guiceFactoryBuilder.implement(key, impl);
                binder.install(guiceFactoryBuilder.build(
                        TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, impl))
                ));
            }
        }
    }
}
