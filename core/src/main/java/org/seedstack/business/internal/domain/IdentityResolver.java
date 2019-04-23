/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import static org.seedstack.shed.reflect.AnnotationPredicates.elementAnnotatedWith;

import java.lang.reflect.Field;
import java.util.Optional;
import org.seedstack.business.domain.Identity;
import org.seedstack.shed.reflect.AnnotationResolver;
import org.seedstack.shed.reflect.Classes;

public class IdentityResolver implements AnnotationResolver<Class<?>, Identity> {
    public static final IdentityResolver INSTANCE = new IdentityResolver();

    private IdentityResolver() {
        // no external instantiation allowed
    }

    @Override
    public Optional<Identity> apply(Class<?> someClass) {
        return resolveField(someClass).map(f -> f.getAnnotation(Identity.class));
    }

    @Override
    public boolean test(Class<?> someClass) {
        return resolveField(someClass).isPresent();
    }

    /**
     * Resolves the first field holding annotated or meta-annotated with {@link Identity}.
     *
     * @param someClass the class to search for identity field.
     * @return the {@link Field} wrapped in an {@link Optional}.
     */
    public Optional<Field> resolveField(Class<?> someClass) {
        return Classes.from(someClass)
                .traversingSuperclasses()
                .fields()
                .filter(elementAnnotatedWith(Identity.class, true))
                .findFirst();
    }
}
