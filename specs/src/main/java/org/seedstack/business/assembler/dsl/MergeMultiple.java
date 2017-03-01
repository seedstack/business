/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.seedstack.business.domain.AggregateRoot;

import java.util.List;


public interface MergeMultiple<D> {

    <A extends AggregateRoot<?>> void into(List<A> aggregateRoots);

    /**
     * Merges the list of dtos to a list of aggregate roots.
     *
     * @param aggregateRootClass the aggregate root class
     * @param <A>                the type of aggregate root
     * @return the assembler DSL to a list of aggregate roots
     */
    <A extends AggregateRoot<?>> MergeMultipleAggregatesFromRepository<A> into(Class<A> aggregateRootClass);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Pair<A1, A2>> into(Class<A1> first, Class<A2> second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth);

}
