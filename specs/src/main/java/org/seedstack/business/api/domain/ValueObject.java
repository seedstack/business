/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.Producible;


/**
 * A value object, as described in the DDD book.
 * <p> 
 * See {@link BaseValueObject} for the base implementation.
 */
@DomainValueObject
public interface ValueObject extends DomainObject, Producible {

    /**
     * Value objects compare by the values of their attributes, they don't have an identity.
     *
     * @param other The other value object.
     * @return <code>true</code> if the given value object's and this value object's attributes are the same.
     */
    boolean equals(Object other);

    @Override
    int hashCode();

    @Override
    String toString();

}
