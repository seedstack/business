/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.shed.reflect.Classes;

/**
 * A specification that restricts the application of another specification to an attribute of the
 * candidate object. It supports nested attributes with a dot notation.
 *
 * @param <T> the type of the candidate object the specification applies to.
 * @param <V> the type of the attribute which is targeted by this specification.
 */
public class AttributeSpecification<T, V> implements Specification<T> {

    private static final String ATTRIBUTE_PATH_PATTERN = "\\.";
    private static final ConcurrentMap<FieldReference, Optional<Field>> fieldCache = new ConcurrentHashMap<>();
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
            Optional<Field> fieldOptional = findField(candidate.getClass(), splitPath[pathIndex]);
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

    private Optional<Field> findField(Class<?> someClass, String fieldName) {
        FieldReference fieldReference = new FieldReference(someClass, fieldName);
        Optional<Field> field;
        if ((field = fieldCache.get(fieldReference)) == null) {
            fieldCache.put(fieldReference, field = Classes.from(someClass)
                    .traversingSuperclasses()
                    .fields()
                    .filter(f -> f.getName()
                            .equals(fieldName))
                    .findFirst());
        }
        return field;
    }

    private Object getFieldValue(Object candidate, Field field) {
        field.setAccessible(true);
        try {
            return field.get(candidate);
        } catch (Exception e) {
            throw BusinessException.wrap(e, BusinessErrorCode.ERROR_ACCESSING_FIELD)
                    .put("className", candidate.getClass()
                            .getName())
                    .put("fieldName", field.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isSatisfiedByValue(Object result) {
        return valueSpecification.isSatisfiedBy((V) result);
    }

    private static class FieldReference {

        final Class<?> someClass;
        final String fieldName;

        public FieldReference(Class<?> someClass, String fieldName) {
            this.someClass = someClass;
            this.fieldName = fieldName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            FieldReference that = (FieldReference) o;

            return someClass.equals(that.someClass) && fieldName.equals(that.fieldName);
        }

        @Override
        public int hashCode() {
            int result = someClass.hashCode();
            result = 31 * result + fieldName.hashCode();
            return result;
        }
    }
}
