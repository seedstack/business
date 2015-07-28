/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.domain.stereotypes.Delete;
import org.seedstack.business.api.domain.stereotypes.Persist;
import org.seedstack.business.api.domain.stereotypes.Read;

/**
 * This interface has to be extended in order to create a Domain Repository <em>interface</em>.
 * <p>
 * To be a valid repository interface, Type must respect the followings:
 * </p>
 * <ul>
 *   <li>be an interface</li>
 *   <li>extends {@link Repository}</li>
 * </ul>
 * The following is a valid Domain repository interface.
 * <pre>
 *  public interface ProductRepository extends Repository&lt;Product,String&gt; {
 *     // nothing needed, but you can add methods with specifics
 *     // then implements them
 *  }
 * </pre>
 * <p>
 * Then this interface has to be implemented by the actual repository implementation.
 * </p>
 *
 * @param <A> the type of the aggregate root class.
 * @param <K>       the type of the aggregate root class.
 * @author epo.jemba@ext.mpsa.com
 */
@DomainRepository
public interface Repository<A extends AggregateRoot<K>, K> {

    /**
     * Loads an aggregate from the persistence by its key.
     *
     * @param id the aggregate key
     * @return the loaded aggregate
     */
    @Read
    A load(K id);

    /**
     * Deletes an aggregate from the persistence by its key.
     *
     * @param aggregate the aggregate to delete
     */
    @Delete
    void delete(A aggregate);

    /**
     * Creates an aggregate in the persistence.
     *
     * @param aggregate the aggregate to persist
     */
    @Persist
    void persist(A aggregate);

    /**
     * Updates an aggregate in the persistence
     *
     * @param aggregate the aggregate to save
     * @return the updated aggregate
     */
    @Persist
    A save(A aggregate);

    /**
     * @return the aggregate root class.
     */
    Class<A> getAggregateRootClass();

    /**
     * @return the aggregate key.
     */
    Class<K> getKeyClass();

}