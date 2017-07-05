/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.Slice;

import java.util.List;
import java.util.stream.Stream;

/**
 * FluentAssembler provides the entry point for the assembler DSL.
 * <p>
 * As {@link org.seedstack.business.assembler.Assembler} it allows to assemble aggregate
 * to DTOs, or merge DTOs into aggregates. But compare to {@code Assembler}, it's not typed so you can
 * do it programmatically, the DSL will find the appropriate assembler to call. It also provides more
 * advanced features like automatically retrieving an aggregate from its repository. Or automatically
 * creating it from its factory.
 * </p>
 */
public interface FluentAssembler {

    /**
     * Assembles an aggregate root.
     *
     * @param aggregateRoot the aggregate root
     * @return the next type for FluentAssembler DSL.
     */
    AssembleSingleWithQualifier assemble(AggregateRoot<?> aggregateRoot);

    /**
     * Assembles a list of aggregates.
     *
     * @param aggregateRoots the list of aggregate roots
     * @return the next type for FluentAssembler DSL.
     */
    AssembleMultipleWithQualifier assemble(List<? extends AggregateRoot<?>> aggregateRoots);

    /**
     * Assembles a tuple of aggregates.
     *
     * @param aggregateRoots the tuple of aggregate roots
     * @return the next type for FluentAssembler DSL.
     */
    AssembleSingleWithQualifier assembleTuple(Tuple aggregateRoots);

    /**
     * Assembles a list of tuple of aggregates.
     *
     * @param aggregateRoots the list of tuple of aggregate roots
     * @return the next type for FluentAssembler DSL.
     */
    AssembleMultipleWithQualifier assembleTuple(List<? extends Tuple> aggregateRoots);

    /**
     * Assembles a slice of aggregates.
     *
     * @param slice the slice to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    AssembleSliceWithQualifier assemble(Slice<? extends AggregateRoot<?>> slice);

    /**
     * Assembles a page of aggregates.
     *
     * @param page the page to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    AssemblePageWithQualifier assemble(Page<? extends AggregateRoot<?>> page);

    /**
     * Assembles a list of aggregates inside a Chunk.
     *
     * @param stream the Stream to assemble
     * @return a dtosAssemblerProvider
     */
    AssembleStreamWithQualifier assemble(Stream<?> stream);

    /**
     * Merges a DTO.
     *
     * @param dto the dto to merge
     * @param <D> the DTO type
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeSingleWithQualifier<D> merge(D dto);

    /**
     * Merges a list of DTOs.
     *
     * @param dtos the list of DTOs
     * @param <D>  the DTO type
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeMultipleWithQualifier<D> merge(List<D> dtos);
}
