/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.utils;

import java.lang.reflect.Field;
import java.util.Optional;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.shed.cache.Cache;
import org.seedstack.shed.cache.CacheParameters;
import org.seedstack.shed.reflect.Classes;

public final class FieldUtils {
    private static final Cache<FieldReference, Optional<Field>> fieldCache = Cache.create(
            new CacheParameters<FieldReference, Optional<Field>>()
                    .setInitialSize(256)
                    .setMaxSize(1024)
                    .setLoadingFunction(fieldReference -> Classes.from(fieldReference.someClass)
                            .traversingSuperclasses()
                            .fields()
                            .filter(f -> f.getName().equals(fieldReference.fieldName))
                            .findFirst())
    );

    private FieldUtils() {
        // no instantiation allowed
    }

    /**
     * Return the value contained in the specified field of the candidate object.
     *
     * @param candidate the object to retrieve the value of.
     * @param field     the field.
     * @return the value contained in the field of the candidate.
     */
    public static Object getFieldValue(Object candidate, Field field) {
        field.setAccessible(true);
        try {
            return field.get(candidate);
        } catch (Exception e) {
            throw BusinessException.wrap(e, BusinessErrorCode.ERROR_ACCESSING_FIELD)
                    .put("className", candidate.getClass())
                    .put("fieldName", field.getName());
        }
    }

    /**
     * Returns the specified field found on the specified class and its ancestors.
     *
     * @param someClass the class to search the field on.
     * @param fieldName the field name.
     * @return the corresponding field object wrapped in an optional.
     */
    public static Optional<Field> resolveField(Class<?> someClass, String fieldName) {
        return fieldCache.get(new FieldReference(someClass, fieldName));
    }

    private static class FieldReference {
        final Class<?> someClass;
        final String fieldName;

        private FieldReference(Class<?> someClass, String fieldName) {
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
