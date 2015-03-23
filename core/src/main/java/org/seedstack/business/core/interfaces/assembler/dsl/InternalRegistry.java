/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;

import java.util.List;

/**
 * This registry is used internally to dynamically provide DDD objects according to the aggregate root
 * they are associated to. It also avoid the proliferation of the injector in other classes of the DSL.
 * <p>
 * This class is required by the genericity of the DSL, but may not be useful for client users.
 * </p>
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public interface InternalRegistry {

    /**
     * Returns an BaseAssembler matching the given aggregate root class and the dto class.
     * <p>
     * If no assembler were created by the client developer, the method returns an
     * {@link org.seedstack.business.core.interfaces.AutomaticAssembler}.
     * </p>
     *
     * @param aggregateRoot the aggregate root class.
     * @param dto           the dto class
     * @return the assembler
     */
    Assembler<?, ?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto);

    /**
     * Returns an BaseTupleAssembler matching the given tuple of aggregate roots and the dto class.
     * <p>
     * Be careful, contrarily to the other {@code tupleAssemblerOf()} method, it contains the
     * aggregate roots and not their classes.
     * </p>
     * If no assembler were created by the client developer, the method returns an
     * {@link org.seedstack.business.core.interfaces.AutomaticTupleAssembler}.
     *
     * @param aggregateRootTuple a tuple of aggregate root.
     * @param dto                the dto class
     * @return the assembler
     */
    Assembler<?, ?, ?> tupleAssemblerOf(Tuple aggregateRootTuple, Class<?> dto);

    /**
     * Returns an BaseTupleAssembler matching the given list of aggregate root classes and the dto class.
     * <p>
     * If no assembler were created by the client developer, the method returns an
     * {@link org.seedstack.business.core.interfaces.AutomaticTupleAssembler}.
     * </p>
     *
     * @param aggregateRootTuple a list of aggregate root classes.
     * @param dto                the dto class
     * @return the assembler
     */
    Assembler<?, ?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto);

    /**
     * Returns a generic factory for the given aggregate root.
     * <p>
     * If no factory were created by the client developer, the method return a
     * default factory, i.e. {@link org.seedstack.business.api.domain.Factory}.
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
     * {@link org.seedstack.business.api.domain.DomainObject}
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
