/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An helper base class that can be extended to create a domain value object. If extending this base
 * class is not desirable, you can instead do one of the following: <ul> <li>Implement {@link
 * ValueObject},</li> <li>Annotate your class with {@link DomainValueObject}.</li> </ul> <p> This
 * base class provides an implementation of {@link #equals(Object)} and {@link #hashCode()} methods
 * based on all the value object attributes. <strong>This mechanism is reflection-based and can be
 * overridden to improve performance. When doing so, take care of respecting the semantics of
 * value object equality.</strong></p>
 *
 * @see ValueObject
 * @see DomainValueObject
 */
public abstract class BaseValueObject implements ValueObject, Serializable {

    /**
     * Computes the hash code by reflection on all non-transient fields. This default implementation uses reflection
     * and may be costly. It can be overridden by an optimized version if performance is critical. Be sure to respect
     * the equality semantics for value objects when doing so.
     *
     * @return Hash code built from all non-transient fields.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    /**
     * Computes the equality by reflection on all non-transient fields. This default implementation uses reflection
     * and may be overridden by an optimized version if performance is critical. Be sure to respect the equality
     * semantics for value objects when doing so.
     *
     * @param other other object
     * @return true if the other object is a {@link ValueObject} and has the same value as this value
     * object, false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || !(other == null || getClass() != other.getClass()) && EqualsBuilder.reflectionEquals(
                this, other, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, false);
    }
}
