/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.seed.SeedException;


/**
 * This abstract class is the base class for all Entities in Seed Business Framework.
 *
 * It provides an {@code equals()} method based on the entity identity. This also enforce
 * the entity to valid, i.e. not null. Otherwise a SeedException will be thrown.
 *
 * @param <ID> The type of the entityId of the Entity.
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseEntity<ID> implements Entity<ID> {
    @Override
    public abstract ID getEntityId();

    /**
     * Computes the hash code on the entity identity returned by {@link #getEntityId()}. This method can be overridden
     * but be sure to respect the equality semantics for entities.
     * when doing so.
     *
     * @return Hash code built from all non-transient fields.
     */
    @Override
    public int hashCode() {
        return getIdentity().hashCode();
    }

    /**
     * Computes the equality on the entity identity returned by {@link #getEntityId()}. This method can be overridden
     * but be sure to respect the equality semantics for entities.
     *
     * @param other other object
     * @return true if the other object is of the same class and has the same identity a this entity, false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        ID entityId = getIdentity();
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return entityId.equals(this.getClass().cast(other).getEntityId());
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), getEntityId());
    }

    private ID getIdentity() {
        ID entityId = getEntityId();
        if (entityId == null) {
            throw SeedException.createNew(DomainErrorCodes.ENTITY_WITHOUT_IDENTITY_ISSUE).put("className", getClass().getName());
        }
        return entityId;
    }
}
