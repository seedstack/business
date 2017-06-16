/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.dsl.MergeMultipleAggregatesFromRepository;
import org.seedstack.business.assembler.dsl.MergeMultipleAggregatesFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MergeMultipleMultipleAggregatesFromRepositoryImpl<A extends AggregateRoot<?>> extends AbstractMergeWithRepository<A> implements MergeMultipleAggregatesFromRepository<A>, MergeMultipleAggregatesFromRepositoryOrFactory<A> {

    private final Class<A> aggregateClass;
    private final List<?> dtos;

    public MergeMultipleMultipleAggregatesFromRepositoryImpl(AssemblerDslContext context, Class<A> aggregateClass, List<?> dtos) {
        super(context);
        this.aggregateClass = aggregateClass;
        this.dtos = dtos;
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public MergeMultipleAggregatesFromRepositoryOrFactory<A> fromRepository() {
        // Just redirect to the expected DSL path
        return this;
    }

    @Override
    public List<A> fromFactory() {
        List<A> aggregateRoots = new ArrayList<>(dtos.size());
        for (Object dto : dtos) {
            aggregateRoots.add(fromFactory(aggregateClass, dto));
        }

        return aggregateRoots;
    }

    /**
     * Loads an aggregate roots from a repository.
     *
     * @param key the aggregate roots identity
     * @return the loaded aggregate root
     */
    @SuppressWarnings("unchecked")
    protected Optional<A> loadFromRepo(Class<? extends AggregateRoot<?>> aggregateClass, Object key) {
        Repository repository = context.repositoryOf(aggregateClass);
        return repository.get(key);
    }

    // --------------------------- AggAssemblerWithRepoAndFactProvider methods

    @Override
    public List<A> orFail() throws AggregateNotFoundException {
        List<A> aggregateRoots = new ArrayList<>(dtos.size());
        for (Object dto : dtos) {
            Object id = resolveId(dto, aggregateClass);
            Optional<A> a = loadFromRepo(aggregateClass, id);

            if (!a.isPresent()) {
                throw new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", aggregateClass.getName(), id));
            }

            aggregateRoots.add(assembleWithDto(a.get(), dto));
        }
        return aggregateRoots;
    }

    @Override
    public List<A> orFromFactory() {
        return orFromFactory(true);
    }

    @Override
    public List<A> orFromFactory(boolean allowMixed) {
        boolean atLeastOneAggregateNotFound = false;
        boolean atLeastOneAggregateFound = false;
        List<A> aggregateRoots = new ArrayList<>(dtos.size());

        // load from the repository
        for (Object dto : dtos) {
            Object id = resolveId(dto, aggregateClass);
            Optional<A> a = loadFromRepo(aggregateClass, id);
            if (!a.isPresent()) {
                atLeastOneAggregateNotFound = true;
                aggregateRoots.add(fromFactory(aggregateClass, dto));
            } else {
                atLeastOneAggregateFound = true;
                aggregateRoots.add(a.get());
            }
            if (!allowMixed && atLeastOneAggregateFound && atLeastOneAggregateNotFound) {
                throw new IllegalStateException("State non consistent some aggregate are persisted but not all.");
            }
        }

        return aggregateRoots;
    }
}
