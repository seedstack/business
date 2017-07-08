/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

/**
 * This registry allows to access assemblers programmatically.
 */
public interface AssemblerRegistry {

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class.
     *
     * @param aggregateRoot the aggregate root class.
     * @param dto           the dto class
     * @return the assembler
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> assemblerOf(Class<A> aggregateRoot, Class<D> dto);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the specified qualifier.
     *
     * @param aggregateRoot the aggregate root class.
     * @param dto           the dto class
     * @param qualifier     the assembler qualifier
     * @return the assembler
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> assemblerOf(Class<A> aggregateRoot, Class<D> dto, @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the specified qualifier.
     *
     * @param aggregateRoot the aggregate root class
     * @param dto           the dto class
     * @param qualifier     the assembler qualifier
     * @return the assembler
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> assemblerOf(Class<A> aggregateRoot, Class<D> dto, @Nullable Class<? extends Annotation> qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @return the assembler
     */
    <T extends Tuple, D> Assembler<T, D> tupleAssemblerOf(Class<? extends AggregateRoot<?>>[] aggregateRootTuple, Class<D> dto);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for the specified qualifier.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @param qualifier          the assembler qualifier
     * @return the assembler
     */
    <T extends Tuple, D> Assembler<T, D> tupleAssemblerOf(Class<? extends AggregateRoot<?>>[] aggregateRootTuple, Class<D> dto, @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for the specified qualifier.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @param qualifier          the assembler qualifier
     * @return the assembler
     */
    <T extends Tuple, D> Assembler<T, D> tupleAssemblerOf(Class<? extends AggregateRoot<?>>[] aggregateRootTuple, Class<D> dto, @Nullable Class<? extends Annotation> qualifier);
}
