/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.javatuples.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.LegacyAssembler;
import org.seedstack.business.fixtures.SomeAggregate;
import org.seedstack.business.fixtures.SomeDto;
import org.seedstack.business.fixtures.SomeDtoAssembler;
import org.seedstack.business.fixtures.SomeDtoTupleAssembler;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class AssemblerIT {
    @Inject
    private FluentAssembler fluentAssembler;
    @Inject
    private Assembler<SomeAggregate, SomeDto> someDtoAssembler;
    @Inject
    private LegacyAssembler<SomeAggregate, SomeDto> someDtoLegacyAssembler;
    @Inject
    private Assembler<Unit<SomeAggregate>, SomeDto> someDtoTupleAssembler;
    @Inject
    private LegacyAssembler<Unit<SomeAggregate>, SomeDto> someDtoLegacyTupleAssembler;

    @Test
    public void fluentAssemblerIsInjected() {
        assertThat(fluentAssembler).isInstanceOf(FluentAssemblerAdapter.class);
    }

    @Test
    public void fluentAssemblerIsWorking() {
        assertThat(fluentAssembler.assemble(new SomeAggregate(1L)).to(SomeDto.class).getId()).isEqualTo("1");
        assertThat(fluentAssembler.assembleTuple(new Unit<>(new SomeAggregate(1L))).to(SomeDto.class).getId())
                .isEqualTo("1");
        assertThat(fluentAssembler.merge(new SomeDto("1")).into(SomeAggregate.class).fromFactory().getId())
                .isEqualTo(1L);
    }

    @Test
    public void assemblersAreInjected() {
        assertThat(someDtoAssembler).isInstanceOf(SomeDtoAssembler.class);
        assertThat(someDtoLegacyAssembler).isInstanceOf(LegacyAssemblerAdapter.class);
    }

    @Test
    public void tupleAssemblersAreInjected() {
        assertThat(someDtoTupleAssembler).isInstanceOf(SomeDtoTupleAssembler.class);
        assertThat(someDtoLegacyTupleAssembler).isInstanceOf(LegacyAssemblerAdapter.class);
    }

    @Test
    public void assemblersAreWorking() {
        assertThat(someDtoAssembler.createDtoFromAggregate(new SomeAggregate(2L)).getId()).isEqualTo("2");
        assertThat(someDtoLegacyAssembler.assembleDtoFromAggregate(new SomeAggregate(3L)).getId()).isEqualTo("3");
    }

    @Test
    public void tupleAssemblersAreWorking() {
        assertThat(someDtoTupleAssembler.createDtoFromAggregate(new Unit<>(new SomeAggregate(2L))).getId())
                .isEqualTo("2");
        assertThat(someDtoLegacyTupleAssembler.assembleDtoFromAggregate(new Unit<>(new SomeAggregate(3L))).getId())
                .isEqualTo("3");
    }

    @Test(expected = BusinessException.class)
    public void fluentAssemblerCanUseAssemblerTypes() {
        fluentAssembler.assemble(new SomeAggregate(1L)).with(AssemblerTypes.MODEL_MAPPER).to(SomeDto.class);
    }
}
