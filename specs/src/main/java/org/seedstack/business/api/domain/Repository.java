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

import org.seedstack.business.api.domain.annotations.DomainRepository;
import org.seedstack.business.api.domain.annotations.stereotypes.Delete;
import org.seedstack.business.api.domain.annotations.stereotypes.Persist;
import org.seedstack.business.api.domain.annotations.stereotypes.Read;

/**
 * This interface has to be extended in order to create a Domain Repository <em>interface</em>.
 * <p/>
 * To be a valid repository interface, Type must respect the followings :
 * <ul>
 * <li> be an interface
 * <li> extends {@link Repository}
 * </ul>
 * <p/>
 * The following is a valid Domain repository interface.
 * <pre>
 *
 *  public interface ProductRepository extends Repository{@literal <Product,String>} {
 *     // nothing needed, but you can add methods with specifics
 *     // then implements them
 *  }
 * </pre>
 *
 * <p/>
 * Then this interface has to be implemented by the actual repository implementation . See {@link BaseRepository} for details.
 *
 * @param <AGGREGATE> the type of the aggregate root class.
 * @param <KEY>       the type of the aggregate root class.
 * @author epo.jemba@ext.mpsa.com
 */
@DomainRepository
public interface Repository<AGGREGATE extends AggregateRoot<KEY>, KEY> {

    /**
     * Loads an aggregate from the persistence by its key.
     *
     * @param id the aggregate key
     * @return the loaded aggregate
     */
    @Read
    AGGREGATE load(KEY id);

    /**
     * Deletes an aggregate from the persistence by its key.
     *
     * @param id the aggregate key
     * @Deprecated This method will be removed in the next version. The {@link #delete(AggregateRoot)} method should be
     * used instead.
     */
    @Delete
    @Deprecated
    void delete(KEY id);

    /**
     * Deletes an aggregate from the persistence by its key.
     *
     * @param aggregate the aggregate to delete
     */
    @Delete
    void delete(AGGREGATE aggregate);

    /**
     * Creates an aggregate in the persistence.
     *
     * @param aggregate the aggregate to persist
     */
    @Persist
    void persist(AGGREGATE aggregate);

    /**
     * Updates an aggregate in the persistence
     *
     * @param aggregate the aggregate to save
     * @return the updated aggregate
     */
    @Persist
    AGGREGATE save(AGGREGATE aggregate);

    /**
     * @return the aggregate root class.
     */
    Class<AGGREGATE> getAggregateRootClass();

    /**
     * @return the aggregate key.
     */
    Class<KEY> getKeyClass();

}