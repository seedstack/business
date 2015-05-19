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

import org.javatuples.*;
import org.seedstack.business.api.domain.AggregateRoot;

import java.util.List;

/**
 * Specifies the target aggregate to assemble.
 *
 * @param <D>
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface AggAssemblerProvider<D> {

    /**
     * Assembles the dto to an aggregate root.
     *
     * @param aggregateRootClass the aggregate root class
     * @param <A>                the type of aggregate root
     * @return the assembler DSL to assemble an aggregate root
     */
    <A extends AggregateRoot<?>> AggAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass);

    /**
     * Assembles the dto to an aggregate root.
     *
     * @param aggregateRoot the aggregate root instance
     * @param <A>           the aggregate root type
     * @return the assembled aggregate root
     */
    <A extends AggregateRoot<?>> A to(A aggregateRoot);

    // --- The above methods are for tuples of instances ---

    /**
     * Assembles the dto to a pair of aggregate roots.
     *
     * @param first  the first aggregate root of the pair
     * @param second the second aggregate root of the pair
     * @param <A1>   the first aggregate root type
     * @param <A2>   the second aggregate root type
     * @return the aggregate root pair
     */
    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    Pair<A1, A2> to(A1 first, A2 second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    Triplet<A1, A2, A3> to(A1 first, A2 second, A3 third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    Quartet<A1, A2, A3, A4> to(A1 first, A2 second, A3 third, A4 fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    Quintet<A1, A2, A3, A4, A5> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    Sextet<A1, A2, A3, A4, A5, A6> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    Septet<A1, A2, A3, A4, A5, A6, A7> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    Octet<A1, A2, A3, A4, A5, A6, A7, A8> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth);

    // --- The above method are tuples of classes

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Pair<A1, A2>> to(Class<A1> first, Class<A2> second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Triplet<A1, A2, A3>> to(Class<A1> first, Class<A2> second, Class<A3> third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Quartet<A1, A2, A3, A4>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Quintet<A1, A2, A3, A4, A5>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Sextet<A1, A2, A3, A4, A5, A6>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth);

    /**
     * Assemble the dto into a tuple of aggregate roots. This method is not recommended.
     * <p>
     * If you can, use the method matching the number of aggregate roots you have in the tuple. For instance use
     * {@link #to(Class, Class)} for a {@code Pair&lt;Customer, Order&gt;} it will return a more typed result.
     * </p>
     *
     * @param aggregateRootClasses a list of aggregate root classes
     * @return a tuple of aggregate roots
     */
    TupleAggAssemblerWithRepoProvider<Tuple> to(List<Class<? extends AggregateRoot<?>>> aggregateRootClasses);

    /**
     * Assemble the dto into a tuple of aggregate roots. This method is not recommended.
     * <p>
     * If you can, use the method matching the number of aggregate roots you have in the tuple. For instance use
     * {@link #to(org.seedstack.business.api.domain.AggregateRoot, org.seedstack.business.api.domain.AggregateRoot)}
     * for a {@code Pair&lt;Customer, Order&gt;} it will return a more typed result.
     * </p>
     *
     * @param aggregateRoots a list of aggregate root classes
     * @return a tuple of aggregate roots
     */
    Tuple to(Tuple aggregateRoots);

}
