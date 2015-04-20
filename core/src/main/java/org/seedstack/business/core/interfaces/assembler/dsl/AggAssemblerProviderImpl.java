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
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerWithRepoProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.TupleAggAssemblerWithRepoProvider;

import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggAssemblerProviderImpl<D> implements AggAssemblerProvider<D> {

    private InternalRegistry registry;
    private final D dto;

    public AggAssemblerProviderImpl(InternalRegistry registry, D dto) {
        this.registry = registry;
        this.dto = dto;
    }

    @Override
    public <A extends AggregateRoot<?>> AggAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass) {
        return new AggAssemblerWithRepoProviderImpl<A>(registry, aggregateRootClass, dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Pair<A1, A2>> to(Class<A1> first, Class<A2> second) {
        return new TupleAggAssemblerWithRepoProviderImpl<Pair<A1, A2>>(registry, Lists.newArrayList(first, second), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    TupleAggAssemblerWithRepoProvider<Triplet<A1, A2, A3>> to(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new TupleAggAssemblerWithRepoProviderImpl<Triplet<A1, A2, A3>>(registry, Lists.newArrayList(first, second, third), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Quartet<A1, A2, A3, A4>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Quartet<A1, A2, A3, A4>>(registry, Lists.newArrayList(first, second, third, fourth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Quintet<A1, A2, A3, A4, A5>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Quintet<A1, A2, A3, A4, A5>>(registry, Lists.newArrayList(first, second, third, fourth, fifth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Sextet<A1, A2, A3, A4, A5, A6>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Sextet<A1, A2, A3, A4, A5, A6>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new TupleAggAssemblerWithRepoProviderImpl<Septet<A1, A2, A3, A4, A5, A6, A7>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Octet<A1, A2, A3, A4, A5, A6, A7, A8>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dto);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>> 
    TupleAggAssemblerWithRepoProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> to(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new TupleAggAssemblerWithRepoProviderImpl<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>(registry, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dto);
    }

    @Override
    public TupleAggAssemblerWithRepoProvider<Tuple> to(List<Class<? extends AggregateRoot<?>>> aggregateRootClasses) {
        return new TupleAggAssemblerWithRepoProviderImpl<Tuple>(registry, aggregateRootClasses, dto);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    Pair<A1, A2> to(A1 first, A2 second) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass());
        Pair<A1, A2> aggregateRootTuple = Tuples.create(first, second);
        Assembler<Pair<A1, A2>, D> assembler = (Assembler<Pair<A1, A2>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    Triplet<A1, A2, A3> to(A1 first, A2 second, A3 third) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass());
        Triplet<A1, A2, A3> aggregateRootTuple = Tuples.create(first, second, third);
        Assembler<Triplet<A1, A2, A3>, D> assembler = (Assembler<Triplet<A1, A2, A3>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    Quartet<A1, A2, A3, A4> to(A1 first, A2 second, A3 third, A4 fourth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass());
        Quartet<A1, A2, A3, A4> aggregateRootTuple = Tuples.create(first, second, third, fourth);
        Assembler<Quartet<A1, A2, A3, A4>, D> assembler = (Assembler<Quartet<A1, A2, A3, A4>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    Quintet<A1, A2, A3, A4, A5> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass());
        Quintet<A1, A2, A3, A4, A5> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth);
        Assembler<Quintet<A1, A2, A3, A4, A5>, D> assembler = (Assembler<Quintet<A1, A2, A3, A4, A5>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    Sextet<A1, A2, A3, A4, A5, A6> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass());
        Sextet<A1, A2, A3, A4, A5, A6> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth);
        Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D> assembler = (Assembler<Sextet<A1, A2, A3, A4, A5, A6>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    Septet<A1, A2, A3, A4, A5, A6, A7> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass());
        Septet<A1, A2, A3, A4, A5, A6, A7> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh);
        Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D> assembler = (Assembler<Septet<A1, A2, A3, A4, A5, A6, A7>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    Octet<A1, A2, A3, A4, A5, A6, A7, A8> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass());
        Octet<A1, A2, A3, A4, A5, A6, A7, A8> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth);
        Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D> assembler = (Assembler<Octet<A1, A2, A3, A4, A5, A6, A7, A8>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass());
        Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
        Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D> assembler = (Assembler<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> to(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth) {
        List<?> aggregateRootClasses = Lists.newArrayList(first.getClass(), second.getClass(), third.getClass(), fourth.getClass(), fifth.getClass(), sixth.getClass(), seventh.getClass(), eighth.getClass(), ninth.getClass(), tenth.getClass());
        Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> aggregateRootTuple = Tuples.create(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth);
        Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D> assembler = (Assembler<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>, D>) registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public Tuple to(Tuple aggregateRootTuple) {
        Assembler assembler = registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) Tuples.toListOfClasses(aggregateRootTuple), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRootTuple, dto);
        return aggregateRootTuple;
    }

    @Override
    public <A extends AggregateRoot<?>> A to(A aggregateRoot) {
        Assembler<A, D> assembler = (Assembler<A, D>) registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoot.getClass(), dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoot, dto);
        return aggregateRoot;
    }

}
