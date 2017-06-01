/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * This interface is the parent type for all implementations of Entity in the Business Framework.
 * <p>
 * This interface should not be used directly as we already provide a base implementation for equals and hashcode.
 *
 * @param <ID> the type of the entityId
 */
@DomainEntity
public interface Entity<ID> extends DomainObject {

    /**
     * Gets the entity id.
     *
     * @return the entity id
     */
    ID getId();

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardles of other attributes.
     */
    boolean equals(Object other);

    @Override
    int hashCode();

    @Override
    String toString();

}
