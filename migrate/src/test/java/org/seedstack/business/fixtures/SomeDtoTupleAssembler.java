/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures;

import org.javatuples.Unit;
import org.seedstack.business.assembler.LegacyBaseTupleAssembler;

public class SomeDtoTupleAssembler extends LegacyBaseTupleAssembler<Unit<SomeAggregate>, SomeDto> {

    @Override
    protected void doAssembleDtoFromAggregate(SomeDto targetDto, Unit<SomeAggregate> sourceAggregate) {
        targetDto.setId(String.valueOf(sourceAggregate.getValue0().getEntityId()));
    }

    @Override
    protected void doMergeAggregateWithDto(Unit<SomeAggregate> targetAggregate, SomeDto sourceDto) {
        // id is handled by factory
    }
}
