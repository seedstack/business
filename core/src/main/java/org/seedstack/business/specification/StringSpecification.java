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
 * Base class for specifications that compare strings. Allows to specify string-related comparison options like trimming
 * or case sensitivity.
 */
public abstract class StringSpecification implements Specification<String> {
    protected final String expectedString;
    protected final Options options;

    /**
     * Creates a string specification.
     *
     * @param expectedString the string to compare the candidate against.
     * @param options        the comparison options.
     */
    protected StringSpecification(String expectedString, Options options) {
        this.options = options;
        this.expectedString = expectedString;
    }

    @Override
    public boolean isSatisfiedBy(String candidateValue) {
        if (options.isTrimmed()) {
            candidateValue = CharMatcher.WHITESPACE.trimFrom(candidateValue);
        } else {
            if (options.isLeadTrimmed()) {
                candidateValue = CharMatcher.WHITESPACE.trimLeadingFrom(candidateValue);
            }
            if (options.isTailTrimmed()) {
                candidateValue = CharMatcher.WHITESPACE.trimTrailingFrom(candidateValue);
            }
        }
        return isSatisfiedByString(candidateValue);
    }

    protected abstract boolean isSatisfiedByString(String candidateString);

    /**
     * @return the string that is compared against the candidate.
     */
    public String getExpectedString() {
        return expectedString;
    }

    /**
     * @return the comparison options.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Options used for comparing strings.
     */
    public interface Options {
        /**
         * @return true if the comparison should ignore leading whitespace, false otherwise.
         */
        boolean isLeadTrimmed();

        /**
         * @return true if the comparison should ignore trailing whitespace, false otherwise.
         */
        boolean isTailTrimmed();

        /**
         * @return true if the comparison should ignore leading and trailing whitespace, false otherwise.
         */
        boolean isTrimmed();

        /**
         * @return true if the comparison should be sensitive to case differences, false otherwise.
         */
        boolean isIgnoringCase();
    }
}
