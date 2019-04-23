/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.data.DataExporter;
import org.seedstack.business.data.DataImporter;
import org.seedstack.business.data.DataManager;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.data.OtherAggregate;
import org.seedstack.business.fixtures.data.SomeAggregate;
import org.seedstack.business.fixtures.data.SomeCustomDto;
import org.seedstack.business.fixtures.data.SomeCustomDtoExporter;
import org.seedstack.business.fixtures.data.SomeCustomDtoImporter;
import org.seedstack.business.fixtures.data.SomeDto;
import org.seedstack.business.internal.data.DefaultDataExporter;
import org.seedstack.business.internal.data.DefaultDataImporter;
import org.seedstack.business.specification.Specification;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.skyscreamer.jsonassert.JSONAssert;

@RunWith(SeedITRunner.class)
public class DataImportExportIT {
    private static final String COMMON = "[{\"group\":\"org.seedstack.business.fixtures.data\","
            + "\"name\":\"SomeCustomDto\","
            + "\"items\":[{\"id\":\"bstark\",\"firstName\":\"Brandon\",\"lastName\":\"STARK\","
            + "\"age\":48}]},"
            + "{\"group\":\"test\",\"name\":\"test\",\"items\":[{\"id\":\"astark\",\"firstName\":\"Arya\","
            + "\"lastName\":\"STARK\"}]}";
    private static final String EXPORTED = COMMON
            + ","
            + "{\"group\":\"auto\",\"name\":\"importTest\",\"items\":[{\"id\":\"jsnow\",\"firstName\":\"John\","
            + "\"lastName\":\"SNOW\",\"age\":29}]}]";
    private static final String IMPORTED = COMMON + "]";
    @Inject
    private Repository<SomeAggregate, String> someAggregateRepository;
    @Inject
    private Repository<OtherAggregate, String> otherAggregateRepository;
    @Inject
    private DataImporter<SomeDto> defaultDataImporter;
    @Inject
    private DataExporter<SomeDto> defaultDataExporter;
    @Inject
    private DataImporter<SomeCustomDto> dataImporter;
    @Inject
    private DataExporter<SomeCustomDto> dataExporter;
    @Inject
    private DataManager dataManager;

    @Before
    public void setUp() {
        someAggregateRepository.clear();
    }

    @Test
    public void customImplementations() {
        assertThat(dataImporter).isInstanceOf(SomeCustomDtoImporter.class);
        assertThat(dataExporter).isInstanceOf(SomeCustomDtoExporter.class);
    }

    @Test
    public void defaultImplementations() {
        assertThat(defaultDataImporter).isInstanceOf(DefaultDataImporter.class);
        assertThat(defaultDataExporter).isInstanceOf(DefaultDataExporter.class);
    }

    @Test
    public void defaultImporter() {
        SomeDto someDto = new SomeDto();
        someDto.setId("test1");
        defaultDataImporter.importData(Stream.of(someDto));
        assertThat(someAggregateRepository.get("test1")).isPresent();
    }

    @Test
    public void defaultExporter() {
        someAggregateRepository.add(createSomeAggregate("tlannister", "Tyrion", "LANNISTER", 37));
        SomeDto exportedDto = defaultDataExporter.exportData()
                .collect(Collectors.toList())
                .get(0);
        assertThat(exportedDto.getId()).isEqualTo("tlannister");
        assertThat(exportedDto.getFirstName()).isEqualTo("Tyrion");
        assertThat(exportedDto.getLastName()).isEqualTo("LANNISTER");
    }

    @Test
    public void dataManager() {
        assertThat(dataManager).isNotNull();
    }

    @Test
    public void dataExport() throws JSONException {
        someAggregateRepository.add(createSomeAggregate("astark", "Arya", "STARK", 16));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dataManager.exportData(byteArrayOutputStream);
        JSONAssert.assertEquals(EXPORTED, new String(byteArrayOutputStream.toByteArray()), false);
    }

    @Test
    public void dataImport() {
        someAggregateRepository.add(createSomeAggregate("rbaratheon", "Robert", "BARATHEON", 55));
        dataManager.importData(new ByteArrayInputStream(IMPORTED.getBytes()));
        List<SomeAggregate> someAggregatesFromImporter = ((SomeCustomDtoImporter) dataImporter).getSomeAggregates();
        assertThat(someAggregatesFromImporter).hasSize(1);
        assertThat(someAggregatesFromImporter.get(0).getId()).isEqualTo("bstark");
        assertThat(someAggregatesFromImporter.get(0).getFirstName()).isEqualTo("Brandon");
        assertThat(someAggregatesFromImporter.get(0).getLastName()).isEqualTo("STARK");
        assertThat(someAggregatesFromImporter.get(0).getAge()).isEqualTo(48);

        List<SomeAggregate> someAggregatesFromRepo = someAggregateRepository.get(Specification.any())
                .collect(Collectors.toList());
        assertThat(someAggregatesFromRepo).hasSize(1);
        assertThat(someAggregatesFromRepo.get(0).getId()).isEqualTo("astark");
        assertThat(someAggregatesFromRepo.get(0).getFirstName()).isEqualTo("Arya");
        assertThat(someAggregatesFromRepo.get(0).getLastName()).isEqualTo("STARK");
        assertThat(someAggregatesFromRepo.get(0).getAge()).isEqualTo(99); // age is lost with SomeDto
    }

    @Test
    public void autoImport() {
        List<OtherAggregate> importedAggregates = otherAggregateRepository.get(Specification.any())
                .collect(Collectors.toList());
        assertThat(importedAggregates).hasSize(1);
        assertThat(importedAggregates.get(0).getId()).isEqualTo("jsnow");
        assertThat(importedAggregates.get(0).getFirstName()).isEqualTo("John");
        assertThat(importedAggregates.get(0).getLastName()).isEqualTo("SNOW");
        assertThat(importedAggregates.get(0).getAge()).isEqualTo(29);
    }

    private SomeAggregate createSomeAggregate(String id, String firstName, String lastName, int age) {
        SomeAggregate someAggregate = new SomeAggregate(id);
        someAggregate.setFirstName(firstName);
        someAggregate.setLastName(lastName);
        someAggregate.setAge(age);
        return someAggregate;
    }
}
