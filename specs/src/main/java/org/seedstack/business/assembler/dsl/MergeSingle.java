/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
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
import org.javatuples.Unit;
import org.seedstack.business.domain.AggregateRoot;

/**
 * An element of the {@link FluentAssembler DSL} allowing to merge one DTO into an aggregate or a
 * tuple of aggregates.
 */
public interface MergeSingle {

  <AggregateRootT extends AggregateRoot<IdT>, IdT> MergeFromRepository<AggregateRootT> into(
    Class<AggregateRootT> aggregateRootClass);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>> MergeFromRepository<Pair<A0, A1>> into(Class<A0> first, Class<A1> second);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>> MergeFromRepository<Triplet<A0, A1, A2>> into(Class<A0> first, Class<A1> second,
    Class<A2> third);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>> MergeFromRepository<Quartet<A0, A1, A2, A3>> into(Class<A0> first, Class<A1> second,
    Class<A2> third, Class<A3> fourth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>> MergeFromRepository<Quintet<A0, A1, A2, A3, A4>> into(Class<A0> first,
    Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>> MergeFromRepository<Sextet<A0, A1, A2, A3, A4, A5>> into(Class<A0> first,
    Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>> MergeFromRepository<Septet<A0, A1, A2, A3, A4, A5, A6>> into(Class<A0> first,
    Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth, Class<A6> seventh);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>> MergeFromRepository<Octet<A0, A1, A2, A3, A4, A5, A6, A7>> into(Class<A0> first,
    Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth, Class<A6> seventh,
    Class<A7> eighth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>> MergeFromRepository<Ennead<A0, A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A0> first,
    Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth, Class<A6> seventh,
    Class<A7> eighth, Class<A8> ninth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>,
    A9 extends AggregateRoot<?>> MergeFromRepository<Decade<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(
    Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
    Class<A6> seventh, Class<A7> eighth, Class<A8> ninth, Class<A9> tenth);

  <AggregateRootT extends AggregateRoot<IdT>, IdT> void into(AggregateRootT aggregateRoot);

  <A0 extends AggregateRoot<?>, A1 extends AggregateRoot<?>> void into(A0 first, A1 second);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth);

  <A0 extends AggregateRoot<?>, A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth, A6 seventh);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth, A6 seventh,
    A7 eighth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth, A6 seventh,
    A7 eighth, A8 ninth);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>,
    A9 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth, A6 seventh,
    A7 eighth, A8 ninth, A9 tenth);

  <AggregateRootT extends AggregateRoot<IdT>, IdT> void into(Unit<AggregateRootT> unit);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>> void into(Pair<A0, A1> pair);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>> void into(Triplet<A0, A1, A2> triplet);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>> void into(Quartet<A0, A1, A2, A3> quartet);

  <A0 extends AggregateRoot<?>, A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>> void into(Quintet<A0, A1, A2, A3, A4> quintet);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>> void into(Sextet<A0, A1, A2, A3, A4, A5> sextet);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>> void into(Septet<A0, A1, A2, A3, A4, A5, A6> septet);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>> void into(Octet<A0, A1, A2, A3, A4, A5, A6, A7> octet);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>> void into(Ennead<A0, A1, A2, A3, A4, A5, A6, A7, A8> ennead);

  <A0 extends AggregateRoot<?>,
    A1 extends AggregateRoot<?>,
    A2 extends AggregateRoot<?>,
    A3 extends AggregateRoot<?>,
    A4 extends AggregateRoot<?>,
    A5 extends AggregateRoot<?>,
    A6 extends AggregateRoot<?>,
    A7 extends AggregateRoot<?>,
    A8 extends AggregateRoot<?>,
    A9 extends AggregateRoot<?>> void into(Decade<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9> decade);
}
