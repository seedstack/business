/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

public class LessThanSpecification<T extends Comparable<?>> extends ComparableSpecification<T> {
    public LessThanSpecification(T expectedValue) {
        super(expectedValue, -1);
    }

    @Override
    public String toString() {
        return "< " + String.valueOf(expectedValue);
    }
}
