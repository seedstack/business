/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import java.util.Map;
import javax.inject.Provider;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MigrateModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateModule.class);
    private final Map<Key<?>, Key<?>> repositoryBindings;
    private final Map<Key<?>, Key<?>> factoryBindings;
    private final Map<Key<?>, Key<?>> assemblerBindings;

    MigrateModule(Map<Key<?>, Key<?>> repositoryBindings, Map<Key<?>, Key<?>> factoryBindings,
            Map<Key<?>, Key<?>> assemblerBindings) {
        this.repositoryBindings = repositoryBindings;
        this.factoryBindings = factoryBindings;
        this.assemblerBindings = assemblerBindings;
    }

    @Override
    protected void configure() {
        bind(FluentAssembler.class).to(FluentAssemblerAdapter.class);

        for (Map.Entry<Key<?>, Key<?>> binding : repositoryBindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue());
            bind(binding.getKey()).toProvider(createRepositoryProvider(binding.getValue()));
        }
        for (Map.Entry<Key<?>, Key<?>> binding : factoryBindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue());
            bind(binding.getKey()).toProvider(createFactoryProvider(binding.getValue()));
        }
        for (Map.Entry<Key<?>, Key<?>> binding : assemblerBindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue());
            bind(binding.getKey()).toProvider(createAssemblerProvider(binding.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    private <A extends AggregateRoot<I>, I, T extends Repository<A, I>> Provider<T> createRepositoryProvider(
            Key<?> value) {
        return new LegacyRepositoryProvider<>((Key<T>) value);
    }

    @SuppressWarnings("unchecked")
    private <P extends Producible, T extends Factory<P>> Provider<T> createFactoryProvider(Key<?> value) {
        return new GenericFactoryProvider<>((Key<T>) value);
    }

    @SuppressWarnings("unchecked")
    private <A, D, T extends Assembler<A, D>> Provider<T> createAssemblerProvider(Key<?> value) {
        return new LegacyAssemblerProvider<>((Key<T>) value);
    }
}
