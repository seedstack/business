/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import org.seedstack.business.internal.domain.IdentityResolver;
import org.seedstack.shed.cache.Cache;
import org.seedstack.shed.cache.CacheParameters;
import org.seedstack.shed.reflect.Classes;
import org.seedstack.shed.reflect.ReflectUtils;

/**
 * An helper base class that can be extended to create a domain entity. If extending this base class
 * is not desirable, you can instead do one of the following: <ul> <li>Implement {@link
 * Entity},</li> <li>Annotate your class with {@link DomainEntity} (but this limits the ability to
 * use framework features).</li> </ul>
 *
 * <p> This base class provides {@link #equals(Object)} and {@link #hashCode()} methods based on the
 * entity identity. It also provides auto-detection of the identity field by reflection (see {@link
 * BaseEntity#getId()}).</p>
 *
 * @param <I> The type of the entity identifier.
 */
public abstract class BaseEntity<I> implements Entity<I> {
    private static final Cache<Class<?>, Optional<Field>> identityFields = Cache.create(
            new CacheParameters<Class<?>, Optional<Field>>()
                    .setInitialSize(256)
                    .setMaxSize(1024)
                    .setLoadingFunction(BaseEntity::resolveIdentityField)
    );

    private static Optional<Field> resolveIdentityField(Class<?> entityClass) {
        Optional<Field> field = IdentityResolver.INSTANCE.resolveField(entityClass);
        if (!field.isPresent()) {
            field = Classes.from(entityClass)
                    .traversingSuperclasses()
                    .field("id");
        }
        return field.map(ReflectUtils::makeAccessible);
    }

    /**
     * Returns the identifier of the entity if present. Starting from the current class and going up
     * in the hierarchy, this method tries to find: <ul> <li>A field annotated with {@link
     * Identity},</li> <li>Then if not found, a field named "id".</li> </ul>
     *
     * <p>If the entity the identity field does not satisfy any of the conditions above, this method
     * must be overridden to return the entity identity value. </p>
     *
     * @return the value of the identity field if found, null otherwise.
     */
    @Override
    @SuppressWarnings("unchecked")
    public I getId() {
        return identityFields.get(getClass())
                .map(f -> (I) ReflectUtils.getValue(f, this))
                .orElse(null);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            // equal reference
            return true;
        }
        if (other == null) {
            // comparison to null
            return false;
        }
        Class<? extends BaseEntity> thisClass = getClass();
        Class<?> otherClass = other.getClass();
        if (!(other instanceof Entity) || (!thisClass.isAssignableFrom(otherClass) && !otherClass.isAssignableFrom(
                thisClass))) {
            // objects are not from the same class hierarchy
            return false;
        }
        return Objects.equals(getId(), ((Entity<?>) other).getId());
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), getId());
    }
}
