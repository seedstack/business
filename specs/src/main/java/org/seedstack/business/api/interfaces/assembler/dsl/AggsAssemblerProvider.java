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
* @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
*/
public interface AggsAssemblerProvider<D> {

    /**
     * Assembles the list of dtos to a list of aggregate roots.
     *
     * @param aggregateRootClass the aggregate root class
     * @param <A>                the type of aggregate root
     * @return the assembler DSL to a list of aggregate roots
     */
    <A extends AggregateRoot<?>> AggsAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass);

    <A extends AggregateRoot<?>> List<A> to(List<A> aggregateRoots);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Pair<A1, A2>> to(Class<A1> first, Class<A2> second);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Triplet<A1, A2, A3>> to(Class<A1> first, Class<A2> second, Class<A3> third);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Quartet<A1, A2, A3, A4>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Quintet<A1, A2, A3, A4, A5>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Sextet<A1, A2, A3, A4, A5, A6>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth);

    <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth);

}
