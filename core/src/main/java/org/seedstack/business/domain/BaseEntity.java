/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.shed.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * This abstract class is the base class for all Entities in Seed Business Framework.
 *
 * It provides an {@code equals()} method based on the entity identity. This also enforce
 * the entity to valid, i.e. not null. Otherwise a SeedException will be thrown.
 *
 * @param <ID> The identifier type.
 */
public abstract class BaseEntity<ID> implements Entity<ID> {
    /**
     * This method tries to find a field named "id" starting from the current class, going up in the hierarchy.
     * If the entity identity field is not named "id", this method must be overridden to return the entity identity value.
     *
     * @return the value of the identity field if found, null otherwise.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ID getId() {
        Field identityField = findIdentityField();
        if (identityField != null) {
            return (ID) ReflectUtils.getValue(identityField, this);
        } else {
            return null;
        }
    }

    /**
     * Computes the hash code on the entity identity returned by {@link #getId()}. This method can be overridden
     * but be sure to respect the equality semantics for entities when doing so.
     *
     * @return Hash code built from all non-transient fields.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /**
     * Computes the equality on the entity identity returned by {@link #getId()}. This method can be overridden
     * but be sure to respect the equality semantics for entities when doing so.
     *
     * @param other other object
     * @return true if the other object is of the same class and has the same identity a this entity, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || !(other == null || getClass() != other.getClass()) && Objects.equals(getId(), ((BaseEntity<?>) other).getId());
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), getId());
    }

    private Field findIdentityField() {
        Class<?> currentClass = getClass();
        Field identityField;
        do {
            try {
                identityField = getClass().getDeclaredField("id");
                if (identityField != null) {
                    return ReflectUtils.makeAccessible(identityField);
                }
            } catch (NoSuchFieldException e) {
                // ignore
            }
        } while (BaseEntity.class.isAssignableFrom(currentClass = currentClass.getSuperclass()));
        return null;
    }
}
