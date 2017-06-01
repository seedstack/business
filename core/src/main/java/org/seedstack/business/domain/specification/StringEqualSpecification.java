/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;

public class StringEqualSpecification<A extends AggregateRoot<?>> extends AbstractStringSpecification<A> {
    public StringEqualSpecification(String path, String expectedValue, StringValueOptions stringValueOptions) {
        super(path, expectedValue, stringValueOptions);
    }

    @Override
    protected boolean isSatisfiedByString(String candidateString) {
        if (options.isIgnoringCase()) {
            return candidateString.equalsIgnoreCase(expectedValue);
        } else {
            return candidateString.equals(expectedValue);
        }
    }

    @Override
    public String toString() {
        return String.format("%s = %s", path, expectedValue);
    }
}
