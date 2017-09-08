/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.util.inmemory;

import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.LimitOption;
import org.seedstack.business.domain.OffsetOption;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.domain.SortOption;
import org.seedstack.business.specification.Specification;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * Default repository for in-memory persistence.
 * <p>
 * When no specific repository exist for the aggregate, this repository will be injected for
 * {@link Repository} with the qualifier {@literal @}InMemory.
 * </p>
 */
public class BaseInMemoryRepository<A extends AggregateRoot<ID>, ID> extends BaseRepository<A, ID> {
    private static final ConcurrentMap<Class<?>, Map<?, ?>> buckets = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    private final Map<ID, A> bucket = (Map<ID, A>) buckets.computeIfAbsent(getAggregateRootClass(), key -> new ConcurrentHashMap<ID, A>());

    public BaseInMemoryRepository() {
    }

    protected BaseInMemoryRepository(Class<A> aggregateRootClass, Class<ID> idClass) {
        super(aggregateRootClass, idClass);
    }

    protected Map<ID, A> getBucket() {
        return bucket;
    }

    @Override
    public void add(A a) throws AggregateExistsException {
        getBucket().put(a.getId(), a);
    }

    @Override
    public Stream<A> get(Specification<A> specification, Option... options) {
        Stream<A> aStream = getBucket().values().stream().filter(specification.asPredicate());
        for (Option option : options) {
            if (option instanceof OffsetOption) {
                aStream = aStream.skip(((OffsetOption) option).getOffset());
            } else if (option instanceof LimitOption) {
                aStream = aStream.limit(((LimitOption) option).getLimit());
            } else if (option instanceof SortOption) {
                // TODO
            }
        }
        return aStream;
    }

    @Override
    public long remove(Specification<A> specification) {
        Iterator<Map.Entry<ID, A>> iterator = getBucket().entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<ID, A> next = iterator.next();
            if (specification.isSatisfiedBy(next.getValue())) {
                iterator.remove();
                count++;
            }
        }
        return count;
    }
}
