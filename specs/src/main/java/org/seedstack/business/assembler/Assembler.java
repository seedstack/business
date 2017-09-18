/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.seedstack.business.assembler.dsl.FluentAssembler;

/**
 * This interface represents the Assembler pattern.
 * <p>
 * It provides two types of transformations:
 * </p>
 * <ul>
 * <li>assembling data from one or multiple aggregates into a representation of the model or a part of the model.</li>
 * <li>merging data from a DTO into one or multiple aggregates</li>
 * </ul>
 *
 * @param <A> the aggregate root
 * @param <D> the dto type
 * @see FluentAssembler
 */
public interface Assembler<A, D> {

    /**
     * Creates a new DTO and merge the given aggregate into it. Method {@link #createDto()} is called to create the DTO
     * instance, then {@link #mergeAggregateIntoDto(Object, Object)} is called to do the merge.
     *
     * @param sourceAggregate The source aggregate
     * @return the resulting dto
     */
    default D createDtoFromAggregate(A sourceAggregate) {
        D dto = createDto();
        mergeAggregateIntoDto(sourceAggregate, dto);
        return dto;
    }

    /**
     * Updates an existing DTO with a source aggregate root.
     *
     * @param sourceAggregate The source aggregate
     * @param targetDto       The target dto
     */
    void mergeAggregateIntoDto(A sourceAggregate, D targetDto);

    /**
     * Merges a source DTO into an existing aggregate root.
     *
     * @param sourceDto       The source dto
     * @param targetAggregate The target aggregate
     */
    void mergeDtoIntoAggregate(D sourceDto, A targetAggregate);

    /**
     * Returns the DTO type handled by the assembler.
     * <p>
     * This method is used by {@link #createDtoFromAggregate(Object)}
     * to determine the DTO type to instantiate.
     * </p>
     *
     * @return the dto class
     */
    Class<D> getDtoClass();

    /**
     * This protected method is in charge of creating a new instance of the DTO.
     * <p>
     * The actual implementation is fine for simple POJO, but it can be
     * extended. The developers will then use {@link #getDtoClass()} to retrieve
     * the destination class.
     * </p>
     *
     * @return the DTO instance.
     */
    D createDto();
}
