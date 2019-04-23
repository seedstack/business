/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
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

/**
 * An element of the {@link FluentAssembler DSL} allowing to merge multiple DTO into aggregates or
 * tuples of aggregates.
 **/
public interface MergeMultiple {

    <A extends AggregateRoot<I>, I> MergeFromRepository<MergeAs<A>> into(Class<A> aggregateRootClass);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Pair<A0, A1>>> into(Class<A0> first,
            Class<A1> second);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Triplet<A0, A1, A2>>> into(Class<A0> first,
            Class<A1> second, Class<A2> third);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Quartet<A0, A1, A2, A3>>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Quintet<A0, A1, A2, A3, A4>>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Sextet<A0, A1, A2, A3, A4, A5>>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Septet<A0, A1, A2, A3, A4, A5, A6>>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
            Class<A6> seventh);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Octet<A0, A1, A2, A3, A4, A5, A6, A7>>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
            Class<A6> seventh, Class<A7> eighth);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Ennead<A0, A1, A2, A3, A4, A5, A6, A7, A8>>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
            Class<A6> seventh, Class<A7> eighth, Class<A8> ninth);

    <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>,
            A9 extends AggregateRoot<?>> MergeFromRepository<MergeAs<Decade<A0, A1, A2, A3, A4, A5, A6, A7, A8,
            A9>>> into(Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth,
            Class<A5> sixth, Class<A6> seventh, Class<A7> eighth, Class<A8> ninth, Class<A9> tenth);
}
