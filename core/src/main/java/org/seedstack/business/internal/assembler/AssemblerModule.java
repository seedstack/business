/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.internal.assembler.dsl.FluentAssemblerImpl;
import org.seedstack.business.internal.assembler.dsl.InternalRegistry;
import org.seedstack.business.internal.assembler.dsl.InternalRegistryInternal;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

class AssemblerModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerModule.class);
    private final Collection<Class<? extends Assembler>> assemblerClasses;
    private final Map<Key<Assembler>, Class<? extends Assembler>> bindings;
    private final Collection<BindingStrategy> bindingStrategies;

    AssemblerModule(Collection<Class<? extends Assembler>> assemblerClasses, Map<Key<Assembler>, Class<? extends Assembler>> bindings, Collection<BindingStrategy> bindingStrategies) {
        this.assemblerClasses = assemblerClasses;
        this.bindings = bindings;
        this.bindingStrategies = bindingStrategies;
    }

    @Override
    protected void configure() {
        bind(InternalRegistry.class).to(InternalRegistryInternal.class);
        bind(FluentAssembler.class).to(FluentAssemblerImpl.class);

        for (Map.Entry<Key<Assembler>, Class<? extends Assembler>> binding : bindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue().getSimpleName());
            bind(binding.getKey()).to(binding.getValue());
        }

        // Bind strategies
        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }
    }
}
