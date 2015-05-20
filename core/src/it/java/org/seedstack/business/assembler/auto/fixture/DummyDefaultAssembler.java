/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.api.DefaultImpl;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.AbstractBaseAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Method;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DefaultImpl
@Named("Dummy")
public class DummyDefaultAssembler<A extends AggregateRoot<?>,D> extends AbstractBaseAssembler<A, D> {


    @SuppressWarnings("unchecked")
    @Inject
    public DummyDefaultAssembler(@Assisted Object[] genericClasses) {
        dtoClass = (Class) genericClasses.clone()[1];
    }

    @Override
    public D assembleDtoFromAggregate(A sourceAggregate) {
        D dto;
        try {
            dto = getDtoClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return hodorify(getDtoClass(), dto);
    }

    @Override
    public void updateDtoFromAggregate(D sourceDto, A sourceAggregate) {
        hodorify(getDtoClass(), sourceDto);
    }

    @Override
    public void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        hodorify(targetAggregate.getClass(), targetAggregate);
    }

    private <T> T hodorify(Class<?> clazz, T instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == String.class) {
                try {
                    method.invoke(instance, "hodor");
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return instance;
    }
}
