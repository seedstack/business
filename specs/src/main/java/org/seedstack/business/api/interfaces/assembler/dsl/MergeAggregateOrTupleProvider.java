/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
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
 * Specifies the target aggregate to merge.
 *
 * @param <D> the DTO type
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface MergeAggregateOrTupleProvider<D> {

    /**
     * Merges the dto to an aggregate root.
     *
     * @param aggregateRoot the aggregate root instance
     * @param <A>           the aggregate root type
     */
    <A extends AggregateRoot<?>> void into(A aggregateRoot);

    /**
     * Merges the dto to an aggregate root.
     *
     * @param aggregateRootClass the aggregate root class
     * @param <A>                the type of aggregate root
     * @return the assembler DSL to assemble an aggregate root
     */
    <A extends AggregateRoot<?>> MergeAggregateWithRepositoryProvider<A> into(Class<A> aggregateRootClass);

    // --- The above methods are for tuples of instances ---

    /**
     * Merges the dto to a pair of aggregate roots.
     *
     * @param first  the first aggregate root of the pair
     * @param second the second aggregate root of the pair
     * @param <A1>   the first aggregate root type
     * @param <A2>   the second aggregate root type
     */
    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    void into(A1 first, A2 second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth);

    // --- The above method are tuples of classes

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Pair<A1, A2>> into(Class<A1> first, Class<A2> second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth);

    /**
     * Merge the dto into a tuple of aggregate roots. This method is not recommended.
     * <p>
     * If you can, use the method matching the number of aggregate roots you have in the tuple. For instance use
     * {@link #into(Class, Class)} for a {@code Pair&lt;Customer, Order&gt;}. The result will be a more typed.
     * </p>
     *
     * @param aggregateRootClasses a list of aggregate root classes
     * @return a tuple of aggregate roots
     */
    MergeTupleWithRepositoryProvider<Tuple> into(List<Class<? extends AggregateRoot<?>>> aggregateRootClasses);

    /**
     * Merge the dto into a tuple of aggregate roots. This method is not recommended.
     * <p>
     * If you can, use the method matching the number of aggregate roots you have in the tuple. For instance use
     * {@link #into(org.seedstack.business.api.domain.AggregateRoot, org.seedstack.business.api.domain.AggregateRoot)}
     * for a {@code Pair&lt;Customer, Order&gt;} it will return a more typed result.
     * </p>
     *
     * @param aggregateRoots a list of aggregate root classes
     */
    @Deprecated
    void into(Tuple aggregateRoots);

}
