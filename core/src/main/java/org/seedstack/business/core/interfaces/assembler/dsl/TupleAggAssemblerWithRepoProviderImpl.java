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

import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.api.interfaces.assembler.dsl.TupleAggAssemblerWithRepoAndFactProvider;
import org.seedstack.business.api.interfaces.assembler.dsl.TupleAggAssemblerWithRepoProvider;
import org.seedstack.business.api.Tuples;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class TupleAggAssemblerWithRepoProviderImpl<T extends Tuple> extends BaseAggAssemblerWithRepoProviderImpl implements TupleAggAssemblerWithRepoProvider<T>, TupleAggAssemblerWithRepoAndFactProvider<T> {

    private final List<Repository<?, ?>> repositories = new ArrayList<Repository<?, ?>>(2);

    public TupleAggAssemblerWithRepoProviderImpl(InternalRegistry registry, AssemblerContext assemblerContext) {
        super(registry, assemblerContext);
    }

    // --------------------------- TupleAggAssemblerWithRepoProvider

    @Override
    public TupleAggAssemblerWithRepoAndFactProvider<T> fromRepository() {
        for (Object o : assemblerContext.getAggregateClasses()) {
            repositories.add(registry.repositoryOf((Class<? extends AggregateRoot<?>>) o));
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T fromFactory() {
        List<Object> aggregateRoots = new ArrayList<Object>();
        for (Object o : assemblerContext.getAggregateClasses()) {
            if (o instanceof Class<?>) {
                Class<? extends AggregateRoot<?>> aggregateClass = (Class<? extends AggregateRoot<?>>) o;
                GenericFactory<?> genericFactory = registry.genericFactoryOf(aggregateClass);
                aggregateRoots.add(getAggregateFromFactory(genericFactory, aggregateClass));
            }
        }
        return (T) assembleWithDto(Tuples.create(aggregateRoots));
    }

    // ---------------------------  TupleAggAssemblerWithRepoAndFactProvider

    @SuppressWarnings("unchecked")
    @Override
    public T orFail() throws AggregateNotFoundException {
        List<Object> aggregateRoots = new ArrayList<Object>();
        for (Object o : assemblerContext.getAggregateClasses()) {
            Class<? extends AggregateRoot<?>> aggregateClass = (Class<? extends AggregateRoot<?>>) o;
            Object id = resolveId(assemblerContext.getDto(), aggregateClass);
            AggregateRoot<?> a = loadFromRepo(id);

            if (a == null) {
                throw new AggregateNotFoundException(String.format("Unable to load aggregate %s for id: %s", aggregateClass, id));
            }

            aggregateRoots.add(a);
        }
        return (T) assembleWithDto(Tuples.create(aggregateRoots));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T thenFromFactory() {
        List<Object> aggregateRoots = new ArrayList<Object>();
        for (Object o : assemblerContext.getAggregateClasses()) {
            // load from the repository
            Class<? extends AggregateRoot<?>> aggregateClass = (Class<? extends AggregateRoot<?>>) o;
            Object id = resolveId(assemblerContext.getDto(), aggregateClass);
            AggregateRoot<?> a = loadFromRepo(id);

            if (a != null) {
                // then assemble the dto in the previously created aggregate root
                aggregateRoots.add(a);
            } else {
                // otherwise fallback on the factory
                GenericFactory<?> genericFactory = registry.genericFactoryOf(aggregateClass);
                aggregateRoots.add(getAggregateFromFactory(genericFactory, aggregateClass));
            }
        }
        return Tuples.create(aggregateRoots);
    }

}
