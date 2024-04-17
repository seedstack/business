/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
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
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeSingle;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.util.Tuples;

class MergeSingleImpl<D> implements MergeSingleWithQualifier {

    private final Context context;
    private final D dto;
    private final Class<D> dtoClass;

    @SuppressWarnings("unchecked")
    MergeSingleImpl(Context context, D dto) {
        this.context = context;
        this.dto = dto;
        this.dtoClass = (Class<D>) dto.getClass();
    }

    @Override
    public <A extends AggregateRoot<I>, I> MergeFromRepository<A> into(Class<A> aggregateRootClass) {
        return new MergeSingleAggregateFromRepositoryImpl<>(context, dto, aggregateRootClass);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>> MergeFromRepository<Pair<A0, A1>> into(Class<A0> first, Class<A1> second) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>> MergeFromRepository<Triplet<A0, A1, A2>> into(Class<A0> first,
            Class<A1> second, Class<A2> third) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>> MergeFromRepository<Quartet<A0, A1, A2, A3>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>> MergeFromRepository<Quintet<A0, A1, A2, A3, A4>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>> MergeFromRepository<Sextet<A0, A1, A2, A3, A4, A5>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth, sixth);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>> MergeFromRepository<Septet<A0, A1, A2, A3, A4, A5, A6>> into(Class<A0> first,
            Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth, Class<A6> seventh) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth, sixth, seventh);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>> MergeFromRepository<Octet<A0, A1, A2, A3, A4, A5, A6, A7>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
            Class<A6> seventh, Class<A7> eighth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth, sixth, seventh,
                eighth);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>> MergeFromRepository<Ennead<A0, A1, A2, A3, A4, A5, A6, A7, A8>> into(
            Class<A0> first, Class<A1> second, Class<A2> third, Class<A3> fourth, Class<A4> fifth, Class<A5> sixth,
            Class<A6> seventh, Class<A7> eighth, Class<A8> ninth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth, sixth, seventh,
                eighth, ninth);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
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
            Class<A6> seventh, Class<A7> eighth, Class<A8> ninth,
            Class<A9> tenth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth,
                fifth, sixth, seventh,
                eighth, ninth, tenth);
    }

    @Override
    public <A extends AggregateRoot<I>, I> void into(Unit<A> unit) {
        context.tupleAssemblerOf(Tuples.itemClasses(unit), dtoClass).mergeDtoIntoAggregate(dto, unit);
    }

    @Override
    public <A0 extends AggregateRoot<?>, A1 extends AggregateRoot<?>> void into(Pair<A0, A1> pair) {
        context.tupleAssemblerOf(Tuples.itemClasses(pair), dtoClass).mergeDtoIntoAggregate(dto, pair);
    }

    @Override
    public <A0 extends AggregateRoot<?>, A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>> void into(Triplet<A0, A1, A2> triplet) {
        context.tupleAssemblerOf(Tuples.itemClasses(triplet), dtoClass)
                .mergeDtoIntoAggregate(dto, triplet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>> void into(Quartet<A0, A1, A2, A3> quartet) {
        context.tupleAssemblerOf(Tuples.itemClasses(quartet), dtoClass)
                .mergeDtoIntoAggregate(dto, quartet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>> void into(Quintet<A0, A1, A2, A3, A4> quintet) {
        context.tupleAssemblerOf(Tuples.itemClasses(quintet), dtoClass)
                .mergeDtoIntoAggregate(dto, quintet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>> void into(Sextet<A0, A1, A2, A3, A4, A5> sextet) {
        context.tupleAssemblerOf(Tuples.itemClasses(sextet), dtoClass)
                .mergeDtoIntoAggregate(dto, sextet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>> void into(Septet<A0, A1, A2, A3, A4, A5, A6> septet) {
        context.tupleAssemblerOf(Tuples.itemClasses(septet), dtoClass)
                .mergeDtoIntoAggregate(dto, septet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>> void into(Octet<A0, A1, A2, A3, A4, A5, A6, A7> octet) {
        context.tupleAssemblerOf(Tuples.itemClasses(octet), dtoClass).mergeDtoIntoAggregate(dto, octet);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>> void into(Ennead<A0, A1, A2, A3, A4, A5, A6, A7, A8> ennead) {
        context.tupleAssemblerOf(Tuples.itemClasses(ennead), dtoClass)
                .mergeDtoIntoAggregate(dto, ennead);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>,
            A9 extends AggregateRoot<?>> void into(Decade<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9> decade) {
        context.tupleAssemblerOf(Tuples.itemClasses(decade), dtoClass)
                .mergeDtoIntoAggregate(dto, decade);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends AggregateRoot<I>, I> void into(A aggregateRoot) {
        context.assemblerOf((Class<A>) aggregateRoot.getClass(), dtoClass)
                .mergeDtoIntoAggregate(dto, aggregateRoot);
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>> void into(A0 first, A1 second) {
        into(Pair.with(first, second));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third) {
        into(Triplet.with(first, second, third));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth) {
        into(Quartet.with(first, second, third, fourth));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth) {
        into(Quintet.with(first, second, third, fourth, fifth));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth) {
        into(Sextet.with(first, second, third, fourth, fifth, sixth));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth,
            A6 seventh) {
        into(Septet.with(first, second, third, fourth, fifth, sixth, seventh));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth,
            A6 seventh, A7 eighth) {
        into(Octet.with(first, second, third, fourth, fifth, sixth, seventh, eighth));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth,
            A6 seventh, A7 eighth, A8 ninth) {
        into(Ennead.with(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
    }

    @Override
    public <A0 extends AggregateRoot<?>,
            A1 extends AggregateRoot<?>,
            A2 extends AggregateRoot<?>,
            A3 extends AggregateRoot<?>,
            A4 extends AggregateRoot<?>,
            A5 extends AggregateRoot<?>,
            A6 extends AggregateRoot<?>,
            A7 extends AggregateRoot<?>,
            A8 extends AggregateRoot<?>,
            A9 extends AggregateRoot<?>> void into(A0 first, A1 second, A2 third, A3 fourth, A4 fifth, A5 sixth,
            A6 seventh, A7 eighth, A8 ninth, A9 tenth) {
        into(Decade.with(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth));
    }

    @Override
    public MergeSingle with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public MergeSingle with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public MergeSingle with(String qualifier) {
        context.setAssemblerQualifier(Names.named(qualifier));
        return this;
    }
}
