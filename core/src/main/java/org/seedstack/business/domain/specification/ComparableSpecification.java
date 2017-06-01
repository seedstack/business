/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;

public class ComparableSpecification<A extends AggregateRoot<?>> extends AbstractValueSpecification<A, Comparable<?>> {
    private final int expectedResult;

    public ComparableSpecification(String path, Comparable<?> expectedValue, int expectedResult) {
        super(path, expectedValue);
        this.expectedResult = expectedResult;
    }

    @Override
    protected boolean isSatisfiedByValue(Comparable<?> candidateValue) {
        return candidateValue.compareTo(uncheckedCast(expectedValue)) == expectedResult;
    }

    @SuppressWarnings("unchecked")
    private static <T> T uncheckedCast(Comparable<?> pThingy) {
        return (T) pThingy;
    }
}
