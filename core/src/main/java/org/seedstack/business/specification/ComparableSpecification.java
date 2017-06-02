/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

public abstract class ComparableSpecification<T extends Comparable<?>> implements Specification<T> {
    protected final T expectedValue;
    private final int expectedResult;

    public ComparableSpecification(T expectedValue, int expectedResult) {
        this.expectedValue = expectedValue;
        this.expectedResult = expectedResult;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return candidate.compareTo(uncheckedCast(expectedValue)) == expectedResult;
    }

    @SuppressWarnings("unchecked")
    private static <T> T uncheckedCast(Comparable<?> pThingy) {
        return (T) pThingy;
    }
}
