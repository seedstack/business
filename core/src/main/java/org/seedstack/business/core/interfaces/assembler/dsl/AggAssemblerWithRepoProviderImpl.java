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
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerWithRepoAndFactProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggAssemblerWithRepoProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggAssemblerWithRepoProviderImpl<A extends AggregateRoot<?>> extends BaseAggAssemblerWithRepoProviderImpl implements AggAssemblerWithRepoProvider<A>, AggAssemblerWithRepoAndFactProvider<A> {

    public AggAssemblerWithRepoProviderImpl(InternalRegistry registry, AssemblerContext assemblerContext) {
        super(registry, assemblerContext);
    }

    // --------------------------- AggAssemblerWithRepoProvider

    @Override
    public AggAssemblerWithRepoAndFactProvider<A> fromRepository() {
        assemblerContext.setRepository(registry.repositoryOf(assemblerContext.getAggregateClass()));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public A fromFactory() {
        GenericFactory<A> genericFactory = (GenericFactory<A>) registry.genericFactoryOf(assemblerContext.getAggregateClass());
        ParameterHolder parameterHolder = dtoInfoResolver.resolveAggregate(assemblerContext.getDto());
        A aggregateRoot = (A) getAggregateFromFactory(genericFactory, assemblerContext.getAggregateClass(), parameterHolder.parameters());
        return assembleWithDto(aggregateRoot);
    }

    /**
     * Loads an aggregate roots from a repository.
     *
     * @param key the aggregate roots identity
     * @param <A> the aggregate root type
     * @return the loaded aggregate root
     */
    protected <A> A loadFromRepo(Object key) {
        Repository repository = assemblerContext.getRepository();
        //noinspection unchecked
        return (A) repository.load(key);
    }

    // --------------------------- AggAssemblerWithRepoAndFactProvider methods

    @Override
    public A orFail() throws AggregateNotFoundException {
        Object id = resolveId(assemblerContext.getDto(), assemblerContext.getAggregateClass());
        A a = loadFromRepo(id);

        if (a == null) {
            throw new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", assemblerContext.getAggregateClass(), id));
        }

        return assembleWithDto(a);
    }

    @Override
    public A thenFromFactory() {
        // load from the repository
        Object id = resolveId(assemblerContext.getDto(), assemblerContext.getAggregateClass());
        A a = loadFromRepo(id);

        if (a != null) {
            // then assemble the dto in the previously created aggregate root
            return assembleWithDto(a);
        } else {
            // otherwise fallback on the factory
            return fromFactory();
        }
    }

    /**
     * Assemble one or a tuple of aggregate root from a dto.
     *
     * @param aggregateRoots the aggregate root(s) to assemble
     * @param <T> type of aggregate root(s). It could be a {@code Tuple} or an {@code AggregateRoot}
     * @return the assembled aggregate root(s)
     */
    protected  <T> T assembleWithDto(T aggregateRoots) {
        Assembler assembler = registry.assemblerOf(assemblerContext.getAggregateClass(), assemblerContext.getDto().getClass());
        //noinspection unchecked
        assembler.mergeAggregateWithDto(aggregateRoots, assemblerContext.getDto());
        return aggregateRoots;
    }

}
