/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver.impl;

import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;
import org.seedstack.business.internal.assembler.dsl.resolver.DtoInfoResolver;
import org.seedstack.business.internal.assembler.dsl.resolver.ParameterHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implementation of the {@link org.seedstack.business.internal.assembler.dsl.resolver.DtoInfoResolver}
 * based on the MatchingFactoryParameter and MatchingFactoryParameter annotation.
 * <p>
 * See Their respective documentation to understand {@code AnnotationResolver} implementation.
 * </p>
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 * @see org.seedstack.business.api.interfaces.assembler.MatchingEntityId
 * @see org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter
 */
public class AnnotationResolver implements DtoInfoResolver {

    public static final String MATCHING_FACT_PARAM = MatchingFactoryParameter.class.getSimpleName();
    public static final String MATCHING_ENTITY_ID = MatchingEntityId.class.getSimpleName();

    @Override
    public ParameterHolder resolveId(Object dto) {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        Method[] methods = dto.getClass().getMethods();
        for (Method method : methods) {
            MatchingEntityId annotation = method.getAnnotation(MatchingEntityId.class);
            if (annotation != null) {
                if (annotation.index() == -1) {
                    // Only if the id is not a value object ! If the constructor has one parameter set the index to 0.
                    // TODO <pith> add more tests and documentation on this
                    if (parameterHolder.uniqueElement() != null) {
                        String message = method.toString() + " - There is already a method annotated with @" + MATCHING_ENTITY_ID
                                + " don't forget to specify the index to indicate the matching parameter: @" + MATCHING_ENTITY_ID + "(index = 0)";
                        throw new IllegalArgumentException(message);
                    }
                    parameterHolder.put(method.toString(), annotation.typeIndex(), -1, getAttributeFromMethod(dto, method)); // The index set to -1 as it is another use case

                } else {
                    parameterHolder.put(method.toString(), annotation.typeIndex(), annotation.index(), getAttributeFromMethod(dto, method));
                }
            }
        }

        // No annotation found
        if (parameterHolder.isEmpty()) {
            String message = String.format("Missing %s annotation on %s's id.", MATCHING_ENTITY_ID, dto.getClass().getSimpleName());
            throw new IllegalArgumentException(message);
        }

        return parameterHolder;
    }

    @Override
    public ParameterHolder resolveAggregate(Object dto) {
        ParameterHolder parameterHolder = new ParameterHolderInternal();

        Method[] methods = dto.getClass().getMethods();
        for (Method method : methods) {
            MatchingFactoryParameter annotation = method.getAnnotation(MatchingFactoryParameter.class);
            if (annotation != null) {
                if (annotation.index() == -1) {
                    // If there is only one parameter in the factory method you can avoid to set the index
                    if (parameterHolder.uniqueElement() != null) {
                        String message = method.getName() + " - There is already a method annotated with @" + MATCHING_FACT_PARAM
                                + " don't forget to specify the index to indicate the matching parameter: @" + MATCHING_FACT_PARAM + "(index = 0)";
                        throw new IllegalArgumentException(message);
                    }
                    parameterHolder.put(method.toString(), 0, getAttributeFromMethod(dto, method)); // The index set to 0

                } else {
                    parameterHolder.put(method.toString(), annotation.typeIndex(), annotation.index(), getAttributeFromMethod(dto, method));
                }
            }
        }

        return parameterHolder;
    }

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
