/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Lists;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.MergeMultiple;
import org.seedstack.business.assembler.dsl.MergeMultipleAggregatesFromRepository;
import org.seedstack.business.assembler.dsl.MergeMultipleTuplesWithRepository;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;


public class MergeMultipleImpl<D> implements MergeMultipleWithQualifier<D> {

    private final AssemblerDslContext context;
    private final List<D> dtos;

    public MergeMultipleImpl(AssemblerDslContext context, List<D> dtos) {
        this.context = context;
        this.dtos = dtos;
    }

    @Override
    public <A extends AggregateRoot<?>> MergeMultipleAggregatesFromRepository<A> into(Class<A> aggregateRootClass) {
        return new MergeMultipleAggregatesFromRepositoryImpl<>(context, aggregateRootClass, dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Pair<A1, A2>> into(Class<A1> first, Class<A2> second) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeMultipleTuplesWithRepository<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dtos);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public MergeMultipleTuplesWithRepository<Tuple> into(Class<? extends AggregateRoot<?>>[] aggregateRootClasses) {
        // Still used by our legacy Assemblers but not part of the API
        return new MergeMultipleTuplesFromRepositoryImpl<>(context, Arrays.asList(aggregateRootClasses), dtos);
    }

    @Override
    public <A extends AggregateRoot<?>> void into(List<A> aggregateRoots) {
        if (aggregateRoots != null && dtos != null) {
            if (aggregateRoots.size() != dtos.size()) {
                throw new IllegalArgumentException("The list of dto should have the same size as the list of aggregate");
            }
            Assembler assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.get(0).getClass(), dtos.get(0).getClass());
            for (int i = 0; i < aggregateRoots.size(); i++) {
                assembler.mergeAggregateWithDto(aggregateRoots.get(i), dtos.get(i));
            }
        }
    }

    @Override
    public MergeMultiple<D> with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public MergeMultiple<D> with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }
}
