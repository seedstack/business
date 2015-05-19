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

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.api.interfaces.assembler.dsl.AggsAssemblerWithRepoAndFactProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggsAssemblerWithRepoProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggsAssemblerWithRepoProviderImpl<A extends AggregateRoot<?>> extends BaseAggAssemblerWithRepoProviderImpl<A> implements AggsAssemblerWithRepoProvider<A>, AggsAssemblerWithRepoAndFactProvider<A> {

    private final Class<A> aggregateClass;
    private final List<?> dtos;

    public AggsAssemblerWithRepoProviderImpl(InternalRegistry registry, Class<A> aggregateClass, List<?> dtos) {
        super(registry);
        this.aggregateClass = aggregateClass;
        this.dtos = dtos;
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public AggsAssemblerWithRepoAndFactProvider<A> fromRepository() {
        // Just redirect to the expected DSL path
        return this;
    }

    @Override
    public List<A> fromFactory() {
        List<A> aggregateRoots = new ArrayList<A>(dtos.size());
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
    protected A loadFromRepo(Class<? extends AggregateRoot<?>> aggregateClass, Object key) {
        Repository repository = registry.repositoryOf(aggregateClass);
        //noinspection unchecked
        return (A) repository.load(key);
    }

    // --------------------------- AggAssemblerWithRepoAndFactProvider methods

    @Override
    public List<A> orFail() throws AggregateNotFoundException {
        List<A> aggregateRoots = new ArrayList<A>(dtos.size());
        for (Object dto : dtos) {
            Object id = resolveId(dto, aggregateClass);
            A a = loadFromRepo(aggregateClass, id);

            if (a == null) {
                throw new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", aggregateClass.getName(), id));
            }

            aggregateRoots.add(assembleWithDto(a, dto));
        }
        return aggregateRoots;
    }

    @Override
    public List<A> orFromFactory() {
        boolean atLeastOneAggregateNotFound = false;
        boolean atLeastOneAggregateFound = false;
        List<A> aggregateRoots = new ArrayList<A>(dtos.size());

        // load from the repository
        for (Object dto : dtos) {
            Object id = resolveId(dto, aggregateClass);
            A a = loadFromRepo(aggregateClass, id);
            if (a == null) {
                atLeastOneAggregateNotFound = true;
            } else {
                atLeastOneAggregateFound = true;
                aggregateRoots.add(a);
            }
            if (atLeastOneAggregateFound && atLeastOneAggregateNotFound) {
                throw new IllegalStateException("State non consistent some aggregate are persisted but not all.");
            }
        }

        if (atLeastOneAggregateNotFound) {
            // Then if none aggregate were persisted, fallback on factory
            return fromFactory();
        } else {
            return aggregateRoots;
        }
    }

}
