/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.seedstack.business.pagination.Slice;

/**
 * An element of the {@link FluentAssembler} DSL allowing to assemble to multiple DTO in various
 * forms (stream, list, set, array, ...).
 */
public interface AssembleMultiple {

    /**
     * Assembles to a {@link Stream} of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the list of DTO.
     */
    <D> Stream<D> toStreamOf(Class<D> dtoClass);

    /**
     * Assembles to the supplied {@link Collection}.
     *
     * @param <D>                the type of the DTO.
     * @param <C>                the type of the collection.
     * @param dtoClass           the DTO class to assemble.
     * @param collectionSupplier the provider of a (preferably empty) collection.
     * @return the collection of DTO.
     */
    <D, C extends Collection<D>> C toCollectionOf(Class<D> dtoClass, Supplier<C> collectionSupplier);

    /**
     * Assembles to a {@link List} of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the list of DTO.
     */
    <D> List<D> toListOf(Class<D> dtoClass);

    /**
     * Assembles to a {@link Set} of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the set of DTO.
     */
    <D> Set<D> toSetOf(Class<D> dtoClass);

    /**
     * Assembles to a {@link Slice} of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble
     * @return the list of DTO.
     */
    <D> Slice<D> toSliceOf(Class<D> dtoClass);

    /**
     * Assembles to an array of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the array of DTO.
     */
    <D> D[] toArrayOf(Class<D> dtoClass);
}
