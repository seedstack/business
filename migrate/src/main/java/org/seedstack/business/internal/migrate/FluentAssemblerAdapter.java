/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import java.util.stream.Stream;
import javax.inject.Inject;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;

class FluentAssemblerAdapter implements FluentAssembler {
    private final org.seedstack.business.assembler.dsl.FluentAssembler fluentAssembler;

    @Inject
    FluentAssemblerAdapter(org.seedstack.business.assembler.dsl.FluentAssembler fluentAssembler) {
        this.fluentAssembler = fluentAssembler;
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleSingleWithQualifier assemble(
            A aggregateRoot) {
        return fluentAssembler.assemble(aggregateRoot);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(
            Stream<A> stream) {
        return fluentAssembler.assemble(stream);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(
            Iterable<A> iterable) {
        return fluentAssembler.assemble(iterable);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssemblePageWithQualifier assemble(
            Page<A> page) {
        return fluentAssembler.assemble(page);
    }

    @Override
    public <T extends Tuple> AssembleSingleWithQualifier assembleTuple(
            T tuple) {
        return fluentAssembler.assembleTuple(tuple);
    }

    @Override
    public <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(
            Stream<T> stream) {
        return fluentAssembler.assembleTuples(stream);
    }

    @Override
    public <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(
            Iterable<T> iterable) {
        return fluentAssembler.assembleTuples(iterable);
    }

    @Override
    public <T extends Tuple> AssemblePageWithQualifier assembleTuples(
            Page<T> page) {
        return fluentAssembler.assembleTuples(page);
    }

    @Override
    public <D> MergeSingleWithQualifier merge(D dto) {
        return fluentAssembler.merge(dto);
    }

    @Override
    public <D> MergeMultipleWithQualifier merge(Stream<D> stream) {
        return fluentAssembler.merge(stream);
    }

    @Override
    public <D> MergeMultipleWithQualifier merge(Iterable<D> iterable) {
        return fluentAssembler.merge(iterable);
    }
}
