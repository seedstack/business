/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSliceWithQualifier;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.assembler.dsl.AssembleStreamWithQualifier;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementation of {@link FluentAssembler}.
 * <p>
 * It uses a Guice provider to get the DSL entry point. Each time you call the {@code assemble()}
 * method a new DSL instance is provided.
 * </p>
 */
public class FluentAssemblerImpl implements FluentAssembler {

    private AssemblerDslContext context;

    @Inject
    public FluentAssemblerImpl(InternalRegistry registry) {
        context = new AssemblerDslContext();
        context.setRegistry(registry);
    }

    @Override
    public AssembleSingleWithQualifier assemble(AggregateRoot<?> aggregateRoot) {
        return new AssembleSingleImpl(context, aggregateRoot);
    }

    @Override
    public AssembleMultipleWithQualifier assemble(List<? extends AggregateRoot<?>> aggregateRoots) {
        return new AssembleMultipleImpl(context, aggregateRoots, null);
    }

    @Override
    public AssembleSingleWithQualifier assembleTuple(Tuple aggregateRoots) {
        return new AssembleSingleImpl(context, aggregateRoots);
    }

    @Override
    public AssembleMultipleWithQualifier assembleTuple(List<? extends Tuple> aggregateRoots) {
        return new AssembleMultipleImpl(context, null, aggregateRoots);
    }

    @Override
    public AssemblePageWithQualifier assemble(Page<? extends AggregateRoot<?>> page) {
        return new AssemblePageImpl(context, page);
    }

    @Override
    public AssembleSliceWithQualifier assemble(Slice<? extends AggregateRoot<?>> slice) {
        return new AssembleSliceImpl(context, slice);
    }

    @Override
    public AssembleStreamWithQualifier assemble(Stream stream) {
        return new AssembleStreamImpl(context, stream);
    }

    @Override
    public <D> MergeSingleWithQualifier<D> merge(D dto) {
        return new MergeSingleImpl<>(context, dto);
    }

    @Override
    public <D> MergeMultipleWithQualifier<D> merge(List<D> dtos) {
        return new MergeMultipleImpl<>(context, dtos);
    }
}
