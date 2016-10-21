/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.fixtures.MyAggregateRoot;
import org.seedstack.business.assembler.fixtures.MyAssembler;
import org.seedstack.business.assembler.fixtures.MyDto;
import org.seedstack.business.assembler.fixtures.MySecuredAssembler;
import org.seedstack.business.assembler.fixtures.MyUnrestrictedDto;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.WithUser;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SeedITRunner.class)
public class BaseAssemblerIT {
    @Inject
    private MyAssembler normalAssembler;
    @Inject
    private MySecuredAssembler mySecuredAssembler;

    @Test
    public void class_returned_by_dto_should_work_fine() {
        Assertions.assertThat(normalAssembler.getDtoClass()).isEqualTo(MyUnrestrictedDto.class);
    }

    @Test
    public void entity_to_dto_way_should_work_fine() {
        MyAggregateRoot entity1 = new MyAggregateRoot();
        entity1.setFirstName("Epo");
        entity1.setLastName("Jemba");
        entity1.setAge(35);
        entity1.setAddress("1 rue de la paix, 75001, Paris");

        MyUnrestrictedDto dto1 = normalAssembler.assembleDtoFromAggregate(entity1);
        assertThat(dto1).isNotNull();
        assertThat(dto1.getNom()).isEqualTo("Jemba, Epo");
        assertThat(dto1.getAge()).isEqualTo(35);
        assertThat(dto1.getAddresse()).isEqualTo("1 rue de la paix, 75001, Paris");
    }

    @Test
    @WithUser(id = "Anakin", password = "imsodark")
    public void entity_to_dto_way_should_work_fine_with_secured_assembler() {
        MyAggregateRoot entity1 = new MyAggregateRoot();
        entity1.setFirstName("Epo");
        entity1.setLastName("Jemba");
        entity1.setAge(35);
        entity1.setAddress("1 rue de la paix, 75001, Paris");

        MyDto dto1 = mySecuredAssembler.assembleDtoFromAggregate(entity1);
        assertThat(dto1).isNotNull();
        assertThat(dto1.getNom()).isEqualTo("Jemba, Epo");
        assertThat(dto1.getAge()).isEqualTo(0);
        assertThat(dto1.getAddresse()).isEqualTo("1 rue de la paix, 75001, Paris");
    }

    @Test
    public void dto_to_entity_way_should_work_fine() {
        MyUnrestrictedDto dto1 = new MyUnrestrictedDto();
        dto1.setNom("Lauer, Adrien");
        dto1.setAge(32);
        dto1.setAddresse("rue des hotels, paris, 75001");

        MyAggregateRoot entity1 = new MyAggregateRoot();

        normalAssembler.mergeAggregateWithDto(entity1, dto1);

        assertThat(entity1.getFirstName()).isEqualTo("Adrien");
        assertThat(entity1.getLastName()).isEqualTo("Lauer");
        assertThat(entity1.getAge()).isEqualTo(32);
        assertThat(entity1.getAddress()).isEqualTo("rue des hotels, paris, 75001");
    }
}
