/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.migrate;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.LegacyAssembler;

class LegacyAssemblerAdapter<A, D> implements LegacyAssembler<A, D> {
    private final Assembler<A, D> assembler;

    LegacyAssemblerAdapter(Assembler<A, D> assembler) {
        this.assembler = assembler;
    }

    @Override
    public D createDtoFromAggregate(A sourceAggregate) {
        return assembler.createDtoFromAggregate(sourceAggregate);
    }

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        assembler.mergeAggregateIntoDto(sourceAggregate, targetDto);
    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
        assembler.mergeDtoIntoAggregate(sourceDto, targetAggregate);
    }

    @Override
    public D createDto() {
        return assembler.createDto();
    }

    @Override
    public Class<D> getDtoClass() {
        return assembler.getDtoClass();
    }
}
