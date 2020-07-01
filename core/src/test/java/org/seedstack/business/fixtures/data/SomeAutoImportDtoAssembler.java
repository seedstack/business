/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.data;

import org.seedstack.business.assembler.BaseAssembler;

public class SomeAutoImportDtoAssembler extends BaseAssembler<OtherAggregate, SomeAutoImportDto> {
    @Override
    public void mergeAggregateIntoDto(OtherAggregate sourceAggregate, SomeAutoImportDto targetDto) {
        targetDto.setId(sourceAggregate.getId());
        targetDto.setFirstName(sourceAggregate.getFirstName());
        targetDto.setLastName(sourceAggregate.getLastName());
        targetDto.setAge(sourceAggregate.getAge());
    }

    @Override
    public void mergeDtoIntoAggregate(SomeAutoImportDto sourceDto, OtherAggregate targetAggregate) {
        // id is already handled by factory
        targetAggregate.setFirstName(sourceDto.getFirstName());
        targetAggregate.setLastName(sourceDto.getLastName());
        targetAggregate.setAge(sourceDto.getAge());
    }
}
