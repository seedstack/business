/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for specifications that compare their expected and candidate value. Those values must
 * implement {@link Comparable}.
 *
 * @param <T> the type of the compared value.
 */
public abstract class ComparableSpecification<T extends Comparable<? super T>> implements Specification<T> {

    private static final Set<Class<?>> convertibleToLong = new HashSet<>();

    static {
        convertibleToLong.add(Integer.class);
        convertibleToLong.add(Short.class);
        convertibleToLong.add(Character.class);
        convertibleToLong.add(Byte.class);
    }

    private final T expectedValue;
    private final Class<? extends Comparable> expectedValueClass;

    /**
     * Creates a comparable specification.
     *
     * @param expectedValue the value used to do the comparison against.
     */
    protected ComparableSpecification(T expectedValue) {
        checkNotNull(expectedValue, "Expected value cannot be null");
        Class<? extends Comparable> expectedValueClass = expectedValue.getClass();
        if (convertibleToLong.contains(expectedValueClass)) {
            this.expectedValue = asLong((Number) expectedValue);
        } else {
            this.expectedValue = expectedValue;
        }
        this.expectedValueClass = this.expectedValue.getClass();
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        Class<? extends Comparable> candidateClass = candidate.getClass();
        if (!candidateClass.equals(expectedValueClass) && convertibleToLong.contains(candidateClass)) {
            return isExpected(asLong((Number) candidate).compareTo(expectedValue));
        } else {
            return isExpected(candidate.compareTo(expectedValue));
        }
    }

    /**
     * Returns the value serving as reference in the comparison.
     *
     * @return the value used to do the comparison against.
     */
    public T getExpectedValue() {
        return expectedValue;
    }

    /**
     * Returns if the comparison result returned by {@link Comparable#compareTo(Object)} is the one expected.
     *
     * @param compareToResult the result returned by {@link Comparable#compareTo(Object)}
     * @return true if the result is expected, false otherwise.
     */
    protected abstract boolean isExpected(int compareToResult);

    @SuppressWarnings("unchecked")
    private T asLong(Number expectedValue) {
        return (T) Long.valueOf(expectedValue.longValue());
    }
}
