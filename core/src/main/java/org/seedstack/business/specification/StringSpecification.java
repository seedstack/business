/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import com.google.common.base.CharMatcher;

/**
 * Base for string-based value specifications.
 */
public abstract class StringSpecification implements Specification<String> {
    protected final String expectedString;
    protected final Options options;

    public StringSpecification(String expectedString, Options options) {
        this.options = options;
        this.expectedString = expectedString;
    }

    @Override
    public boolean isSatisfiedBy(String candidateValue) {
        if (options.isTrimmed()) {
            candidateValue = CharMatcher.WHITESPACE.trimFrom(candidateValue);
        } else {
            if (options.isLeftTrimmed()) {
                candidateValue = CharMatcher.WHITESPACE.trimLeadingFrom(candidateValue);
            }
            if (options.isRightTrimmed()) {
                candidateValue = CharMatcher.WHITESPACE.trimTrailingFrom(candidateValue);
            }
        }
        return isSatisfiedByString(candidateValue);
    }

    protected abstract boolean isSatisfiedByString(String candidateString);

    public String getExpectedString() {
        return expectedString;
    }

    public Options getOptions() {
        return options;
    }

    public interface Options {
        boolean isLeftTrimmed();

        boolean isRightTrimmed();

        boolean isTrimmed();

        boolean isIgnoringCase();
    }
}
