/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

class DomainModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainModule.class);
    private final Map<Key<?>, Class<?>> bindings;
    private final Collection<BindingStrategy> bindingStrategies;

    DomainModule(Map<Key<?>, Class<?>> bindings, Collection<BindingStrategy> bindingStrategies) {
        this.bindings = bindings;
        this.bindingStrategies = bindingStrategies;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        bind(DomainRegistry.class).to(DomainRegistryImpl.class);

        for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue().getSimpleName());
            bind(binding.getKey()).to((Class) binding.getValue());
        }

        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }
    }
}
