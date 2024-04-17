/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

/**
 * A string-specialized specification that is satisfied only when the expected and the candidate
 * strings are equal according to the given {@link StringSpecification.Options}.
 */
public class StringEqualSpecification extends StringSpecification {

    /**
     * Creates a string-equality specification.
     *
     * @param expectedString the string to compare the candidate against.
     */
    public StringEqualSpecification(String expectedString) {
        super(expectedString);
    }

    /**
     * Creates a string-equality specification.
     *
     * @param expectedString the string to compare the candidate against.
     * @param options        the comparison options.
     */
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
