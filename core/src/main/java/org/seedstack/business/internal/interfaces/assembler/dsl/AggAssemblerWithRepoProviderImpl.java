/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl;

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerWithRepoAndFactProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerWithRepoProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AggAssemblerWithRepoProviderImpl<A extends AggregateRoot<?>> extends BaseAggAssemblerWithRepoProviderImpl<A> implements AggAssemblerWithRepoProvider<A>, AggAssemblerWithRepoAndFactProvider<A> {

    private final Class<A> aggregateClass;
    private final Object dto;

    public AggAssemblerWithRepoProviderImpl(InternalRegistry registry, Class<A> aggregateClass, Object dto) {
        super(registry);
        this.aggregateClass = aggregateClass;
        this.dto = dto;
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public AggAssemblerWithRepoAndFactProvider<A> fromRepository() {
        // Just redirect to the expected DSL path
        return this;
    }

    @SuppressWarnings("unchecked")
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
    protected A loadFromRepo(Object key) {
        Repository repository = registry.repositoryOf(aggregateClass);
        //noinspection unchecked
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
