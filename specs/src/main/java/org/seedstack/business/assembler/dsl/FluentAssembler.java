/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import java.util.stream.Stream;
import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;

/**
 * FluentAssembler is a DSL aimed at automating the most frequent tasks that occurs when mapping
 * aggregates to Data Transfer Objects (DTO) and back. It works seamlessly on individual objects,
 * {@link Stream}s, {@link Iterable}s, and {@link Page}s. It also works with tuples of aggregates.
 *
 * <p> When merging a DTO back into aggregate(s), FluentAssembler can use a {@link
 * org.seedstack.business.domain.Repository} and/or a {@link org.seedstack.business.domain.Factory}
 * to obtain the aggregate(s). </p>
 */
public interface FluentAssembler {

    /**
     * Assembles an aggregate.
     *
     * @param <A>           the type of the aggregate root.
     * @param <I>           the type of the aggregate root identifier.
     * @param aggregateRoot the aggregate root to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<I>, I> AssembleSingleWithQualifier assemble(A aggregateRoot);

    /**
     * Assembles a stream of aggregates.
     *
     * @param <A>    the type of the aggregate root.
     * @param <I>    the type of the aggregate root identifier.
     * @param stream the stream of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(Stream<A> stream);

    /**
     * Assembles an iterable of aggregates.
     *
     * @param <A>      the type of the aggregate root.
     * @param <I>      the type of the aggregate root identifier.
     * @param iterable the iterable of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<I>, I> AssembleMultipleWithQualifier assemble(Iterable<A> iterable);

    /**
     * Assembles a page of aggregates.
     *
     * @param <A>  the type of the aggregate root.
     * @param <I>  the type of the aggregate root identifier.
     * @param page the page of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <A extends AggregateRoot<I>, I> AssemblePageWithQualifier assemble(Page<A> page);

    /**
     * Assembles a tuple of aggregates into a single DTO.
     *
     * @param <T>   the type of the tuple.
     * @param tuple the tuple of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleSingleWithQualifier assembleTuple(T tuple);

    /**
     * Assembles a stream of tuple of aggregates.
     *
     * @param <T>    the type of the tuple.
     * @param stream the stream of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Stream<T> stream);

    /**
     * Assembles an iterable of tuple of aggregates.
     *
     * @param <T>      the type of the tuple.
     * @param iterable the iterable of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssembleMultipleWithQualifier assembleTuples(Iterable<T> iterable);

    /**
     * Assembles a page of tuple of aggregates.
     *
     * @param <T>  the type of the tuple.
     * @param page the page of tuples of aggregate roots to assemble.
     * @return the next type for FluentAssembler DSL.
     */
    <T extends Tuple> AssemblePageWithQualifier assembleTuples(Page<T> page);

    /**
     * Merges a DTO back into an aggregate.
     *
     * @param <D> the DTO type.
     * @param dto the dto to merge
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeSingleWithQualifier merge(D dto);

    /**
     * Merges a stream of DTO back into aggregates.
     *
     * @param <D>    the DTO type.
     * @param stream the stream of DTO.
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeMultipleWithQualifier merge(Stream<D> stream);

    /**
     * Merges an iterable of DTO back into aggregates.
     *
     * @param <D>      the DTO type.
     * @param iterable the iterable of DTO.
     * @return the next type for FluentAssembler DSL.
     */
    <D> MergeMultipleWithQualifier merge(Iterable<D> iterable);
}
