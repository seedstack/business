/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;

import java.util.regex.Pattern;

public class StringMatchingSpecification<A extends AggregateRoot<?>> extends AbstractStringSpecification<A> {
    private volatile Pattern ignoringCasePattern;
    private volatile Pattern pattern;

    public StringMatchingSpecification(String path, String expectedPattern, StringValueOptions stringValueOptions) {
        super(path, expectedPattern, stringValueOptions);
    }

    @Override
    protected boolean isSatisfiedByString(String candidateString) {
        if (options.isIgnoringCase()) {
            if (ignoringCasePattern == null) {
                ignoringCasePattern = Pattern.compile(
                        expectedValue.replace("*", ".*").replace("?", "."),
                        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                );
            }
            return ignoringCasePattern.matcher(candidateString).matches();
        } else {
            if (pattern == null) {
                pattern = Pattern.compile(expectedValue.replace("*", ".*").replace("?", "."));
            }
            return pattern.matcher(candidateString).matches();
        }
    }

    @Override
    public String toString() {
        return String.format("%s =~ %s", path, expectedValue);
    }
}
