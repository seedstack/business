/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Producible;


/**
 * Interface for domain value objects.
 */
@DomainValueObject
public interface ValueObject extends DomainObject, Producible {

    /**
     * Value Object equality is computed on all attributes.
     *
     * @param other The other value object.
     * @return true if the both value objects have equal attributes, false otherwise.
     */
    @Override
    boolean equals(Object other);

    @Override
    int hashCode();

    @Override
    String toString();

}
