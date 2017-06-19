/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * This abstract class is the base class for all Entities in Seed Business Framework.
 *
 * It provides an {@code equals()} method based on the entity identity. This also enforce
 * the entity to valid, i.e. not null. Otherwise a SeedException will be thrown.
 *
 * @param <ID> The type of the entityId of the Entity.
 */
public abstract class BaseEntity<ID> implements Entity<ID> {
    /**
     * Computes the hash code on the entity identity returned by {@link #getId()}. This method can be overridden
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
     * Computes the equality on the entity identity returned by {@link #getId()}. This method can be overridden
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
        return entityId.equals(this.getClass().cast(other).getId());
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), getId());
    }

    private ID getIdentity() {
        ID entityId = getId();
        if (entityId == null) {
            throw new IllegalStateException("Entity without identity: " + getClass().getName());
        }
        return entityId;
    }
}
