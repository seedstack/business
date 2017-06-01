/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.SeedException;
import org.seedstack.shed.reflect.Classes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Base class for value-bound specifications which are checking that the value of an aggregate attribute is satisfying
 * an expected value. This supports nested attributes with a dot notation. For instance
 *
 * @param <A>
 * @param <V>
 */
public abstract class AbstractValueSpecification<A extends AggregateRoot<?>, V> implements Specification<A> {
    private static final int AGGREGATE_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String PROPERTY_PATTERN = "\\.";
    private static final ConcurrentMap<FieldReference, Optional<Field>> fieldCache = new ConcurrentHashMap<>();
    private final Class<A> aggregateRootClass;
    private final Class<V> valueClass;
    private final String[] properties;
    protected final String path;
    protected final V expectedValue;

    @SuppressWarnings("unchecked")
    public AbstractValueSpecification(String path, V expectedValue) {
        Class<?>[] generics = BusinessUtils.resolveGenerics(AbstractValueSpecification.class, getClass());
        this.aggregateRootClass = (Class<A>) generics[AGGREGATE_INDEX];
        this.valueClass = (Class<V>) generics[VALUE_INDEX];
        this.properties = path.split(PROPERTY_PATTERN);
        this.path = path;
        this.expectedValue = expectedValue;
    }

    public String getPath() {
        return path;
    }

    public V getExpectedValue() {
        return expectedValue;
    }

    public Class<A> getAggregateRootClass() {
        return aggregateRootClass;
    }

    public Class<V> getValueClass() {
        return valueClass;
    }

    protected abstract boolean isSatisfiedByValue(V candidateValue);

    @Override
    public boolean isSatisfiedBy(A candidate) {
        return isSatisfiedBy(candidate, 0);
    }

    private boolean isSatisfiedBy(Object candidate, int propertyIndex) {
        if (candidate != null) {
            Optional<Field> fieldOptional = findField(candidate.getClass(), properties[propertyIndex]);
            if (fieldOptional.isPresent()) {
                Object result = getFieldValue(candidate, fieldOptional.get());
                if (propertyIndex < properties.length - 1) {
                    // TODO: support arrays
                    if (result instanceof Collection) {
                        return ((Collection<?>) result).stream().anyMatch(item -> isSatisfiedBy(item, propertyIndex + 1));
                    } else if (result.getClass().isArray()) {
                        return Arrays.stream((Object[]) result).anyMatch(item -> isSatisfiedBy(item, propertyIndex + 1));
                    } else {
                        return isSatisfiedBy(result, propertyIndex + 1);
                    }
                } else {
                    return isSatisfiedByObject(result);
                }
            }
        }
        return false;
    }

    private Optional<Field> findField(Class<?> someClass, String fieldName) {
        FieldReference fieldReference = new FieldReference(someClass, fieldName);
        Optional<Field> field;
        if ((field = fieldCache.get(fieldReference)) == null) {
            fieldCache.putIfAbsent(fieldReference, field = Classes.from(someClass)
                    .traversingSuperclasses()
                    .fields()
                    .filter(f -> f.getName().equals(fieldName))
                    .findFirst());
        }
        return field;
    }

    private Object getFieldValue(Object candidate, Field field) {
        field.setAccessible(true);
        try {
            return field.get(candidate);
        } catch (Exception e) {
            throw SeedException.wrap(e, BusinessErrorCode.ERROR_ACCESSING_FIELD)
                    .put("className", candidate.getClass().getName())
                    .put("fieldName", field.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isSatisfiedByObject(Object result) {
        return valueClass.isAssignableFrom(result.getClass()) && isSatisfiedByValue((V) result);
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

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
