/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

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
import org.seedstack.business.internal.Tuples;

import java.lang.annotation.Annotation;

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
    public <A extends AggregateRoot<ID>, ID> MergeFromRepository<A> into(Class<A> aggregateRootClass) {
        return new MergeSingleAggregateFromRepositoryImpl<>(context, dto, aggregateRootClass);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeFromRepository<Pair<A1, A2>> into(Class<A1> first, Class<A2> second) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeFromRepository<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeFromRepository<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeFromRepository<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeFromRepository<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth, sixth);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeFromRepository<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth, sixth, seventh);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeFromRepository<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeFromRepository<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeFromRepository<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new MergeSingleTupleFromRepositoryImpl<>(context, dto, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth);
    }

    // --------------------------------------------------------------------------------------------------------

    @Override
    public <A extends AggregateRoot<ID>, ID> void into(Unit<A> unit) {
        context.tupleAssemblerOf(Tuples.itemClasses(unit), dtoClass).mergeDtoIntoAggregate(dto, unit);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    void into(Pair<A1, A2> pair) {
        context.tupleAssemblerOf(Tuples.itemClasses(pair), dtoClass).mergeDtoIntoAggregate(dto, pair);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    void into(Triplet<A1, A2, A3> triplet) {
        context.tupleAssemblerOf(Tuples.itemClasses(triplet), dtoClass).mergeDtoIntoAggregate(dto, triplet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    void into(Quartet<A1, A2, A3, A4> quartet) {
        context.tupleAssemblerOf(Tuples.itemClasses(quartet), dtoClass).mergeDtoIntoAggregate(dto, quartet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    void into(Quintet<A1, A2, A3, A4, A5> quintet) {
        context.tupleAssemblerOf(Tuples.itemClasses(quintet), dtoClass).mergeDtoIntoAggregate(dto, quintet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    void into(Sextet<A1, A2, A3, A4, A5, A6> sextet) {
        context.tupleAssemblerOf(Tuples.itemClasses(sextet), dtoClass).mergeDtoIntoAggregate(dto, sextet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    void into(Septet<A1, A2, A3, A4, A5, A6, A7> septet) {
        context.tupleAssemblerOf(Tuples.itemClasses(septet), dtoClass).mergeDtoIntoAggregate(dto, septet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    void into(Octet<A1, A2, A3, A4, A5, A6, A7, A8> octet) {
        context.tupleAssemblerOf(Tuples.itemClasses(octet), dtoClass).mergeDtoIntoAggregate(dto, octet);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    void into(Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9> ennead) {
        context.tupleAssemblerOf(Tuples.itemClasses(ennead), dtoClass).mergeDtoIntoAggregate(dto, ennead);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    void into(Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> decade) {
        context.tupleAssemblerOf(Tuples.itemClasses(decade), dtoClass).mergeDtoIntoAggregate(dto, decade);
    }

    // --------------------------------------------------------------------------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public <A extends AggregateRoot<ID>, ID>
    void into(A aggregateRoot) {
        context.assemblerOf((Class<A>) aggregateRoot.getClass(), dtoClass).mergeDtoIntoAggregate(dto, aggregateRoot);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    void into(A1 first, A2 second) {
        into(Pair.with(first, second));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third) {
        into(Triplet.with(first, second, third));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth) {
        into(Quartet.with(first, second, third, fourth));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth) {
        into(Quintet.with(first, second, third, fourth, fifth));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth) {
        into(Sextet.with(first, second, third, fourth, fifth, sixth));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh) {
        into(Septet.with(first, second, third, fourth, fifth, sixth, seventh));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth) {
        into(Octet.with(first, second, third, fourth, fifth, sixth, seventh, eighth));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth) {
        into(Ennead.with(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    void into(A1 first, A2 second, A3 third, A4 fourth, A5 fifth, A6 sixth, A7 seventh, A8 eighth, A9 ninth, A10 tenth) {
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
}
