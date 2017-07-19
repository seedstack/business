/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.spi.GenericImplementation;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Method;

@GenericImplementation
@Named("Dummy")
public class DummyDefaultAssembler<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D> {
    @SuppressWarnings("unchecked")
    @Inject
    public DummyDefaultAssembler(@Assisted Object[] genericClasses) {
        super((Class) genericClasses[1]);
    }

    @Override
    public D createDtoFromAggregate(A sourceAggregate) {
        D dto;
        try {
            dto = getDtoClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return hodorify(getDtoClass(), dto);
    }

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D sourceDto) {
        hodorify(getDtoClass(), sourceDto);
    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
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
