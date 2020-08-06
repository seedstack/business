/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

class MergeSingleAggregateFromRepositoryImpl<A extends AggregateRoot<I>, I, D> implements MergeFromRepository<A>,
        MergeFromRepositoryOrFactory<A> {

    private final MergeMultipleAggregatesFromRepositoryImpl<A, I, D> multipleMerger;

    MergeSingleAggregateFromRepositoryImpl(Context context, D dto, Class<A> aggregateRootClass) {
        multipleMerger = new MergeMultipleAggregatesFromRepositoryImpl<>(context, Stream.of(dto), aggregateRootClass);
    }

    @Override
    public MergeFromRepositoryOrFactory<A> fromRepository() {
        // no call because no qualifier to set (see below)
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<A> fromRepository(Annotation qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<A> fromRepository(String qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
        return this;
    }

    @Override
    public MergeFromRepositoryOrFactory<A> fromRepository(Class<? extends Annotation> qualifier) {
        // keep the call below for the side-effect of setting the qualifier in the DSL context
        multipleMerger.fromRepository(qualifier);
        return this;
    }

    @Override
    public A fromFactory() {
        return multipleMerger.fromFactory()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A fromFactory(Annotation qualifier) {
        return multipleMerger.fromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A fromFactory(String qualifier) {
        return multipleMerger.fromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A fromFactory(Class<? extends Annotation> qualifier) {
        return multipleMerger.fromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A orFail() throws AggregateNotFoundException {
        return multipleMerger.orFail()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A orFromFactory() {
        return multipleMerger.orFromFactory()
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A orFromFactory(Annotation qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A orFromFactory(String qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }

    @Override
    public A orFromFactory(Class<? extends Annotation> qualifier) {
        return multipleMerger.orFromFactory(qualifier)
                .asStream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
    }
}
