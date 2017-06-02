/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import java.util.regex.Pattern;

public class StringMatchingSpecification extends StringSpecification {
    private volatile Pattern ignoringCasePattern;
    private volatile Pattern pattern;

    public StringMatchingSpecification(String expectedString, Options options) {
        super(expectedString, options);
    }

    @Override
    protected boolean isSatisfiedByString(String candidateString) {
        if (options.isIgnoringCase()) {
            if (ignoringCasePattern == null) {
                ignoringCasePattern = Pattern.compile(
                        expectedString.replace("*", ".*").replace("?", "."),
                        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                );
            }
            return ignoringCasePattern.matcher(candidateString).matches();
        } else {
            if (pattern == null) {
                pattern = Pattern.compile(expectedString.replace("*", ".*").replace("?", "."));
            }
            return pattern.matcher(candidateString).matches();
        }
    }

    @Override
    public String toString() {
        return "=~ " + String.valueOf(expectedString);
    }
}
