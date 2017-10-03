/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.data;

import org.seedstack.business.assembler.BaseAssembler;

public class SomeDtoAssembler extends BaseAssembler<SomeAggregate, SomeDto> {
    @Override
    public void mergeAggregateIntoDto(SomeAggregate sourceAggregate, SomeDto targetDto) {
        targetDto.setId(sourceAggregate.getId());
        targetDto.setFirstName(sourceAggregate.getFirstName());
        targetDto.setLastName(sourceAggregate.getLastName());
    }

    @Override
    public void mergeDtoIntoAggregate(SomeDto sourceDto, SomeAggregate targetAggregate) {
        // id is already handled by factory
        targetAggregate.setFirstName(sourceDto.getFirstName());
        targetAggregate.setLastName(sourceDto.getLastName());
        targetAggregate.setAge(99);
    }
}
