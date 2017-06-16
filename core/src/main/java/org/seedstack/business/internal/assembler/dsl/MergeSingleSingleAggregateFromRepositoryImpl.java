/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.dsl.MergeSingleAggregateFromRepository;
import org.seedstack.business.assembler.dsl.MergeSingleAggregateFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

import java.util.Optional;


public class MergeSingleSingleAggregateFromRepositoryImpl<A extends AggregateRoot<?>> extends AbstractMergeWithRepository<A> implements MergeSingleAggregateFromRepository<A>, MergeSingleAggregateFromRepositoryOrFactory<A> {

    private final Class<A> aggregateClass;
    private final Object dto;

    public MergeSingleSingleAggregateFromRepositoryImpl(AssemblerDslContext context, Class<A> aggregateClass, Object dto) {
        super(context);
        this.aggregateClass = aggregateClass;
        this.dto = dto;
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public MergeSingleAggregateFromRepositoryOrFactory<A> fromRepository() {
        // Just redirect to the expected DSL path
        return this;
    }

    @Override
    public A fromFactory() {
        return fromFactory(aggregateClass, dto);
    }

    /**
     * Loads an aggregate roots from a repository.
     *
     * @param key the aggregate roots identity
     * @return the loaded aggregate root
     */
    @SuppressWarnings("unchecked")
    protected Optional<A> loadFromRepo(Object key) {
        Repository repository = context.repositoryOf(aggregateClass);
        return repository.get(key);
    }

    // --------------------------- AggAssemblerWithRepoAndFactProvider methods

    @Override
    public A orFail() throws AggregateNotFoundException {
        Object id = resolveId(dto, aggregateClass);
        return loadFromRepo(id)
                .map(aggregate -> assembleWithDto(aggregate, dto))
                .orElseThrow(() -> new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", aggregateClass, id)));
    }

    @Override
    public A orFromFactory() {
        return loadFromRepo(resolveId(dto, aggregateClass))
                .map(aggregate -> assembleWithDto(aggregate, dto))
                .orElseGet(this::fromFactory);
    }
}
