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

import com.google.common.collect.Lists;
import org.javatuples.*;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggsAssemblerProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggsAssemblerWithRepoProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.TupleAggsAssemblerWithRepoProvider;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggsAssemblerProviderImpl<D> implements AggsAssemblerProvider<D> {

    private InternalRegistry registry;
    private final List<D> dtos;

    public AggsAssemblerProviderImpl(InternalRegistry registry, List<D> dtos) {
        this.registry = registry;
        this.dtos = dtos;
    }

    @Override
    public <A extends AggregateRoot<?>> AggsAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass) {
        return new AggsAssemblerWithRepoProviderImpl<A>(registry, aggregateRootClass, dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Pair<A1, A2>> to(Class<A1> first, Class<A2> second) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Pair<A1, A2>>(registry, Lists.newArrayList(first, second), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    TupleAggsAssemblerWithRepoProvider<Triplet<A1, A2, A3>> to(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Triplet<A1, A2, A3>>(registry, Lists.newArrayList(first, second, third), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Quartet<A1, A2, A3, A4>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Quartet<A1, A2, A3, A4>>(registry, Lists.newArrayList(first, second, third, fourth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Quintet<A1, A2, A3, A4, A5>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Quintet<A1, A2, A3, A4, A5>>(registry, Lists.newArrayList(first, second, third, fourth, fifth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Sextet<A1, A2, A3, A4, A5, A6>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Sextet<A1, A2, A3, A4, A5, A6>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Septet<A1, A2, A3, A4, A5, A6, A7>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Octet<A1, A2, A3, A4, A5, A6, A7, A8>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>> 
    TupleAggsAssemblerWithRepoProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new TupleAggsAssemblerWithRepoProviderImpl<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dtos);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public TupleAggsAssemblerWithRepoProvider<Tuple> to(Class<? extends AggregateRoot<?>>[] aggregateRootClasses) {
        // Still used by our legacy Assemblers but not part of the API
        return new TupleAggsAssemblerWithRepoProviderImpl<Tuple>(registry, Arrays.asList(aggregateRootClasses), dtos);
    }

    @Override
    public <A extends AggregateRoot<?>> List<A> to(List<A> aggregateRoots) {
        if (aggregateRoots != null && dtos != null) {
            if (aggregateRoots.size() != dtos.size()) {
                throw new IllegalArgumentException("The list of dto should have the same size as the list of aggregate");
            }
            Assembler assembler = registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.get(0).getClass(), dtos.get(0).getClass());
            for (int i = 0; i < aggregateRoots.size(); i++) {
                assembler.mergeAggregateWithDto(aggregateRoots.get(i), dtos.get(i));
            }
        }
        return aggregateRoots;
    }

}
