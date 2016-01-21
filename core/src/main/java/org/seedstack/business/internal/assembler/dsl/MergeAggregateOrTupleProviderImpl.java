/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Lists;
import org.javatuples.*;
import org.seedstack.business.Tuples;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.dsl.MergeAggregateWithRepositoryProvider;
import org.seedstack.business.assembler.dsl.MergeAggregateOrTupleProvider;
import org.seedstack.business.assembler.dsl.MergeAggregateOrTupleWithQualifierProvider;
import org.seedstack.business.assembler.dsl.MergeTupleWithRepositoryProvider;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class MergeAggregateOrTupleProviderImpl<D> implements MergeAggregateOrTupleWithQualifierProvider<D> {

    private final AssemblerDslContext context;
    private final D dto;

    public MergeAggregateOrTupleProviderImpl(AssemblerDslContext context, D dto) {
        this.context = context;
        this.dto = dto;
    }

    @Override
    public <A extends AggregateRoot<?>> MergeAggregateWithRepositoryProvider<A> into(Class<A> aggregateRootClass) {
        return new MergeMergeAggregateWithRepositoryProviderImpl<A>(context, aggregateRootClass, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Pair<A1, A2>> into(Class<A1> first, Class<A2> second) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Pair<A1, A2>>(context, Lists.newArrayList(first, second), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Triplet<A1, A2, A3>>(context, Lists.newArrayList(first, second, third), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Quartet<A1, A2, A3, A4>>(context, Lists.newArrayList(first, second, third, fourth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Quintet<A1, A2, A3, A4, A5>>(context, Lists.newArrayList(first, second, third, fourth, fifth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Sextet<A1, A2, A3, A4, A5, A6>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Septet<A1, A2, A3, A4, A5, A6, A7>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Octet<A1, A2, A3, A4, A5, A6, A7, A8>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeTupleWithRepositoryProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dto);
    }

    @Override
    public MergeTupleWithRepositoryProvider<Tuple> into(List<Class<? extends AggregateRoot<?>>> aggregateRootClasses) {
        return new MergeMergeTupleWithRepositoryProviderImpl<Tuple>(context, aggregateRootClasses, dto);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    void into(A1 first, A2 second) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass());
        Pair<A1, A2> aggregateRootTuple = Tuples.create(first, second);
        Assembler<Pair<A1, A2>, D> assembler = (Assembler<Pair<A1, A2>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass());
        Triplet<A1, A2, A3> aggregateRootTuple = Tuples.create(first, second, third);
        Assembler<Triplet<A1, A2, A3>, D> assembler = (Assembler<Triplet<A1, A2, A3>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass());
        Quartet<A1, A2, A3, A4> aggregateRootTuple = Tuples.create(first, second, third, fourth);
        Assembler<Quartet<A1, A2, A3, A4>, D> assembler = (Assembler<Quartet<A1, A2, A3, A4>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass());
        Quintet<A1, A2, A3, A4, A5> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth);
        Assembler<Quintet<A1, A2, A3, A4, A5>, D> assembler = (Assembler<Quintet<A1, A2, A3, A4, A5>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass());
        Sextet<A1, A2, A3, A4, A5, A6> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth);
        Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D> assembler = (Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass());
        Septet<A1, A2, A3, A4, A5, A6, A7> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh);
        Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D> assembler = (Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass());
        Octet<A1, A2, A3, A4, A5, A6, A7, A8> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth);
        Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D> assembler = (Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass());
        Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
        Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D> assembler = (Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass(), tenth.getClass());
        Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth);
        Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D> assembler = (Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D>) context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public void into(Tuple aggregateRootTuple) {
        Assembler assembler = context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) Tuples.toListOfClasses(aggregateRootTuple), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
    }

    @Override
    public <A extends AggregateRoot<?>> void into(A aggregateRoot) {
        Assembler<A, D> assembler = (Assembler<A, D>) context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoot.getClass(), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoot, dto);
    }

    @Override
    public MergeAggregateOrTupleProvider<D> with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public MergeAggregateOrTupleProvider<D> with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public MergeAggregateOrTupleProvider<D> with(AssemblerTypes qualifier) {
        context.setAssemblerQualifierClass(qualifier.get());
        return this;
    }
}
