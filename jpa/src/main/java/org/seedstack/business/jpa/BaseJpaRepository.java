/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa;

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.BaseRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;


/**
 * This class serves as inheritance base for the JPA repositories.
 *
 * @param <A> JPA Entity Type (DDD: Aggregate)
 * @param <K> key type
 *            
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public abstract class BaseJpaRepository<A extends AggregateRoot<K>, K> extends BaseRepository<A, K> {

    @Inject 
    protected EntityManager entityManager;

    /**
     * Constructor.
     */
    public BaseJpaRepository() {
    }

    protected BaseJpaRepository(Class<A> aggregateRootClass, Class<K> kClass) {
        super(aggregateRootClass, kClass);
    }

    @Override
	protected A doLoad(K id) {
        return entityManager.find(getAggregateRootClass(), id);
    }

    @Override
    @Deprecated
    protected void doDelete(K id) {
    	entityManager.remove(load(id));
    }

    @Override
    protected void doDelete(A aggregate) {
        entityManager.remove(aggregate);
    }

    @Override
    protected void doPersist(A aggregate) {
    	entityManager.persist(aggregate);
    }

    @Override
    protected A doSave(A aggregate) {
        return entityManager.merge(aggregate);
    }
    
    
}
