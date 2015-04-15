/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;


import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;

/**
 * Provides methods to specify the aggregate class to which aggregate root (or tuple of aggregate root) assemble.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public interface BaseAggAssemblerProvider {

    /**
     * Assembles to an aggregate root.
     *
     * @param aggregateRootClass the aggregate root class
     * @param <A>                the type of aggregate root
     * @return the assembler DSL to assemble an aggregate root
     */
    <A extends AggregateRoot<?>> AggAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass);

    /**
     * Assembles to a tuple of aggregate roots.
     * <p>
     * The parameters are cut in three in order to not conflict with the non tuple method.
     * </p>
     *
     * @param firstAggregateClass   the first aggregate root class of the tuple
     * @param secondAggregateClass  the second class
     * @param otherAggregateClasses and the rest
     * @param <T>                   The tuple type
     * @return the assembler DSL to assemble a tuple of aggregate roots
     */
    <T extends Tuple> TupleAggAssemblerWithRepoProvider<T> to(Class<? extends AggregateRoot<?>> firstAggregateClass, Class<? extends AggregateRoot<?>> secondAggregateClass, Class<? extends AggregateRoot<?>>... otherAggregateClasses);
}
