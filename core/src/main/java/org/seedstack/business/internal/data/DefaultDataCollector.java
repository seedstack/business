/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.seedstack.business.data.DataExporter;
import org.seedstack.business.data.DataImporter;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;

/**
 * Collects the binding strategies for default importers and exporters.
 */
class DefaultDataCollector {
    private final Set<Class<?>> classesWithExplicitImporter;
    private final Set<Class<?>> classesWithExplicitExporter;

    DefaultDataCollector(Set<Class<?>> classesWithExplicitImporter, Set<Class<?>> classesWithExplicitExporter) {
        this.classesWithExplicitImporter = classesWithExplicitImporter;
        this.classesWithExplicitExporter = classesWithExplicitExporter;
    }

    Collection<BindingStrategy> collect(Collection<Class<?>> dataSetClasses) {
        List<BindingStrategy> bindingStrategies = new ArrayList<>();

        // Create a binding strategy for each data set classes that don't have an explicit importer
        bindingStrategies.add(new GenericBindingStrategy<>(
                DataImporter.class,
                DefaultDataImporter.class,
                dataSetClasses.stream()
                        .filter(dataSetClass -> !classesWithExplicitImporter.contains(dataSetClass))
                        .map(dataSetClass -> new Type[]{dataSetClass})
                        .collect(toSet())));

        // Create a binding strategy for each data set classes that don't have an explicit importer
        bindingStrategies.add(new GenericBindingStrategy<>(
                DataExporter.class,
                DefaultDataExporter.class,
                dataSetClasses.stream()
                        .filter(dataSetClass -> !classesWithExplicitExporter.contains(dataSetClass))
                        .map(dataSetClass -> new Type[]{dataSetClass})
                        .collect(toSet())));

        return bindingStrategies;
    }
}
