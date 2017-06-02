/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

public class StringEqualSpecification extends StringSpecification {
    public StringEqualSpecification(String expectedString, Options options) {
        super(expectedString, options);
    }

    @Override
    protected boolean isSatisfiedByString(String candidateString) {
        if (options.isIgnoringCase()) {
            return candidateString.equalsIgnoreCase(expectedString);
        } else {
            return candidateString.equals(expectedString);
        }
    }

    @Override
    public String toString() {
        return "= " + String.valueOf(expectedString);
    }
}
