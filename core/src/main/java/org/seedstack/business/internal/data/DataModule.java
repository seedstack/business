/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import java.util.Collection;
import java.util.Map;
import org.seedstack.business.data.DataManager;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DataModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);
    private final Map<Key<?>, Class<?>> bindings;
    private final Collection<BindingStrategy> bindingStrategies;
    private final Map<String, Map<String, DataImporterDefinition<?>>> importerDefs;
    private final Map<String, Map<String, DataExporterDefinition<?>>> exporterDefs;

    DataModule(Map<Key<?>, Class<?>> bindings, Collection<BindingStrategy> bindingStrategies,
            Map<String, Map<String, DataImporterDefinition<?>>> importerDefs,
            Map<String, Map<String, DataExporterDefinition<?>>> exporterDefs) {
        this.bindings = bindings;
        this.bindingStrategies = bindingStrategies;
        this.importerDefs = importerDefs;
        this.exporterDefs = exporterDefs;
    }

    @Override
    protected void configure() {
        bind(DataManager.class).to(DataManagerImpl.class);

        // Bind explicit importers/exporters
        for (Map.Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue()
                    .getSimpleName());
            bind(binding.getKey()).to(cast(binding.getValue()));
        }

        // Bind strategies
        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }

        // Bind internal maps
        bind(new ImporterMapTypeLiteral()).toInstance(importerDefs);
        bind(new ExporterMapTypeLiteral()).toInstance(exporterDefs);
    }

    @SuppressWarnings("unchecked")
    private <T extends Class<?>> T cast(Class<?> someClass) {
        return (T) someClass;
    }

    private static class ImporterMapTypeLiteral extends TypeLiteral<Map<String, Map<String,
            DataImporterDefinition<?>>>> {}

    private static class ExporterMapTypeLiteral extends TypeLiteral<Map<String, Map<String,
            DataExporterDefinition<?>>>> {}
}
