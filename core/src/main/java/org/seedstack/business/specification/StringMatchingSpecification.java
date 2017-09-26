/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import java.util.regex.Pattern;

/**
 * A string-specialized specification that is satisfied only when the expected and the candidate
 * strings are matching according to the given {@link StringSpecification.Options}. Matching
 * supports the following wildcards: <ul> <li>'{@literal *}' which matches any zero-length or longer
 * character sequence,</li> <li>'{@literal ?}' which matches any character.</li> </ul>
 */
public class StringMatchingSpecification extends StringSpecification {

    /**
     * Wildcard for matching any zero-length or longer character sequence.
     */
    public static final String MULTI_CHARACTER_WILDCARD = "*";
    /**
     * Wildcard for matching any character.
     */
    public static final String SINGLE_CHARACTER_WILDCARD = "?";
    private volatile Pattern ignoringCasePattern;
    private volatile Pattern pattern;

    /**
     * Creates a string-matching specification.
     *
     * @param expectedString the string that the candidate is expected to match.
     * @param options        the matching options.
     */
    public StringMatchingSpecification(String expectedString, Options options) {
        super(expectedString, options);
    }

    @Override
    protected boolean isSatisfiedByString(String candidateString) {
        if (options.isIgnoringCase()) {
            if (ignoringCasePattern == null) {
                ignoringCasePattern = Pattern.compile(expectedString.replace(MULTI_CHARACTER_WILDCARD, ".*")
                        .replace(SINGLE_CHARACTER_WILDCARD, "."), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            }
            return ignoringCasePattern.matcher(candidateString)
                    .matches();
        } else {
            if (pattern == null) {
                pattern = Pattern.compile(expectedString.replace(MULTI_CHARACTER_WILDCARD, ".*")
                        .replace(SINGLE_CHARACTER_WILDCARD, "."));
            }
            return pattern.matcher(candidateString)
                    .matches();
        }
    }

    @Override
    public String toString() {
        return "=~ " + String.valueOf(expectedString);
    }
}
