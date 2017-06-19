/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.identity;

import org.seedstack.business.domain.Identity;
import org.seedstack.shed.reflect.AnnotationResolver;
import org.seedstack.shed.reflect.Classes;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.seedstack.shed.reflect.AnnotationPredicates.elementAnnotatedWith;

public class IdentityAnnotationResolver implements AnnotationResolver<Class<?>, Identity> {
    public static final IdentityAnnotationResolver INSTANCE = new IdentityAnnotationResolver();

    private IdentityAnnotationResolver() {
        // no external instantiation allowed
    }

    @Override
    public Optional<Identity> apply(Class<?> aClass) {
        return resolveField(aClass).map(f -> f.getAnnotation(Identity.class));
    }

    @Override
    public boolean test(Class<?> aClass) {
        return resolveField(aClass).isPresent();
    }

    Optional<Field> resolveField(Class<?> aClass) {
        return Classes.from(aClass)
                .traversingSuperclasses()
                .fields()
                .filter(elementAnnotatedWith(Identity.class, true))
                .findFirst();
    }
}
