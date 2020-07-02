/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.data;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.util.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.data.DataExporter;
import org.seedstack.business.data.DataImporter;
import org.seedstack.business.data.DataManager;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.Configuration;
import org.seedstack.shed.exception.Throwing;

/**
 * Implementation of the {@link DataManager}.
 */
class DataManagerImpl implements DataManager {
    private static final String UTF_8 = "UTF-8";
    private static final String DATA_SET = "dataSet";
    private static final String IMPORTED_CLASS = "importedClass";
    private static final String GROUP = "group";
    private static final String NAME = "name";
    private static final String ITEMS = "items";
    private static final String CLASSES_MAP_KEY = "%s:%s";
    private final JsonFactory jsonFactory;
    private final ObjectMapper objectMapper;
    @Inject
    private Map<String, Map<String, DataExporterDefinition<?>>> allDataExporters;
    @Inject
    private Map<String, Map<String, DataImporterDefinition<?>>> allDataImporters;
    @Configuration
    private BusinessConfig.DataConfig dataConfig;
    @Inject
    private Injector injector;

    DataManagerImpl() {
        this.jsonFactory = new JsonFactory();
        this.objectMapper = new ObjectMapper();
        this.jsonFactory.setCodec(this.objectMapper);
    }

    @Override
    public void exportData(OutputStream outputStream, String group) {
        Map<String, DataExporterDefinition<?>> dataExporterDefinitions = allDataExporters.get(group);

        if (dataExporterDefinitions == null) {
            throw BusinessException.createNew(DataErrorCode.NO_EXPORTER_FOUND)
                    .put(DATA_SET, group);
        }

        List<DataSetMarker<?>> datasets = new ArrayList<>();
        for (DataExporterDefinition<?> dataExporterDefinition : dataExporterDefinitions.values()) {
            datasets.add(new DataSetMarker<>(dataExporterDefinition.getGroup(), dataExporterDefinition.getName(),
                    getExporterInstance(dataExporterDefinition.getExportedClass()).exportData()));
        }

        dumpAll(datasets, outputStream);
    }

    @Override
    public void exportData(OutputStream outputStream, String group, String name) {
        Map<String, DataExporterDefinition<?>> dataExporterDefinitionMap = allDataExporters.get(group);

        if (dataExporterDefinitionMap == null) {
            throw BusinessException.createNew(DataErrorCode.NO_EXPORTER_FOUND)
                    .put(DATA_SET, String.format(CLASSES_MAP_KEY, group, name));
        }

        DataExporterDefinition<?> dataExporterDefinition = dataExporterDefinitionMap.get(name);

        if (dataExporterDefinition == null) {
            throw BusinessException.createNew(DataErrorCode.NO_EXPORTER_FOUND)
                    .put(DATA_SET, String.format(CLASSES_MAP_KEY, group, name));
        }

        dumpAll(Lists.newArrayList(
                new DataSetMarker<>(dataExporterDefinition.getGroup(), dataExporterDefinition.getName(),
                        getExporterInstance(dataExporterDefinition.getExportedClass()).exportData())), outputStream);
    }

    @Override
    public void exportData(OutputStream outputStream) {
        List<DataSetMarker<?>> dataSets = new ArrayList<>();

        for (Map<String, DataExporterDefinition<?>> dataExporterDefinitionMap : allDataExporters.values()) {
            for (DataExporterDefinition<?> dataExporterDefinition : dataExporterDefinitionMap.values()) {
                DataExporter<Object> dataExporter = getExporterInstance(dataExporterDefinition.getExportedClass());
                dataSets.add(new DataSetMarker<>(dataExporterDefinition.getGroup(), dataExporterDefinition.getName(),
                        dataExporter.exportData()));
            }
        }

        dumpAll(dataSets, outputStream);
    }

    @Override
    public void importData(InputStream inputStream) {
        importData(inputStream, null, null);
    }

    @Override
    public void importData(InputStream inputStream, String group) {
        importData(inputStream, group, null);
    }

    @Override
    @SuppressFBWarnings(value = "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE",
            justification = "False positive due to Java 11")
    public void importData(InputStream inputStream, String acceptGroup, String acceptName) {
        try (JsonParser jsonParser = this.jsonFactory.createParser(
                new InputStreamReader(inputStream, Charset.forName(UTF_8)))) {
            ParsingState state = ParsingState.START;
            String group = null;
            String name = null;
            JsonToken jsonToken = jsonParser.nextToken();

            while (jsonToken != null) {
                switch (state) {
                    case START:
                        if (jsonToken == JsonToken.START_ARRAY) {
                            state = ParsingState.DEFINITION_START;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "start array expected");
                        }

                        break;
                    case DEFINITION_START:
                        if (jsonToken == JsonToken.START_OBJECT) {
                            state = ParsingState.DEFINITION_GROUP;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "start object expected");
                        }

                        break;
                    case DEFINITION_GROUP:
                        if (jsonToken == JsonToken.FIELD_NAME && GROUP.equals(jsonParser.getCurrentName())) {
                            group = jsonParser.nextTextValue();
                            state = ParsingState.DEFINITION_NAME;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "group field expected");
                        }

                        break;
                    case DEFINITION_NAME:
                        if (jsonToken == JsonToken.FIELD_NAME && NAME.equals(jsonParser.getCurrentName())) {
                            name = jsonParser.nextTextValue();
                            state = ParsingState.DEFINITION_ITEMS;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "name field expected");
                        }

                        break;
                    case DEFINITION_ITEMS:
                        if (jsonToken == JsonToken.FIELD_NAME && ITEMS.equals(jsonParser.getCurrentName())) {
                            if ((acceptGroup == null || acceptGroup.equals(group))
                                    && (acceptName == null || acceptName.equals(name))) {
                                consumeItems(jsonParser, group, name);
                            } else {
                                skipItems(jsonParser);
                            }
                            state = ParsingState.DEFINITION_END;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "items field expected");
                        }

                        break;
                    case DEFINITION_END:
                        if (jsonToken == JsonToken.END_OBJECT) {
                            group = null;
                            name = null;
                            state = ParsingState.END;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "end object expected");
                        }

                        break;
                    case END:
                        if (jsonToken == JsonToken.START_OBJECT) {
                            state = ParsingState.DEFINITION_GROUP;
                        } else if (jsonToken == JsonToken.END_ARRAY) {
                            state = ParsingState.START;
                        } else {
                            throwParsingError(jsonParser.getCurrentLocation(), "start object or end array expected");
                        }

                        break;
                    default:
                        throwParsingError(jsonParser.getCurrentLocation(), "unexpected parser state");
                }

                jsonToken = jsonParser.nextToken();
            }
        } catch (IOException e) {
            throw BusinessException.wrap(e, DataErrorCode.IMPORT_FAILED);
        }
    }

    private void throwParsingError(JsonLocation jsonLocation, String message) {
        throw BusinessException.createNew(DataErrorCode.FAILED_TO_PARSE_DATA_STREAM)
                .put("parsingError", message)
                .put("line", jsonLocation.getLineNr())
                .put("col", jsonLocation.getColumnNr())
                .put("offset", jsonLocation.getCharOffset());
    }

    private void consumeItems(JsonParser jsonParser, String group, String name) throws IOException {
        Map<String, DataImporterDefinition<?>> dataImporterDefinitionMap = allDataImporters.get(group);
        if (dataImporterDefinitionMap == null) {
            throw BusinessException.createNew(DataErrorCode.NO_IMPORTER_FOUND)
                    .put(GROUP, group)
                    .put(NAME, name);
        }

        DataImporterDefinition<?> currentImporterDefinition = dataImporterDefinitionMap.get(name);
        if (currentImporterDefinition == null) {
            throw BusinessException.createNew(DataErrorCode.NO_IMPORTER_FOUND)
                    .put(GROUP, group)
                    .put(NAME, name);
        }
        if (!group.equals(currentImporterDefinition.getGroup())) {
            throw BusinessException.createNew(DataErrorCode.UNEXPECTED_DATA_TYPE)
                    .put(DATA_SET, String.format(CLASSES_MAP_KEY, group, name))
                    .put(IMPORTED_CLASS, currentImporterDefinition.getImportedClass()
                            .getName());
        }
        if (!name.equals(currentImporterDefinition.getName())) {
            throw BusinessException.createNew(DataErrorCode.UNEXPECTED_DATA_TYPE)
                    .put(DATA_SET, String.format(CLASSES_MAP_KEY, group, name))
                    .put(IMPORTED_CLASS, currentImporterDefinition.getImportedClass()
                            .getName());
        }

        // Check if items contains an array
        if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
            throw new IllegalArgumentException("Items should be an array");
        }

        jsonParser.nextToken();

        // If the array is not empty consume it
        if (jsonParser.getCurrentToken() != JsonToken.END_ARRAY) {
            DataImporter<Object> currentDataImporter = getImporterInstance(
                    currentImporterDefinition.getImportedClass());
            if (dataConfig.isClearBeforeImport()) {
                currentDataImporter.clear();
            }
            currentDataImporter.importData(StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                    jsonParser.readValuesAs(currentImporterDefinition.getImportedClass()), Spliterator.ORDERED),
                    false));

            // The array should properly end
            if (jsonParser.getCurrentToken() != JsonToken.END_ARRAY) {
                throw new IllegalArgumentException("end array expected");
            }
        }
    }

    private void skipItems(JsonParser jsonParser) throws IOException {
        // Check if items contains an array
        if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
            throw new IllegalArgumentException("Items should be an array");
        }
        // Skip values
        jsonParser.skipChildren();
        // The array should properly end
        if (jsonParser.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new IllegalArgumentException("end array expected");
        }
    }

    @Override
    public boolean isInitialized(String group, String name) {
        Map<String, DataImporterDefinition<?>> dataImporterDefinitionMap = allDataImporters.get(group);

        if (dataImporterDefinitionMap == null) {
            throw BusinessException.createNew(DataErrorCode.NO_IMPORTER_FOUND)
                    .put(GROUP, group)
                    .put(NAME, name);
        }

        DataImporterDefinition<?> dataImporterDefinition = dataImporterDefinitionMap.get(name);

        if (dataImporterDefinition == null) {
            throw BusinessException.createNew(DataErrorCode.NO_IMPORTER_FOUND)
                    .put(GROUP, group)
                    .put(NAME, name);
        }

        return getImporterInstance(dataImporterDefinition.getImportedClass()).isInitialized();
    }

    @SuppressFBWarnings(value = "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE",
            justification = "False positive due to Java 11")
    private void dumpAll(List<DataSetMarker<?>> dataSetMarker, OutputStream outputStream) {
        try (JsonGenerator jsonGenerator = this.jsonFactory.createGenerator(
                new OutputStreamWriter(outputStream, Charset.forName(UTF_8)))) {
            ObjectWriter objectWriter = objectMapper.writer();

            jsonGenerator.writeStartArray();

            for (DataSetMarker<?> objectDataSetMarker : dataSetMarker) {
                // start
                jsonGenerator.writeStartObject();

                // metadata
                jsonGenerator.writeStringField(GROUP, objectDataSetMarker.getGroup());
                jsonGenerator.writeStringField(NAME, objectDataSetMarker.getName());

                // items
                jsonGenerator.writeArrayFieldStart(ITEMS);
                objectDataSetMarker.getItems()
                        .forEach((Throwing.Consumer<Object, IOException>) item -> objectWriter.writeValue(jsonGenerator,
                                item));
                jsonGenerator.writeEndArray();

                // end
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();

            jsonGenerator.flush();
        } catch (IOException e) {
            throw BusinessException.wrap(e, DataErrorCode.EXPORT_FAILED);
        }
    }

    @SuppressWarnings("unchecked")
    private DataExporter<Object> getExporterInstance(Class<?> exportedClass) {
        return injector.getInstance(
                (Key<DataExporter<Object>>) Key.get(Types.newParameterizedType(DataExporter.class, exportedClass)));
    }

    @SuppressWarnings("unchecked")
    private DataImporter<Object> getImporterInstance(Class<?> importedClass) {
        return injector.getInstance(
                (Key<DataImporter<Object>>) Key.get(Types.newParameterizedType(DataImporter.class, importedClass)));
    }

    private enum ParsingState {
        START, DEFINITION_START, DEFINITION_GROUP, DEFINITION_NAME, DEFINITION_ITEMS, DEFINITION_END, END
    }
}
