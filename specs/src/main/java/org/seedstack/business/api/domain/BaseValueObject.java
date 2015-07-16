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

import org.apache.commons.lang.builder.*;

import java.io.Serializable;

/**
 * This class is the inheritance base for ValueObject implementations. It offers specific {@code equals()} and
 * {@code hashCode()} methods.
 */
public abstract class BaseValueObject implements ValueObject, Serializable, Comparable<BaseValueObject> {

    private static final long serialVersionUID = -6131435316889092990L;

	private transient int cachedHashCode;

	protected BaseValueObject() {
	}

    /**
     * @return Hash code built from all non-transient fields.
     */
    @Override
    public final int hashCode() {
        // Using a local variable to ensure that we only do a single read
        // of the cachedHashCode field, to avoid race conditions.
        // It doesn't matter if several threads compute the hash code and overwrite
        // each other, but it's important that we never return 0, which could happen
        // with multiple reads of the cachedHashCode field.
        //
        // See java.lang.String.hashCode()
        int h = cachedHashCode;
        if (h == 0) {
            // Lazy initialization of hash code.
            // Value objects are immutable, so the hash code never changes.
            h = HashCodeBuilder.reflectionHashCode(this, false);
            cachedHashCode = h;
        }

        return h;
    }

    /**
     * @param other other object
     * @return True if other object has the same value as this value object.
     */
    @Override
    public final boolean equals(final Object other) {
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false);
    }


    @Override
	public int compareTo(BaseValueObject o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}

}
