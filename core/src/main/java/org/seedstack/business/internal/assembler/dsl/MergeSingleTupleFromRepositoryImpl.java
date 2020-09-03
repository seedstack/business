/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

class MergeSingleTupleFromRepositoryImpl<T extends Tuple, D> implements MergeFromRepository<T>,
        MergeFromRepositoryOrFactory<T> {

    private final MergeMultipleTuplesFromRepositoryImpl<T, D> multipleMerger;

    @SafeVarargs
    MergeSingleTupleFromRepositoryImpl(Context context, D dto, Class<? extends AggregateRoot<?>>... aggregateClasses) {
        multipleMerger = new MergeMultipleTuplesFromRepositoryImpl<>(context, Stream.of(dto), aggregateClasses);
    }

    @Override
    public MergeFromRepositoryOrFactory<T> fromRepository() {
        // no call because no qualifier to set (see below)
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<T> fromRepository(Annotation qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<T> fromRepository(String qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<T> fromRepository(Class<? extends Annotation> qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
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
    public T fromFactory(Annotation qualifier) {
        return multipleMerger.fromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T fromFactory(String qualifier) {
        return multipleMerger.fromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T fromFactory(Class<? extends Annotation> qualifier) {
        return multipleMerger.fromFactory(qualifier)
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

    @Override
    public T orFromFactory(Annotation qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T orFromFactory(String qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public T orFromFactory(Class<? extends Annotation> qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }
}
