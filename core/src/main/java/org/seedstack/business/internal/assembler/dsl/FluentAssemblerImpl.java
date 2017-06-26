/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.dsl.*;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Chunk;
import org.seedstack.business.pagination.Page;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link org.seedstack.business.assembler.FluentAssembler}.
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
    public AssemblePageWithQualifier assemble(Page pagination) {
        return new AssemblePageImpl(context, pagination);
    }

    @Override
    public AssembleChunkWithQualifier assemble(Chunk pagination) {
        return new AssembleChunkImpl(context, pagination);
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
