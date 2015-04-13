/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.resolver;

import org.fest.reflect.core.Reflection;
import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;
import org.seedstack.business.api.interfaces.assembler.resolver.DtoInfoResolver;
import org.seedstack.business.api.interfaces.assembler.resolver.ParameterHolder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AnnotationResolver implements DtoInfoResolver {

    @Override
    public ParameterHolder resolveId(Object dto) {
        return resolveParameterFromDto(dto, MatchingEntityId.class);
    }

    @Override
    public ParameterHolder resolveAggregate(Object dto) {
        return resolveParameterFromDto(dto, MatchingFactoryParameter.class);
    }

    private ParameterHolder resolveParameterFromDto(Object dto, Class<? extends Annotation> annotationClass) {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        // filter methods annotated with the annotationClass
        Method[] methods = dto.getClass().getMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                int fieldIndex = fieldIndex(annotation);
                int typeIndex = typeIndex(annotation);

                if (fieldIndex == -1) {
                    // TODO : this case might be handle later
                    if (parameterHolder.first() != null) {
                        throw new IllegalArgumentException("Missing index on @MatchingEntityId annotation for " + method.getName());
                    }
                    parameterHolder.put(method.getName(), 0, getAttributeFromMethod(dto, method));
                } else {
                    if (typeIndex == -1) {
                        parameterHolder.put(method.getName(), fieldIndex, getAttributeFromMethod(dto, method));
                    } else {
                        parameterHolder.put(method.getName(), fieldIndex, typeIndex, getAttributeFromMethod(dto, method));
                    }
                }
            }
        }

        // No @MatchingEntityId found
        if (parameterHolder.isEmpty()) {
            throw new IllegalArgumentException("Missing @MatchingEntityId annotation on " + dto.getClass().getSimpleName() + "'s id.");
        }

        return parameterHolder;
    }

    // ------
    // These two methods are needed because of the lack of interface for annotations.
    // This is the contract that annotations used by the resolveParameterFromDto() method should follow.

    private int fieldIndex(Annotation anno) {
        return Reflection.method("index").withReturnType(int.class).withParameterTypes().in(anno).invoke();
    }

    private int typeIndex(Annotation anno) {
        return Reflection.method("typeIndex").withReturnType(int.class).withParameterTypes().in(anno).invoke();
    }

    // ------

    private Object getAttributeFromMethod(Object dto, Method method) {
        try {
            return method.invoke(dto);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to call " + method.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Failed to call " + method.getName(), e);
        }
    }

}
