/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.util.inmemory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;
import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.LimitOption;
import org.seedstack.business.domain.OffsetOption;
import org.seedstack.business.domain.SortOption;
import org.seedstack.business.specification.Specification;

/**
 * An helper base class that can be extended to create an in-memory
 * implementation of a {@link org.seedstack.business.domain.Repository}. It is
 * backed by a {@link ConcurrentHashMap} per aggregate root class. As such,
 * insertion order is NOT guaranteed to be maintained through the repository
 * lifecycle.
 */
public abstract class BaseInMemoryRepository<A extends AggregateRoot<I>, I> extends BaseRepository<A, I> {
    private static final ConcurrentMap<Class<?>, Map<?, ?>> buckets = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private final Map<I, A> bucket = (Map<I, A>) buckets.computeIfAbsent(getAggregateRootClass(),
            key -> new ConcurrentHashMap<I, A>());

    /**
     * Creates a base in-memory repository. Actual classes managed by the repository
     * are determined by reflection.
     */
    protected BaseInMemoryRepository() {
    }

    /**
     * Creates a base in-memory repository. Actual classes managed by the repository
     * are specified explicitly.
     *
     * @param aggregateRootClass the actual aggregate root class.
     * @param idClass            the actual aggregate identifier class.
     */
    protected BaseInMemoryRepository(Class<A> aggregateRootClass, Class<I> idClass) {
        super(aggregateRootClass, idClass);
    }

    @Override
    public void add(A a) throws AggregateExistsException {
        if (bucket.containsKey(a.getId())) {
            throw new AggregateExistsException("Value already exist on repository");
        }
        bucket.put(a.getId(), a);
    }

    @Override
    public Stream<A> get(Specification<A> specification, Option... options) {
        Stream<A> stream = bucket.values()
                .stream()
                .filter(specification.asPredicate());
        for (Option option : options) {
            if (option instanceof OffsetOption) {
                stream = stream.skip(((OffsetOption) option).getOffset());
            } else if (option instanceof LimitOption) {
                stream = stream.limit(((LimitOption) option).getLimit());
            } else if (option instanceof SortOption) {
                stream = stream.sorted(((SortOption) option).buildComparator());
            }
        }
        return stream;
    }

    @Override
    public long remove(Specification<A> specification) {
        Iterator<Map.Entry<I, A>> iterator = bucket.entrySet()
                .iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<I, A> next = iterator.next();
            if (specification.isSatisfiedBy(next.getValue())) {
                iterator.remove();
                count++;
            }
        }
        return count;
    }
}
