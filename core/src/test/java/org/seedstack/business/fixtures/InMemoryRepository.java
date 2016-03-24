/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.seed.persistence.inmemory.api.InMemory;

import java.util.Map;

/**
 * Repository for in memory persistence.
 * <p>
 * When no specific repository exist for the aggregate, this repository will be injected for
 * {@link org.seedstack.business.domain.Repository} with the qualifier {@literal @}InMemory.
 * </p>
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class InMemoryRepository<Aggregate extends AggregateRoot<Key>, Key> extends BaseRepository<Aggregate, Key> {

    @InMemory
    protected Map<Object, Object> inMemorySortedMap;

    /**
     * Constructor.
     */
    public InMemoryRepository() {
    }

    /**
     * Constructor.
     *
     * @param aggregateRootClass the aggregate root class
     * @param kClass             the aggregate key class
     */
    public InMemoryRepository(Class<Aggregate> aggregateRootClass, Class<Key> kClass) {
        super(aggregateRootClass, kClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Aggregate load(Key id) {
        return (Aggregate) inMemorySortedMap.get(id);
    }

    @Override
    public boolean exists(Key id) {
        return inMemorySortedMap.containsKey(id);
    }

    @Override
    public long count() {
        return inMemorySortedMap.size();
    }

    @Override
    public void clear() {
        inMemorySortedMap.clear();
    }

    @Override
    public void delete(Key id) {
        inMemorySortedMap.remove(id);
    }

    @Override
    public void delete(Aggregate aggregate) {
        inMemorySortedMap.remove(aggregate.getEntityId());
    }

    @Override
    public void persist(Aggregate aggregate) {
        this.inMemorySortedMap.put(aggregate.getEntityId(), aggregate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Aggregate save(Aggregate aggregate) {
        return (Aggregate) this.inMemorySortedMap.put(aggregate.getEntityId(), aggregate);
    }

}
