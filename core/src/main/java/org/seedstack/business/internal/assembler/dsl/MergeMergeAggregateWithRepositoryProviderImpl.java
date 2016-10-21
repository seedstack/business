/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.assembler.dsl.MergeAggregateWithRepositoryThenFactoryProvider;
import org.seedstack.business.assembler.dsl.MergeAggregateWithRepositoryProvider;
import org.seedstack.business.assembler.dsl.AggregateNotFoundException;


public class MergeMergeAggregateWithRepositoryProviderImpl<A extends AggregateRoot<?>> extends BaseAggAssemblerWithRepoProviderImpl<A> implements MergeAggregateWithRepositoryProvider<A>, MergeAggregateWithRepositoryThenFactoryProvider<A> {

    private final Class<A> aggregateClass;
    private final Object dto;

    public MergeMergeAggregateWithRepositoryProviderImpl(AssemblerDslContext context, Class<A> aggregateClass, Object dto) {
        super(context);
        this.aggregateClass = aggregateClass;
        this.dto = dto;
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public MergeAggregateWithRepositoryThenFactoryProvider<A> fromRepository() {
        // Just redirect to the expected DSL path
        return this;
    }

    @Override
    public A fromFactory() {
        return fromFactory(aggregateClass,dto);
    }

    /**
     * Loads an aggregate roots from a repository.
     *
     * @param key the aggregate roots identity
     * @return the loaded aggregate root
     */
    @SuppressWarnings("unchecked")
    protected A loadFromRepo(Object key) {
        Repository repository = context.repositoryOf(aggregateClass);
        return (A) repository.load(key);
    }

    // --------------------------- AggAssemblerWithRepoAndFactProvider methods

    @Override
    public A orFail() throws AggregateNotFoundException {
        Object id = resolveId(dto, aggregateClass);
        A a = loadFromRepo(id);

        if (a == null) {
            throw new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", aggregateClass, id));
        }

        return assembleWithDto(a, dto);
    }

    @Override
    public A orFromFactory() {
        // load from the repository
        Object id = resolveId(dto, aggregateClass);
        A a = loadFromRepo(id);

        if (a != null) {
            // then assemble the dto in the previously created aggregate root
            return assembleWithDto(a, dto);
        } else {
            // otherwise fallback on the factory
            return fromFactory();
        }
    }

}
