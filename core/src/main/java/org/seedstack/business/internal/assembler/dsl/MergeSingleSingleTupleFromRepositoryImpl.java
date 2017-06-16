/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.MergeSingleTupleFromRepositoryOrFactory;
import org.seedstack.business.assembler.dsl.MergeSingleTupleWithRepository;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.Tuples;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MergeSingleSingleTupleFromRepositoryImpl<T extends Tuple> extends AbstractMergeWithRepository implements MergeSingleTupleWithRepository<T>, MergeSingleTupleFromRepositoryOrFactory<T> {

    private final List<Class<? extends AggregateRoot<?>>> aggregateClasses;
    private final Object dto;

    public MergeSingleSingleTupleFromRepositoryImpl(AssemblerDslContext context, List<Class<? extends AggregateRoot<?>>> aggregateClasses, Object dto) {
        super(context);
        this.aggregateClasses = aggregateClasses;
        this.dto = dto;
    }

    // --------------------------- TupleAggAssemblerWithRepoProvider

    @Override
    public MergeSingleTupleFromRepositoryOrFactory<T> fromRepository() {
        // it just redirect to the good interface
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T fromFactory() {
        List<Object> aggregateRoots = new ArrayList<>();
        ParameterHolder parameterHolder = dtoInfoResolver.resolveAggregate(dto);
        int aggregateIndex = 0;
        for (Object o : aggregateClasses) {
            if (o instanceof Class<?>) {
                Class<? extends AggregateRoot<?>> aggregateClass = (Class<? extends AggregateRoot<?>>) o;
                Factory<?> factory = context.genericFactoryOf(aggregateClass);
                Object aggregate = getAggregateFromFactory(factory, aggregateClass, parameterHolder.parametersOfAggregateRoot(aggregateIndex));
                aggregateRoots.add(aggregate);
            } else {
                // TODO replace by a seed exception
                throw new IllegalArgumentException(o + " should be a class. the .to(Tuple aggregateClasses) method only accepts tuple of aggregate root classes.");
            }
            aggregateIndex++;
        }
        return (T) assembleWithDto(Tuples.create(aggregateRoots));
    }

    // ---------------------------  TupleAggAssemblerWithRepoAndFactProvider

    @SuppressWarnings("unchecked")
    @Override
    public T orFail() throws AggregateNotFoundException {
        // list of triplet - each triplet contains the aggregate root instance, its class and its id (useful if the AR is null).
        List<Triplet<Optional<AggregateRoot<?>>, Class<?>, Object>> aggregateRootsMetadata = loadFromRepository();

        boolean shouldThrow = false;

        List<AggregateRoot<?>> aggregateRoots = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder().append("Unable to load ");
        for (Triplet<Optional<AggregateRoot<?>>, Class<?>, Object> triplet : aggregateRootsMetadata) {

            if (!triplet.getValue0().isPresent()) {
                // If at least one aggregate root is null we throw a AggregateNotFoundException
                shouldThrow = true;
                stringBuilder.append("aggregate: ").append(triplet.getValue1()).append(" for id: ").append(triplet.getValue2());
            } else {
                aggregateRoots.add(triplet.getValue0().get());
            }
        }

        if (shouldThrow) {
            throw new AggregateNotFoundException(stringBuilder.toString());
        }

        return (T) assembleWithDto(Tuples.create(aggregateRoots));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T orFromFactory() {
        // list of triplet - each triplet contains the aggregate root instance, its class and its id (useful if the AR is null).
        List<Triplet<Optional<AggregateRoot<?>>, Class<?>, Object>> aggregateRootsMetadata = loadFromRepository();

        List<AggregateRoot<?>> aggregateRoots = new ArrayList<>();

        StringBuilder errorMessage = new StringBuilder().append("Unable to load ");
        for (Triplet<Optional<AggregateRoot<?>>, Class<?>, Object> triplet : aggregateRootsMetadata) {

            if (!triplet.getValue0().isPresent()) {
                errorMessage.append("aggregate: ").append(triplet.getValue1()).append(" for id: ").append(triplet.getValue2());
            } else {
                aggregateRoots.add(triplet.getValue0().get());
            }
        }

        T result;
        if (aggregateRootsMetadata.isEmpty()) {
            // should not append
            result = null;
        } else if (aggregateRoots.isEmpty()) {
            // No aggregate root persisted -> fallback on factories
            result = fromFactory();
        } else if (aggregateRootsMetadata.size() != aggregateRoots.size()) {
            // data are inconsistent some required aggregate roots are persisted but not all
            throw new IllegalStateException(errorMessage.toString());
        } else {
            // all aggregate roots are loaded -> assemble them and return them
            result = assembleWithDto(Tuples.create(aggregateRoots));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private List<Triplet<Optional<AggregateRoot<?>>, Class<?>, Object>> loadFromRepository() {
        Tuple ids = resolveIds(dto, aggregateClasses);

        List<Triplet<Optional<AggregateRoot<?>>, Class<?>, Object>> aggregateRoots = new ArrayList<>();
        for (int i = 0; i < ids.getSize(); i++) {
            Class<? extends AggregateRoot<?>> aggregateClass = aggregateClasses.get(i);
            Object id = ids.getValue(i);

            Repository repository = context.repositoryOf(aggregateClass);
            Optional<AggregateRoot<?>> aggregateRoot = repository.get(id);
            aggregateRoots.add(new Triplet<>(aggregateRoot, aggregateClass, id));
        }

        return aggregateRoots;
    }

    /**
     * Assemble one or a tuple of aggregate root from a dto.
     *
     * @param aggregateRoots the aggregate root(s) to assemble
     * @param <T> type of aggregate root(s). It could be a {@code Tuple} or an {@code AggregateRoot}
     * @return the assembled aggregate root(s)
     */
    @SuppressWarnings("unchecked")
    protected  <T> T assembleWithDto(T aggregateRoots) {
        Assembler assembler = context.tupleAssemblerOf(aggregateClasses, dto.getClass());
        assembler.mergeAggregateWithDto(aggregateRoots, dto);
        return aggregateRoots;
    }

}
