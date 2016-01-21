/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

/**
 * This interface represents the Assembler pattern.
 * <p>
 * It provides two types of transformations:
 * </p>
 * <ul>
 *     <li>assembling data from one or multiple aggregates into a representation of the model or a part of the model.</li>
 *     <li>merging data from a DTO into one or multiple aggregates</li>
 * </ul>
 *
 * @param <A> the aggregate root
 * @param <D> the dto type
 * @see org.seedstack.business.assembler.FluentAssembler
 */
public interface Assembler<A, D> {

    /**
     * Creates a new DTO and assemble it from the aggregate.
     * <p>
     * Equivalent of {@link #assembleDtoFromAggregate(Object, Object) assembleDtoFromAggregate(new D(), sourceAggregate)}
     * </p>
     *
     * @param sourceAggregate The source aggregate
     * @return the resulting dto
     */
    D assembleDtoFromAggregate(A sourceAggregate);

    /**
     * Updates an existing DTO with a source aggregate root.
     *
     * @param targetDto       The target dto
     * @param sourceAggregate The source aggregate
     */
    void assembleDtoFromAggregate(D targetDto, A sourceAggregate);

    /**
     * Merges a source DTO into an existing aggregate root.
     *
     * @param targetAggregate The target aggregate
     * @param sourceDto       The source dto
     */
    void mergeAggregateWithDto(A targetAggregate, D sourceDto);

    /**
     * Returns the DTO type handled by the assembler.
     * <p>
     * This method is used by {@link #assembleDtoFromAggregate(Object)}
     * to determine the DTO type to instantiate.
     * </p>
     *
     * @return the dto class
     */
    Class<D> getDtoClass();
}
