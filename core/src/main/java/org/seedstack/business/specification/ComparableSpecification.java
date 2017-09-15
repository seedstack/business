/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ComparableSpecification<T extends Comparable<? super T>> implements Specification<T> {
    private static final Set<Class<?>> convertibleToLong = new HashSet<>();

    static {
        convertibleToLong.add(Integer.class);
        convertibleToLong.add(Short.class);
        convertibleToLong.add(Character.class);
        convertibleToLong.add(Byte.class);
    }

    protected final T expectedValue;
    protected final Class<? extends Comparable> expectedValueClass;
    private final int expectedResult;

    public ComparableSpecification(T expectedValue, int expectedResult) {
        checkNotNull(expectedValue, "Expected value cannot be null");
        Class<? extends Comparable> expectedValueClass = expectedValue.getClass();
        if (convertibleToLong.contains(expectedValueClass)) {
            this.expectedValue = asLong((Number) expectedValue);
        } else {
            this.expectedValue = expectedValue;
        }
        this.expectedValueClass = this.expectedValue.getClass();
        this.expectedResult = expectedResult;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        Class<? extends Comparable> candidateClass = candidate.getClass();
        if (candidateClass != expectedValueClass && convertibleToLong.contains(candidateClass)) {
            return asLong((Number) candidate).compareTo(expectedValue) == expectedResult;
        } else {
            return candidate.compareTo(expectedValue) == expectedResult;
        }
    }

    public T getExpectedValue() {
        return expectedValue;
    }

    @SuppressWarnings("unchecked")
    private T asLong(Number expectedValue) {
        return (T) Long.valueOf(expectedValue.longValue());
    }
}
