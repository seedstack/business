/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import com.google.common.base.CharMatcher;

/**
 * Base class for specifications that compare strings. Allows to specify string-related comparison
 * options like trimming or case sensitivity.
 */
public abstract class StringSpecification implements Specification<String> {

    protected final String expectedString;
    protected final Options options;

    /**
     * Creates a string specification.
     *
     * @param expectedString the string to compare the candidate against.
     */
    protected StringSpecification(String expectedString) {
        this.expectedString = expectedString;
        this.options = Options.empty();
    }

    /**
     * Creates a string specification.
     *
     * @param expectedString the string to compare the candidate against.
     * @param options        the comparison options.
     */
    protected StringSpecification(String expectedString, Options options) {
        this.expectedString = expectedString;
        this.options = options;
    }

    @Override
    public boolean isSatisfiedBy(String candidateValue) {
        String testValue = candidateValue;
        if (options.isTrimmed()) {
            testValue = CharMatcher.whitespace().trimFrom(testValue);
        } else {
            if (options.isLeadTrimmed()) {
                testValue = CharMatcher.whitespace().trimLeadingFrom(testValue);
            }
            if (options.isTailTrimmed()) {
                testValue = CharMatcher.whitespace().trimTrailingFrom(testValue);
            }
        }
        return isSatisfiedByString(testValue);
    }

    protected abstract boolean isSatisfiedByString(String candidateString);

    /**
     * Returns the expected string.
     *
     * @return the string that is compared against the candidate.
     */
    public String getExpectedString() {
        return expectedString;
    }

    /**
     * Returns the comparison options.
     *
     * @return the comparison options.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Options used for comparing strings.
     */
    public static class Options {
        private boolean leadTrimmed;
        private boolean tailTrimmed;
        private boolean ignoringCase;

        public static Options empty() {
            return new Options();
        }

        /**
         * Returns if the comparison should ignore leading whitespace.
         *
         * @return true if the comparison should ignore leading whitespace, false otherwise.
         */
        public boolean isLeadTrimmed() {
            return leadTrimmed;
        }

        /**
         * Sets if the comparison should ignore leading whitespace.
         *
         * @param leadTrimmed true if the comparison should ignore leading whitespace, false otherwise.
         * @return itself for chaining.
         */
        public Options setLeadTrimmed(boolean leadTrimmed) {
            this.leadTrimmed = leadTrimmed;
            return this;
        }

        /**
         * Returns if the comparison should ignore trailing whitespace.
         *
         * @return true if the comparison should ignore trailing whitespace, false otherwise.
         */
        public boolean isTailTrimmed() {
            return tailTrimmed;
        }

        /**
         * Sets if the comparison should ignore trailing whitespace.
         *
         * @param tailTrimmed true if the comparison should ignore trailing whitespace, false otherwise.
         * @return itself for chaining.
         */
        public Options setTailTrimmed(boolean tailTrimmed) {
            this.tailTrimmed = tailTrimmed;
            return this;
        }

        /**
         * Returns if the comparison should ignore leading and trailing whitespace.
         *
         * @return true if the comparison should ignore leading and trailing whitespace, false otherwise.
         */
        public boolean isTrimmed() {
            return isLeadTrimmed() && isTailTrimmed();
        }

        /**
         * Sets if the comparison should ignore leading and trailing whitespace.
         *
         * @param trimmed true if the comparison should ignore leading and trailing whitespace, false otherwise.
         * @return itself for chaining.
         */
        public Options setTrimmed(boolean trimmed) {
            setLeadTrimmed(trimmed);
            setTailTrimmed(trimmed);
            return this;
        }

        /**
         * Returns if the comparison should ignore the case.
         *
         * @return true if the comparison should be sensitive to case differences, false otherwise.
         */
        public boolean isIgnoringCase() {
            return ignoringCase;
        }

        /**
         * Sets if the comparison should ignore the case.
         *
         * @param ignoringCase true if the comparison should be sensitive to case differences, false otherwise.
         * @return itself for chaining.
         */
        public Options setIgnoringCase(boolean ignoringCase) {
            this.ignoringCase = ignoringCase;
            return this;
        }
    }
}
