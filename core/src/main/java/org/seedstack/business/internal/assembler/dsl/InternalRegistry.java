/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.domain.*;
import org.seedstack.business.assembler.Assembler;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This registry is used internally to dynamically provide DDD objects according to the aggregate root
 * they are associated to. It also avoid the proliferation of the injector in other classes of the DSL.
 * <p>
 * This class is required because of the DSL genericity, but it may not be useful for client users.
 * </p>
 */
public interface InternalRegistry {

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class.
     *
     * @param aggregateRoot the aggregate root class.
     * @param dto           the dto class
     * @return the assembler
     */
    Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the specified qualifier.
     *
     * @param aggregateRoot the aggregate root class.
     * @param dto           the dto class
     * @param qualifier     the assembler qualifier
     * @return the assembler
     */
    Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto, @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given aggregate root class and the dto class for the specified qualifier.
     *
     * @param aggregateRoot the aggregate root class
     * @param dto           the dto class
     * @param qualifier     the assembler qualifier
     * @return the assembler
     */
    Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto, @Nullable Class<? extends Annotation> qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @return the assembler
     */
    Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for the specified qualifier.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @param qualifier          the assembler qualifier
     * @return the assembler
     */
    Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto, @Nullable Annotation qualifier);

    /**
     * Returns the Assembler matching the given list of aggregate root classes and the dto class for the specified qualifier.
     *
     * @param aggregateRootTuple a list of aggregate root classes
     * @param dto                the dto class
     * @param qualifier          the assembler qualifier
     * @return  the assembler
     */
    Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto, @Nullable Class<? extends Annotation> qualifier);

    /**
     * Returns a generic factory for the given aggregate root.
     * <p>
     * If no factory were created by the client developer, the method return a
     * default factory, i.e. {@link Factory}.
     * </p>
     *
     * @param aggregateRoot the aggregate root
     * @return the factory
     */
    GenericFactory<?> genericFactoryOf(Class<? extends AggregateRoot<?>> aggregateRoot);

    /**
     * Returns a default factory for the given domain object. It can be used for instance
     * to create value object or aggregate roots.
     *
     * @param domainObject the domain object produced by the factory
     * @return the factory
     * @throws java.lang.IllegalArgumentException if the passed domainObject does not implement
     *                                            {@link org.seedstack.business.domain.DomainObject}
     */
    Factory<?> defaultFactoryOf(Class<? extends DomainObject> domainObject);

    /**
     * Returns a repository for the given aggregate root.
     * <p>
     * If no repository were created by the client developer,
     * the method return a default repository.
     * </p>
     *
     * @param aggregateRoot the aggregate root
     * @return the repository
     */
    Repository<?, ?> repositoryOf(Class<? extends AggregateRoot<?>> aggregateRoot);
}
