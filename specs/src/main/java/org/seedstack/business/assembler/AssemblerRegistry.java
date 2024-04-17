/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import java.lang.annotation.Annotation;
import javax.annotation.Nullable;
import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;

/**
 * This registry allows to access assemblers programmatically.
 */
public interface AssemblerRegistry {

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class.
     *
     * @param <A>                the type of the aggregate root.
     * @param <D>                the type of the DTO.
     * @param aggregateRootClass the aggregate root class.
     * @param dtoClass           the dto class.
     * @return an assembler instance.
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass, Class<D> dtoClass);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the
     * specified qualifier.
     *
     * @param <A>                the type of the aggregate root.
     * @param <D>                the type of the DTO.
     * @param aggregateRootClass the aggregate root class.
     * @param dtoClass           the dto class.
     * @param qualifier          the assembler qualifier.
     * @return an assembler instance.
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass, Class<D> dtoClass,
            @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the
     * specified qualifier.
     *
     * @param <A>                the type of the aggregate root.
     * @param <D>                the type of the DTO.
     * @param aggregateRootClass the aggregate root class.
     * @param dtoClass           the dto class.
     * @param qualifier          the assembler qualifier.
     * @return an assembler instance.
     */
    <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass, Class<D> dtoClass,
            @Nullable Class<? extends Annotation> qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class.
     *
     * @param <T>                  the type of the tuple.
     * @param <D>                  the type of the DTO.
     * @param aggregateRootClasses an array of aggregate root classes.
     * @param dtoClass             the dto class.
     * @return an assembler instance.
     */
    <T extends Tuple, D> Assembler<T, D> getTupleAssembler(Class<? extends AggregateRoot<?>>[] aggregateRootClasses,
            Class<D> dtoClass);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for
     * the specified qualifier.
     *
     * @param <T>                  the type of the tuple.
     * @param <D>                  the type of the DTO.
     * @param aggregateRootClasses an array of aggregate root classes.
     * @param dtoClass             the dto class.
     * @param qualifier            the assembler qualifier.
     * @return an assembler instance.
     */
    <T extends Tuple, D> Assembler<T, D> getTupleAssembler(Class<? extends AggregateRoot<?>>[] aggregateRootClasses,
            Class<D> dtoClass, @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for
     * the specified qualifier.
     *
     * @param <T>                  the type of the tuple.
     * @param <D>                  the type of the DTO.
     * @param aggregateRootClasses an array of aggregate root classes.
     * @param dtoClass             the dto class.
     * @param qualifier            the assembler qualifier.
     * @return an assembler instance.
     */
    <T extends Tuple, D> Assembler<T, D> getTupleAssembler(Class<? extends AggregateRoot<?>>[] aggregateRootClasses,
            Class<D> dtoClass, @Nullable Class<? extends Annotation> qualifier);
}
