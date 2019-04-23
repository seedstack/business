/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.seedstack.business.domain.AggregateRoot;

@Deprecated
public abstract class LegacyBaseAssembler<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D>
        implements LegacyAssembler<A, D> {
    @Override
    public D assembleDtoFromAggregate(A sourceAggregate) {
        D newDto = createDto();
        doAssembleDtoFromAggregate(newDto, sourceAggregate);
        return newDto;
    }

    protected abstract void doAssembleDtoFromAggregate(D targetDto, A sourceAggregate);

    protected abstract void doMergeAggregateWithDto(A targetAggregate, D sourceDto);

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        doAssembleDtoFromAggregate(targetDto, sourceAggregate);
    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
        doMergeAggregateWithDto(targetAggregate, sourceDto);
    }
}
