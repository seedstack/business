/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.utils;

import com.google.common.primitives.Primitives;
import org.seedstack.business.BusinessException;
import org.seedstack.business.internal.BusinessErrorCode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


public final class MethodMatcher {

    MethodMatcher() {
    }

    public static Method findMatchingMethod(Class<?> classToInspect, Class<?> returnType, Object... params) {
        Method[] methods = classToInspect.getMethods();
        Method checkedMethod = null;
        for (Method method : methods) {
            Type[] parameterTypes = method.getParameterTypes();
            // if the return type is not specified (i.e null) we only check the parameters
            boolean matchReturnType = returnType == null || returnType.equals(method.getReturnType());

            if (checkParams(parameterTypes, params) && matchReturnType) {
                if (checkedMethod == null) {
                    checkedMethod = method;
                } else {
                    throw BusinessException.createNew(BusinessErrorCode.AMBIGUOUS_METHOD_FOUND).put("method1", method).put("method2", checkedMethod)
                            .put("object", classToInspect.getSimpleName()).put("parameters", params);
                }
            }
        }
        return checkedMethod;
    }

    private static boolean checkParams(Type[] parameterTypes, Object[] params) {
        return params.length == 0 || (parameterTypes.length == params.length && checkParameterTypes(parameterTypes, params));
    }

    public static Method findMatchingMethod(Class<?> classToInspect, Object... params) {
        return findMatchingMethod(classToInspect, null, params);
    }

    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> findMatchingConstructor(Class<T> classToInspect, Object... params) {
        Constructor<T> checkedConstructors = null;
        for (Constructor<?> constructor : classToInspect.getDeclaredConstructors()) {
            Type[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == params.length && checkParameterTypes(parameterTypes, params)) {
                if (checkedConstructors == null) {
                    checkedConstructors = (Constructor<T>) constructor;
                } else {
                    throw BusinessException.createNew(BusinessErrorCode.AMBIGUOUS_CONSTRUCTOR_FOUND).put("constructor1", constructor).put("constructor2", checkedConstructors)
                            .put("object", classToInspect.getSimpleName()).put("parameters", params);
                }
            }
        }
        return checkedConstructors;
    }

    private static boolean checkParameterTypes(Type[] parameterTypes, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            Object object = args[i];
            Type parameterType = parameterTypes[i];
            if (object != null) {
                Class<?> objectType = object.getClass();
                Class<?> unWrapPrimitive = null;
                if (Primitives.isWrapperType(objectType)) {
                    unWrapPrimitive = Primitives.unwrap(objectType);
                }
                if (!(((Class<?>) parameterType).isAssignableFrom(objectType) || (unWrapPrimitive != null && ((Class<?>) parameterType)
                        .isAssignableFrom(unWrapPrimitive)))) {
                    return false;
                }
            }
        }
        return true;
    }
}
