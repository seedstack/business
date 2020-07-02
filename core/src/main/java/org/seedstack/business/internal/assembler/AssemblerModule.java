/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.multibindings.Multibinder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.internal.assembler.dsl.FluentAssemblerImpl;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.shed.reflect.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AssemblerModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerModule.class);
    private final Map<Key<?>, Class<?>> bindings;
    private final List<Class<? extends DtoInfoResolver>> dtoInfoResolverClasses;
    private final Collection<BindingStrategy> bindingStrategies;

    AssemblerModule(Map<Key<?>, Class<?>> bindings,
            List<Class<? extends DtoInfoResolver>> dtoInfoResolverClasses,
            Collection<BindingStrategy> bindingStrategies) {
        this.bindings = bindings;
        this.dtoInfoResolverClasses = dtoInfoResolverClasses;
        this.bindingStrategies = bindingStrategies;
    }

    @Override
    protected void configure() {
        install(new AssemblerPrivateModule(dtoInfoResolverClasses));

        for (Map.Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue()
                    .getSimpleName());
            bind(binding.getKey()).to(Classes.cast(binding.getValue()));
        }

        // Bind strategies
        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }
    }

    private static class AssemblerPrivateModule extends PrivateModule {

        private final List<Class<? extends DtoInfoResolver>> dtoInfoResolverClasses;

        AssemblerPrivateModule(List<Class<? extends DtoInfoResolver>> dtoInfoResolverClasses) {
            this.dtoInfoResolverClasses = dtoInfoResolverClasses;
        }

        @Override
        protected void configure() {
            bind(AssemblerRegistry.class).to(AssemblerRegistryImpl.class);
            bind(FluentAssembler.class).to(FluentAssemblerImpl.class);

            Multibinder<DtoInfoResolver> multibinder = Multibinder.newSetBinder(binder(), DtoInfoResolver.class);
            for (Class<? extends DtoInfoResolver> dtoInfoResolverClass : dtoInfoResolverClasses) {
                LOGGER.trace("Binding DTO info resolver {}", dtoInfoResolverClass.getName());
                multibinder.addBinding()
                        .to(dtoInfoResolverClass);
            }

            expose(AssemblerRegistry.class);
            expose(FluentAssembler.class);
        }
    }
}
