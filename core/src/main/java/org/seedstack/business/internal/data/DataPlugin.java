/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import static java.util.stream.Collectors.toSet;
import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;
import static org.seedstack.business.internal.utils.PluginUtils.associateInterfaceToImplementations;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.Context;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.kametic.specifications.Specification;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.data.DataExporter;
import org.seedstack.business.data.DataImporter;
import org.seedstack.business.data.DataManager;
import org.seedstack.business.data.DataSet;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.DomainProvider;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.shed.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataPlugin.class);
    private final Map<String, Map<String, DataImporterDefinition<?>>> importerDefs = new HashMap<>();
    private final Map<String, Map<String, DataExporterDefinition<?>>> exporterDefs = new HashMap<>();
    private final Collection<Class<? extends DataImporter>> importerClasses = new HashSet<>();
    private final Collection<Class<? extends DataExporter>> exporterClasses = new HashSet<>();
    private final Collection<Class<?>> dataClasses = new HashSet<>();
    private final Map<Key<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Key<?>, Class<?>> overridingBindings = new HashMap<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();
    @Inject
    private DataManager dataManager;

    @Override
    public String name() {
        return "business-data";
    }

    @Override
    protected Collection<Class<?>> dependencies() {
        return Lists.newArrayList(DomainProvider.class);
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .specification(BusinessSpecifications.DATA_IMPORTER)
                .specification(BusinessSpecifications.DATA_EXPORTER)
                .specification(BusinessSpecifications.DATA_SET)
                .build();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        Map<Specification, Collection<Class<?>>> classesBySpec = initContext.scannedTypesBySpecification();

        streamClasses(classesBySpec.get(BusinessSpecifications.DATA_IMPORTER), DataImporter.class)
                .filter(importerClass -> !DefaultDataImporter.class.isAssignableFrom(importerClass))
                .forEach(importerClasses::add);
        LOGGER.debug("Data importers => {}", importerClasses);

        streamClasses(classesBySpec.get(BusinessSpecifications.DATA_EXPORTER), DataExporter.class)
                .filter(exporterClass -> !DefaultDataExporter.class.isAssignableFrom(exporterClass))
                .forEach(exporterClasses::add);
        LOGGER.debug("Data exporters => {}", exporterClasses);

        streamClasses(classesBySpec.get(BusinessSpecifications.DATA_SET), Object.class).forEach(
                dataClasses::add);
        LOGGER.debug("DTO classes with default importer/exporter => {}", dataClasses);

        // Add bindings for explicit data importers/exporters
        bindings.putAll(associateInterfaceToImplementations(DataImporter.class, importerClasses, false));
        overridingBindings.putAll(associateInterfaceToImplementations(DataImporter.class, importerClasses, true));
        bindings.putAll(associateInterfaceToImplementations(DataExporter.class, exporterClasses, false));
        overridingBindings.putAll(associateInterfaceToImplementations(DataExporter.class, exporterClasses, true));

        Set<Class<?>> classesWithExplicitImporter = importerClasses.stream()
                .map(importerClass -> (Class<?>) BusinessUtils.resolveGenerics(DataImporter.class, importerClass)[0])
                .collect(toSet());
        Set<Class<?>> classesWithExplicitExporter = exporterClasses.stream()
                .map(exporterClass -> (Class<?>) BusinessUtils.resolveGenerics(DataExporter.class, exporterClass)[0])
                .collect(toSet());

        // Then add bindings for default data importers/exporters
        bindingStrategies.addAll(new DefaultDataCollector(classesWithExplicitImporter, classesWithExplicitExporter)
                .collect(dataClasses));

        buildImporterDefs(classesWithExplicitImporter);
        buildExporterDefs(classesWithExplicitExporter);

        return InitState.INITIALIZED;
    }

    @SuppressWarnings("unchecked")
    private void buildImporterDefs(Set<Class<?>> classesWithExplicitImporter) {
        dataClasses.stream()
                .filter(dataSetClass -> !classesWithExplicitImporter.contains(dataSetClass))
                .forEach(this::buildImporterDef);

        for (Class<? extends DataImporter> importerClass : importerClasses) {
            Class<?> importedClass = (Class<?>) BusinessUtils.resolveGenerics(DataImporter.class, importerClass)[0];
            if (importedClass == null) {
                throw SeedException.createNew(DataErrorCode.MISSING_TYPE_PARAMETER)
                        .put("class", importerClass);
            }
            buildImporterDef(importedClass);
        }
    }

    private void buildImporterDef(Class<?> importedClass) {
        DataSet dataSet = importedClass.getAnnotation(DataSet.class);
        String group = dataSet == null ? getPackageName(importedClass) : dataSet.group();
        String name = dataSet == null ? importedClass.getSimpleName() : dataSet.name();
        importerDefs.computeIfAbsent(group, k -> new HashMap<>())
                .put(name, new DataImporterDefinition<>(
                        group,
                        name,
                        importedClass));
    }

    @SuppressWarnings("unchecked")
    private void buildExporterDefs(Set<Class<?>> classesWithExplicitExporter) {
        dataClasses.stream()
                .filter(dataSetClass -> !classesWithExplicitExporter.contains(dataSetClass))
                .forEach(this::buildExporterDef);

        for (Class<? extends DataExporter> exporterClass : exporterClasses) {
            Class<?> exportedClass = (Class<?>) BusinessUtils.resolveGenerics(DataExporter.class, exporterClass)[0];
            if (exportedClass == null) {
                throw SeedException.createNew(DataErrorCode.MISSING_TYPE_PARAMETER)
                        .put("class", exporterClass);
            }
            buildExporterDef(exportedClass);
        }
    }

    private void buildExporterDef(Class<?> exportedClass) {
        DataSet dataSet = exportedClass.getAnnotation(DataSet.class);
        String group = dataSet == null ? getPackageName(exportedClass) : dataSet.group();
        String name = dataSet == null ? exportedClass.getSimpleName() : dataSet.name();
        exporterDefs.computeIfAbsent(group, k -> new HashMap<>())
                .put(name, new DataExporterDefinition<>(
                        group,
                        name,
                        exportedClass));
    }

    private String getPackageName(Class<?> someClass) {
        String className = someClass.getName();
        if (className.contains(".")) {
            return className.substring(0, className.lastIndexOf('.'));
        } else {
            return className;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new DataModule(bindings, bindingStrategies, importerDefs, exporterDefs);
    }

    @Override
    public Object nativeOverridingUnitModule() {
        return new DataOverridingModule(overridingBindings);
    }

    @Override
    public void start(Context context) {
        BusinessConfig.DataConfig dataConfig = getConfiguration(BusinessConfig.DataConfig.class);
        ClassLoader classLoader = ClassLoaders.findMostCompleteClassLoader(DataPlugin.class);

        if (dataConfig.isImportOnStart()) {
            for (Map<String, DataImporterDefinition<?>> dataImporterDefinitionMap : importerDefs.values()) {
                for (DataImporterDefinition<?> dataImporterDefinition : dataImporterDefinitionMap.values()) {
                    String dataPath = String.format("META-INF/data/%s/%s.json", dataImporterDefinition.getGroup(),
                            dataImporterDefinition.getName());
                    InputStream dataStream = classLoader.getResourceAsStream(dataPath);

                    if (dataStream != null) {
                        if (!dataManager.isInitialized(dataImporterDefinition.getGroup(),
                                dataImporterDefinition.getName()) || dataConfig.isForceImport()) {
                            LOGGER.info("Importing initialization data for {}.{}", dataImporterDefinition.getGroup(),
                                    dataImporterDefinition.getName());
                            dataManager.importData(dataStream, dataImporterDefinition.getGroup(),
                                    dataImporterDefinition.getName());
                        }

                        try {
                            dataStream.close();
                        } catch (IOException e) {
                            LOGGER.warn("Unable to close data resource " + dataPath, e);
                        }
                    }
                }
            }
        }
    }
}
