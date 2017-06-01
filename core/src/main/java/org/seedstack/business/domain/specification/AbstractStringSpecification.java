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

/**
 * Base for string-based value specifications.
 *
 * @param <A> the aggregate specification.
 */
public abstract class AbstractStringSpecification<A extends AggregateRoot<?>> extends AbstractValueSpecification<A, String> {
    protected final StringValueOptions options;

    public AbstractStringSpecification(String path, String expectedValue, StringValueOptions options) {
        super(path, expectedValue);
        this.options = options;
    }

    @Override
    protected boolean isSatisfiedByValue(String candidateValue) {
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

    public StringValueOptions getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", path, expectedValue);
    }
}
