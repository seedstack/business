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
     * Assembles an aggregate.
     *
     * @param aggregateRoot the aggregate root to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<ID>, ID> AssembleSingleWithQualifier assemble(A aggregateRoot);

    /**
     * Assembles a stream of aggregates.
     *
     * @param stream the stream of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<ID>, ID> AssembleMultipleWithQualifier assemble(Stream<A> stream);

    /**
     * Assembles an iterable of aggregates.
     *
     * @param iterable the iterable of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<ID>, ID> AssembleMultipleWithQualifier assemble(Iterable<A> iterable);

    /**
     * Assembles a page of aggregates.
     *
     * @param page the page of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<ID>, ID> AssemblePageWithQualifier assemble(Page<A> page);

    /**
     * Assembles a tuple of aggregates into a single DTO.
     *
     * @param tuple the tuple of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleSingleWithQualifier assembleTuple(T tuple);

    /**
     * Assembles a stream of tuple of aggregates.
     *
     * @param stream the stream of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Stream<T> stream);

    /**
     * Assembles an iterable of tuple of aggregates.
     *
     * @param iterable the iterable of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Iterable<T> iterable);

    /**
     * Assembles a page of tuple of aggregates.
     *
     * @param page the page of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssemblePageWithQualifier assembleTuples(Page<T> page);

    /**
     * Merges a DTO back into an aggregate.
     *
     * @param dto the dto to merge
     * @param <D> the DTO type.
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeSingleWithQualifier merge(D dto);

    /**
     * Merges a stream of DTO back into aggregates.
     *
     * @param stream the stream of DTO.
     * @param <D>    the DTO type.
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeMultipleWithQualifier merge(Stream<D> stream);

    /**
     * Merges an iterable of DTO back into aggregates.
     *
     * @param iterable the iterable of DTO.
     * @param <D>      the DTO type.
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeMultipleWithQualifier merge(Iterable<D> iterable);
}
