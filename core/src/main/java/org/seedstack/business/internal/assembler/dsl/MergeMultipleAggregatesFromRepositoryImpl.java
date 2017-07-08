/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;

import java.util.Optional;
import java.util.stream.Stream;


class MergeMultipleAggregatesFromRepositoryImpl<A extends AggregateRoot<ID>, ID, D> extends AbstractMergeWithRepository implements MergeFromRepository<MergeAs<A>>, MergeFromRepositoryOrFactory<MergeAs<A>> {
    private final Class<A> aggregateClass;
    private final Stream<D> dtoStream;

    MergeMultipleAggregatesFromRepositoryImpl(Context context, Stream<D> dtoStream, Class<A> aggregateClass) {
        super(context);
        this.aggregateClass = aggregateClass;
        this.dtoStream = dtoStream;
    }

    @Override
    public MergeFromRepositoryOrFactory<MergeAs<A>> fromRepository() {
        return this;
    }

    @Override
    public MergeAs<A> fromFactory() {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            A aggregateFromFactory = createFromFactory(
                    aggregateClass,
                    getDtoInfoResolver().resolveAggregate(dto).parameters()
            );
            mergeAggregateFromDto(aggregateFromFactory, dto);
            return aggregateFromFactory;
        }));
    }

    @Override
    public MergeAs<A> orFail() throws AggregateNotFoundException {
        return new MergeAsImpl<>(
                dtoStream.map(dto -> {
                    ID identifier = createIdentifier(getDtoInfoResolver().resolveId(dto), aggregateClass);
                    A aggregateRootFromRepository = loadFromRepository(identifier)
                            .orElseThrow(() -> new AggregateNotFoundException(String.format(
                                    "Unable to load aggregate %s for id: %s",
                                    aggregateClass.getName(),
                                    identifier)
                            ));
                    mergeAggregateFromDto(aggregateRootFromRepository, dto);
                    return aggregateRootFromRepository;
                })
        );
    }

    @Override
    public MergeAs<A> orFromFactory() {
        return new MergeAsImpl<>(
                dtoStream.map(dto -> {
                    ID identifier = createIdentifier(getDtoInfoResolver().resolveId(dto), aggregateClass);
                    A aggregateRootFromRepositoryOrFactory = loadFromRepository(identifier)
                            .orElseGet(() -> createFromFactory(
                                    aggregateClass,
                                    getDtoInfoResolver().resolveAggregate(dto).parameters())
                            );
                    mergeAggregateFromDto(aggregateRootFromRepositoryOrFactory, dto);
                    return aggregateRootFromRepositoryOrFactory;
                })
        );
    }

    private Optional<A> loadFromRepository(ID identifier) {
        return getContext().repositoryOf(aggregateClass).get(identifier);
    }
}
