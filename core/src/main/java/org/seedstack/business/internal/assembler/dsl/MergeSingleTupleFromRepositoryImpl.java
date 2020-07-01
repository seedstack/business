/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import java.util.stream.Stream;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;

class MergeSingleTupleFromRepositoryImpl<T extends Tuple, D> implements MergeFromRepository<T>,
        MergeFromRepositoryOrFactory<T> {

    private final MergeMultipleTuplesFromRepositoryImpl<T, D> multipleMerger;

    @SafeVarargs
    MergeSingleTupleFromRepositoryImpl(Context context, D dto, Class<? extends AggregateRoot<?>>... aggregateClasses) {
        multipleMerger = new MergeMultipleTuplesFromRepositoryImpl<>(context, Stream.of(dto), aggregateClasses);
    }

    @Override
    public MergeFromRepositoryOrFactory<T> fromRepository() {
        return this;
    }

    @Override
    public T fromFactory() {
        return multipleMerger.fromFactory()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T orFail() throws AggregateNotFoundException {
        return multipleMerger.orFail()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T orFromFactory() {
        return multipleMerger.orFromFactory()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }
}
