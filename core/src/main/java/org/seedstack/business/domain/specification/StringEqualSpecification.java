/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import com.google.common.base.CharMatcher;
import org.seedstack.business.domain.AggregateRoot;

import static com.google.common.base.Preconditions.checkArgument;

public class StringEqualSpecification<A extends AggregateRoot<?>> extends AbstractValueSpecification<A> {
    private final StringValueOptions stringValueOptions;

    public StringEqualSpecification(String path, String expectedValue, StringValueOptions stringValueOptions) {
        super(path, expectedValue);
        this.stringValueOptions = stringValueOptions;
    }

    @Override
    protected boolean isSatisfiedByValue(Object candidateValue) {
        checkArgument(candidateValue instanceof String, "Candidate value is not a string");
        String candidateString = (String) candidateValue;
        if (stringValueOptions.isTrimmed()) {
            candidateString = CharMatcher.WHITESPACE.trimFrom(candidateString);
        } else {
            if (stringValueOptions.isLeftTrimmed()) {
                candidateString = CharMatcher.WHITESPACE.trimLeadingFrom(candidateString);
            }
            if (stringValueOptions.isRightTrimmed()) {
                candidateString = CharMatcher.WHITESPACE.trimTrailingFrom(candidateString);
            }
        }
        if (stringValueOptions.isIgnoringCase()) {
            return candidateString.equalsIgnoreCase((String) expectedValue);
        } else {
            return candidateString.equals(expectedValue);
        }
    }

    @Override
    public String toString() {
        return String.format("%s = %s", path, expectedValue.toString());
    }
}
