/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler;

@Deprecated
public interface LegacyAssembler<A, D> extends Assembler<A, D> {
    /**
     * Creates a new DTO and assemble it from the aggregate.
     * <p>
     * Equivalent of
     * {@link #assembleDtoFromAggregate(Object, Object) assembleDtoFromAggregate(new D(), sourceAggregate)}
     * </p>
     *
     * @param sourceAggregate The source aggregate
     * @return the resulting dto
     */
    default D assembleDtoFromAggregate(A sourceAggregate) {
        return createDtoFromAggregate(sourceAggregate);
    }

    /**
     * Updates an existing DTO with a source aggregate root.
     *
     * @param targetDto       The target dto
     * @param sourceAggregate The source aggregate
     */
    default void assembleDtoFromAggregate(D targetDto, A sourceAggregate) {
        mergeAggregateIntoDto(sourceAggregate, targetDto);
    }

    /**
     * Merges a source DTO into an existing aggregate root.
     *
     * @param targetAggregate The target aggregate
     * @param sourceDto       The source dto
     */
    default void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        mergeDtoIntoAggregate(sourceDto, targetAggregate);
    }
}