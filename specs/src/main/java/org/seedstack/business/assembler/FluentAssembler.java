/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;

import java.util.List;

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
     * @return a DtoAssemblerProvider
     */
    AssembleSingleWithQualifier assemble(AggregateRoot<?> aggregateRoot);

    /**
     * Assembles a list of aggregates.
     *
     * @param aggregateRoots the list of aggregate roots
     * @return a DtosAssemblerProvider
     */
    AssembleMultipleWithQualifier assemble(List<? extends AggregateRoot<?>> aggregateRoots);

    /**
     * Assembles a tuple of aggregates.
     *
     * @param aggregateRoots the tuple of aggregate roots
     * @return a DtoAssemblerProvider
     */
    AssembleSingleWithQualifier assembleTuple(Tuple aggregateRoots);

    /**
     * Assembles a list of tuple of aggregates.
     *
     * @param aggregateRoots the list of tuple of aggregate roots
     * @return a DtosAssemblerProvider
     */
    AssembleMultipleWithQualifier assembleTuple(List<? extends Tuple> aggregateRoots);

    /**
     * Merges a DTO.
     *
     * @param dto the dto to merge
     * @param <D> the DTO type
     * @return a MergeAggregateOrTupleWithQualifierProvider
     */
    <D> MergeSingleWithQualifier<D> merge(D dto);

    /**
     * Merges a list of DTOs.
     *
     * @param dtos the list of DTOs
     * @param <D>  the DTO type
     * @return a MergeAggregatesOrTuplesWithQualifierProvider
     */
    <D> MergeMultipleWithQualifier<D> merge(List<D> dtos);

}
