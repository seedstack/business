/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.spi.DtoInfoResolver;

/**
 * Implementation of {@link FluentAssembler}. <p> It uses a Guice provider to get the DSL entry
 * point. Each time you call the {@code assemble()} method a new DSL instance is provided. </p>
 */
public class FluentAssemblerImpl implements FluentAssembler {

    private Context context;

    @Inject
    public FluentAssemblerImpl(DomainRegistry domainRegistry, AssemblerRegistry registry,
            Set<DtoInfoResolver> dtoInfoResolvers) {
        context = new Context(domainRegistry, registry, dtoInfoResolvers);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleSingleWithQualifier assemble(A aggregateRoot) {
        return new AssembleSingleImpl<>(context, aggregateRoot, null);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(Iterable<A> iterable) {
        return new AssembleMultipleImpl<>(context, StreamSupport.stream(iterable.spliterator(), false), null);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(Stream<A> stream) {
        return new AssembleMultipleImpl<>(context, stream, null);
    }

    @Override
    public <A extends AggregateRoot<I>, I> AssemblePageWithQualifier assemble(Page<A> page) {
        return new AssemblePageImpl<>(context, page, null);
    }

    @Override
    public <T extends Tuple> AssembleSingleWithQualifier assembleTuple(T tuple) {
        return new AssembleSingleImpl<>(context, null, tuple);
    }

    @Override
    public <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Stream<T> stream) {
        return new AssembleMultipleImpl<>(context, null, stream);
    }

    @Override
    public <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Iterable<T> iterable) {
        return new AssembleMultipleImpl<>(context, null, StreamSupport.stream(iterable.spliterator(), false));
    }

    @Override
    public <T extends Tuple> AssemblePageWithQualifier assembleTuples(Page<T> page) {
        return new AssemblePageImpl<>(context, null, page);
    }

    @Override
    public <D> MergeSingleWithQualifier merge(D dto) {
        return new MergeSingleImpl<>(context, dto);
    }

    @Override
    public <D> MergeMultipleWithQualifier merge(Stream<D> stream) {
        return new MergeMultipleImpl<>(context, stream);
    }

    @Override
    public <D> MergeMultipleWithQualifier merge(Iterable<D> iterable) {
        return new MergeMultipleImpl<>(context, StreamSupport.stream(iterable.spliterator(), false));
    }
}
