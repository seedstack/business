/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * This class is the inheritance base for ValueObject implementations. It offers specific {@code equals()} and
 * {@code hashCode()} methods which are computed by reflection on all non-transient field values. If performance is critical,
 * consider overriding those methods with optimized versions. Be sure to respect the equality semantics for value objects
 * when doing so.
 */
public abstract class BaseValueObject implements ValueObject, Serializable {
    /**
     * Computes the hash code by reflection on all non-transient fields. This method can is quite costly and may be
     * overridden by an optimized version if performance is critical. Be sure to respect the equality semantics for value objects
     * when doing so.
     *
     * @return Hash code built from all non-transient fields.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    /**
     * Computes the equality by reflection on all non-transient fields. This method can is quite costly and may be
     * overridden by an optimized version if performance is critical. Be sure to respect the equality semantics for value objects
     * when doing so.
     *
     * @param other other object
     * @return true if the other object is a {@link ValueObject} and has the same value as this value object, false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, other, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, false);
    }
}
