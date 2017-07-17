/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.internal.domain.IdentityResolver;
import org.seedstack.shed.reflect.Classes;
import org.seedstack.shed.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A base class implementing basic features of DDD entities.
 *
 * It provides {@link #equals(Object)} and {@link #hashCode()} methods based on the entity identity. It also provides
 * auto-detection of the identity field by reflection (see {@link BaseEntity#getId()}).
 *
 * @param <ID> The type of the entity identifier.
 */
public abstract class BaseEntity<ID> implements Entity<ID> {
    // This unbounded cache of identity fields can only grow up to the number of entity classes in the system
    private static final ConcurrentMap<Class<?>, Field> identityFields = new ConcurrentHashMap<>();

    /**
     * Starting from the current class and going up in the hierarchy, this method tries to find:
     * <ul>
     * <li>A field annotated with {@link Identity},</li>
     * <li>Then if not found, a field named "id".</li>
     * </ul>
     * If the entity the identity field does not satisfy any of the conditions above, this method must be overridden to return
     * the entity identity value.
     *
     * @return the value of the identity field if found, null otherwise.
     */
    @Override
    public ID getId() {
        Field identityField = identityFields.computeIfAbsent(
                getClass(),
                aClass -> IdentityResolver.INSTANCE.resolveField(aClass)
                        .map(Optional::of)
                        .orElseGet(() -> findIdentityByName(aClass))
                        .map(ReflectUtils::makeAccessible)
                        .orElse(null)
        );

        if (identityField != null) {
            return ReflectUtils.getValue(identityField, this);
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

    private Optional<Field> findIdentityByName(Class<?> aClass) {
        return Classes.from(aClass)
                .traversingSuperclasses()
                .fields()
                .filter(field -> field.getName().equals("id"))
                .findFirst();
    }
}
