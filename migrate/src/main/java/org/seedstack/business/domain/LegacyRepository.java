/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.seed.Ignore;

@Ignore
@Deprecated
public interface LegacyRepository<A extends AggregateRoot<I>, I> extends Repository<A, I> {
    // This interface is marked @Ignore to avoid being detected as an actual repository

    /**
     * Loads an aggregate from the persistence by its identifier.
     *
     * @param id the aggregate identifier
     * @return the loaded aggregate or null if it doesn't exist.
     */
    default A load(I id) {
        return get(id).orElse(null);
    }

    /**
     * Check that the aggregate identified by the specified identifier exists.
     *
     * @param id the aggregate identifier
     * @return true if the aggregate exists, false otherwise.
     */
    default boolean exists(I id) {
        return contains(id);
    }

    /**
     * Returns the number of aggregates managed by this repository.
     *
     * @return the number of aggregates managed by this repository.
     */
    default long count() {
        return size();
    }

    /**
     * Deletes an aggregate from the persistence by its identifier.
     *
     * @param id the aggregate identifier.
     */
    default void delete(I id) {
        remove(id);
    }

    /**
     * Deletes an aggregate instance from the persistence.
     *
     * @param aggregate the aggregate to delete.
     */
    default void delete(A aggregate) {
        remove(aggregate);
    }

    /**
     * Creates an aggregate in the persistence.
     *
     * @param aggregate the aggregate to persist.
     */
    default void persist(A aggregate) {
        add(aggregate);
    }

    /**
     * Updates an aggregate in the persistence.
     *
     * @param aggregate the aggregate to save.
     * @return the updated aggregate.
     */
    default A save(A aggregate) {
        update(aggregate);
        return aggregate;
    }

    /**
     * Returns the aggregate identifier class.
     *
     * @return the aggregate identifier.
     */
    default Class<I> getKeyClass() {
        return getIdentifierClass();
    }
}
