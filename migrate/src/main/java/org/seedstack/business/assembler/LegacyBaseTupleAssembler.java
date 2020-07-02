/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler;

import org.javatuples.Tuple;

@Deprecated
public abstract class LegacyBaseTupleAssembler<T extends Tuple, D> extends BaseTupleAssembler<T, D>
        implements LegacyAssembler<T, D> {
    @Override
    public D assembleDtoFromAggregate(T sourceAggregate) {
        D newDto = createDto();
        doAssembleDtoFromAggregate(newDto, sourceAggregate);
        return newDto;
    }

    protected abstract void doAssembleDtoFromAggregate(D targetDto, T sourceAggregate);

    protected abstract void doMergeAggregateWithDto(T targetAggregate, D sourceDto);

    @Override
    public void mergeAggregateIntoDto(T sourceAggregate, D targetDto) {
        doAssembleDtoFromAggregate(targetDto, sourceAggregate);
    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, T targetAggregate) {
        doMergeAggregateWithDto(targetAggregate, sourceDto);
    }
}
