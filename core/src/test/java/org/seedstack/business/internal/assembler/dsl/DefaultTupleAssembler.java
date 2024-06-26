/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.BaseTupleAssembler;

class DefaultTupleAssembler<T extends Tuple, D> extends BaseTupleAssembler<T, D> {

    DefaultTupleAssembler(Class<D> dtoClass) {
        super(dtoClass);
    }

    @Override
    public void mergeAggregateIntoDto(T sourceAggregate, D targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, T targetAggregate) {

    }
}
