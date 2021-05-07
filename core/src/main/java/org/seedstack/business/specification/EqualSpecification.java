/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import java.util.Objects;

/**
 * A specification satisfied only when the expected and the candidate values are equal according to
 * {@link Objects#equals(Object, Object)}.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public class EqualSpecification<T> implements Specification<T> {

    private final T expectedValue;

    /**
     * Creates an specification of equality.
     *
     * @param expectedValue the value used to do the equality check against.
     */
    public EqualSpecification(T expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return Objects.equals(candidate, expectedValue);
    }

    /**
     * Returns the expected value.
     *
     * @return the value equality is checked against.
     */
    public T getExpectedValue() {
        return expectedValue;
    }

    @Override
    public String toString() {
        return "= " + String.valueOf(expectedValue);
    }
}
