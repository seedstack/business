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
import org.seedstack.business.assembler.dsl.MergeSingle;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleAggregateFromRepository;
import org.seedstack.business.assembler.dsl.MergeSingleTupleWithRepository;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.Tuples;

import java.lang.annotation.Annotation;
import java.util.List;


public class MergeSingleImpl<D> implements MergeSingleWithQualifier<D> {
    private final AssemblerDslContext context;
    private final D dto;

    public MergeSingleImpl(AssemblerDslContext context, D dto) {
        this.context = context;
        this.dto = dto;
    }

    @Override
    public <A extends AggregateRoot<?>> MergeSingleAggregateFromRepository<A> into(Class<A> aggregateRootClass) {
        return new MergeSingleSingleAggregateFromRepositoryImpl<>(context, aggregateRootClass, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Pair<A1, A2>> into(Class<A1> first, Class<A2> second) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeSingleTupleWithRepository<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dto);
    }

    @Override
    public MergeSingleTupleWithRepository<Tuple> into(List<Class<? extends AggregateRoot<?>>> aggregateRootClasses) {
        return new MergeSingleSingleTupleFromRepositoryImpl<>(context, aggregateRootClasses, dto);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    void into(A1 first, A2 second) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass());
        Pair<A1, A2> aggregateRootTuple = Tuples.create(first, second);
        @SuppressWarnings("unchecked")
        Assembler<Pair<A1, A2>, D> assembler = (Assembler<Pair<A1, A2>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass());
        Triplet<A1, A2, A3> aggregateRootTuple = Tuples.create(first, second, third);
        @SuppressWarnings("unchecked")
        Assembler<Triplet<A1, A2, A3>, D> assembler = (Assembler<Triplet<A1, A2, A3>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass());
        Quartet<A1, A2, A3, A4> aggregateRootTuple = Tuples.create(first, second, third, fourth);
        @SuppressWarnings("unchecked")
        Assembler<Quartet<A1, A2, A3, A4>, D> assembler = (Assembler<Quartet<A1, A2, A3, A4>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass());
        Quintet<A1, A2, A3, A4, A5> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth);
        @SuppressWarnings("unchecked")
        Assembler<Quintet<A1, A2, A3, A4, A5>, D> assembler = (Assembler<Quintet<A1, A2, A3, A4, A5>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass());
        Sextet<A1, A2, A3, A4, A5, A6> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth);
        @SuppressWarnings("unchecked")
        Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D> assembler = (Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass());
        Septet<A1, A2, A3, A4, A5, A6, A7> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh);
        @SuppressWarnings("unchecked")
        Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D> assembler = (Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass());
        Octet<A1, A2, A3, A4, A5, A6, A7, A8> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth);
        @SuppressWarnings("unchecked")
        Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D> assembler = (Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass());
        Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
        @SuppressWarnings("unchecked")
        Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D> assembler = (Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass(), tenth.getClass());
        Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth);
        @SuppressWarnings("unchecked")
        Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D> assembler = (Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A extends AggregateRoot<?>> void into(A aggregateRoot) {
        @SuppressWarnings("unchecked")
        Assembler<A, D> assembler = (Assembler<A, D>) context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoot.getClass(), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoot, dto);
    }

    @Override
    public MergeSingle<D> with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public MergeSingle<D> with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }
}
