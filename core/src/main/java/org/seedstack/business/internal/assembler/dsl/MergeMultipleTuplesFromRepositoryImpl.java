/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.Tuples;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;


class MergeMultipleTuplesFromRepositoryImpl<T extends Tuple, D> extends AbstractMergeWithRepository implements MergeFromRepository<MergeAs<T>>, MergeFromRepositoryOrFactory<MergeAs<T>> {
    private final Class<? extends AggregateRoot<?>>[] aggregateClasses;
    private final Stream<D> dtoStream;

    @SafeVarargs
    MergeMultipleTuplesFromRepositoryImpl(Context context, Stream<D> dtoStream, Class<? extends AggregateRoot<?>>... aggregateClasses) {
        super(context);
        this.dtoStream = dtoStream;
        this.aggregateClasses = aggregateClasses;
    }

    @Override
    public MergeFromRepositoryOrFactory<MergeAs<T>> fromRepository() {
        return this;
    }

    @Override
    public MergeAs<T> fromFactory() {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            AggregateRoot<?>[] aggregateRoots = new AggregateRoot[aggregateClasses.length];
            ParameterHolder parameterHolder = getDtoInfoResolver().resolveAggregate(dto);

            for (int i = 0; i < aggregateClasses.length; i++) {
                aggregateRoots[i] = createFromFactory(aggregateClasses[i], parameterHolder.parametersOfAggregateRoot(i));
            }

            T tuple = Tuples.create((Object[]) aggregateRoots);
            mergeTupleFromDto(tuple, dto);
            return tuple;
        }));
    }

    @Override
    public MergeAs<T> orFail() throws AggregateNotFoundException {
        return loadOrCreate(false);
    }

    @Override
    public MergeAs<T> orFromFactory() {
        return loadOrCreate(true);
    }

    private MergeAs<T> loadOrCreate(boolean loadMissingFromFactory) {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            Object[] identifiers = createIdentifiers(getDtoInfoResolver().resolveId(dto), aggregateClasses);
            AggregateRoot<?>[] aggregateRoots = loadFromRepository(identifiers);
            ParameterHolder parameterHolder = getDtoInfoResolver().resolveAggregate(dto);
            StringBuilder stringBuilder = new StringBuilder();
            boolean hasErrors = false;

            for (int i = 0; i < aggregateRoots.length; i++) {
                if (aggregateRoots[i] == null) {
                    if (loadMissingFromFactory) {
                        aggregateRoots[i] = createFromFactory(aggregateClasses[i], parameterHolder.parametersOfAggregateRoot(i));
                    } else {
                        hasErrors = true;
                        stringBuilder.append(aggregateClasses[i].getName()).append("[").append(identifiers[i]).append("] ");
                    }
                }
            }

            if (hasErrors) {
                throw new AggregateNotFoundException(stringBuilder.toString());
            }

            T tuple = Tuples.create((Object[]) aggregateRoots);
            mergeTupleFromDto(tuple, dto);
            return tuple;
        }));
    }

    @SuppressWarnings("unchecked")
    private <A extends AggregateRoot<ID>, ID> A[] loadFromRepository(Object[] identifiers) {
        checkState(identifiers.length == aggregateClasses.length, "Aggregate and identifier cardinality don't match");
        A[] aggregateRoots = (A[]) new AggregateRoot[aggregateClasses.length];
        for (int i = 0; i < aggregateClasses.length; i++) {
            aggregateRoots[i] = getContext().repositoryOf((Class<A>) aggregateClasses[i]).get((ID) identifiers[i]).orElse(null);
        }
        return aggregateRoots;
    }

    @SuppressWarnings("unchecked")
    private void mergeTupleFromDto(T tuple, D dto) {
        Assembler<Tuple, D> tupleAssembler = getContext().tupleAssemblerOf(aggregateClasses, (Class<D>) dto.getClass());
        tupleAssembler.mergeAggregateWithDto(tuple, dto);
    }
}
