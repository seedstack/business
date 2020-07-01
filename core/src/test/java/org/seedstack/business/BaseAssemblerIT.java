/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.fixtures.assembler.MyAggregateRoot;
import org.seedstack.business.fixtures.assembler.MyUnrestrictedDto;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class BaseAssemblerIT {
    @Inject
    private Assembler<MyAggregateRoot, MyUnrestrictedDto> normalAssembler;

    @Test
    public void class_returned_by_dto_should_work_fine() {
        Assertions.assertThat(normalAssembler.getDtoClass())
                .isEqualTo(MyUnrestrictedDto.class);
    }

    @Test
    public void entity_to_dto_way_should_work_fine() {
        MyAggregateRoot entity1 = new MyAggregateRoot();
        entity1.setFirstName("Epo");
        entity1.setLastName("Jemba");
        entity1.setAge(35);
        entity1.setAddress("1 rue de la paix, 75001, Paris");

        MyUnrestrictedDto dto1 = normalAssembler.createDtoFromAggregate(entity1);
        assertThat(dto1).isNotNull();
        assertThat(dto1.getName()).isEqualTo("Jemba, Epo");
        assertThat(dto1.getAge()).isEqualTo(35);
        assertThat(dto1.getAddress()).isEqualTo("1 rue de la paix, 75001, Paris");
    }

    @Test
    public void dto_to_entity_way_should_work_fine() {
        MyUnrestrictedDto dto1 = new MyUnrestrictedDto();
        dto1.setName("Lauer, Adrien");
        dto1.setAge(32);
        dto1.setAddress("rue des hotels, paris, 75001");

        MyAggregateRoot entity1 = new MyAggregateRoot();

        normalAssembler.mergeDtoIntoAggregate(dto1, entity1);

        assertThat(entity1.getFirstName()).isEqualTo("Adrien");
        assertThat(entity1.getLastName()).isEqualTo("Lauer");
        assertThat(entity1.getAge()).isEqualTo(32);
        assertThat(entity1.getAddress()).isEqualTo("rue des hotels, paris, 75001");
    }
}
