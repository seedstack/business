/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import static org.seedstack.business.internal.utils.FieldUtils.getFieldValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.seedstack.business.internal.utils.FieldUtils;

/**
 * A specification that restricts the application of another specification to an attribute of the
 * candidate object. It supports nested attributes with a dot notation.
 *
 * @param <T> the type of the candidate object the specification applies to.
 * @param <V> the type of the attribute which is targeted by this specification.
 */
public class AttributeSpecification<T, V> implements Specification<T> {
    private static final String ATTRIBUTE_PATH_PATTERN = "\\.";

    private final String path;
    private final String[] splitPath;
    private final Specification<V> valueSpecification;

    /**
     * Creates an attribute specification.
     *
     * @param path               the path to the target attribute, supporting dot notation for nested
     *                           attributes.
     * @param valueSpecification the specification that the value of the target attribute must
     *                           satisfy.
     */
    public AttributeSpecification(String path, Specification<V> valueSpecification) {
        this.path = path;
        this.splitPath = path.split(ATTRIBUTE_PATH_PATTERN);
        this.valueSpecification = valueSpecification;
    }

    /**
     * Returns the path.
     *
     * @return the path to the target attribute.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the specification on the value.
     *
     * @return the specification that the value of the target attribute must satisfy.
     */
    public Specification<V> getValueSpecification() {
        return valueSpecification;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return isSatisfiedBy(candidate, 0);
    }

    private boolean isSatisfiedBy(Object candidate, int pathIndex) {
        if (candidate != null) {
            Optional<Field> fieldOptional = FieldUtils.resolveField(candidate.getClass(), splitPath[pathIndex]);
            if (fieldOptional.isPresent()) {
                Object result = getFieldValue(candidate, fieldOptional.get());
                if (pathIndex < splitPath.length - 1) {
                    if (result instanceof Collection) {
                        return ((Collection<?>) result).stream()
                                .anyMatch(item -> isSatisfiedBy(item, pathIndex + 1));
                    } else if (result.getClass()
                            .isArray()) {
                        return Arrays.stream((Object[]) result)
                                .anyMatch(item -> isSatisfiedBy(item, pathIndex + 1));
                    } else if (result instanceof Map) {
                        return ((Collection<?>) ((Map<?, ?>) result).values()).stream()
                                .anyMatch(item -> isSatisfiedBy(item, pathIndex + 1));
                    } else {
                        return isSatisfiedBy(result, pathIndex + 1);
                    }
                } else {
                    return isSatisfiedByValue(result);
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return path + " " + String.valueOf(valueSpecification);
    }

    @SuppressWarnings("unchecked")
    private boolean isSatisfiedByValue(Object result) {
        return valueSpecification.isSatisfiedBy((V) result);
    }
}
