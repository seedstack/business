/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.datasecurity;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import org.seedstack.business.api.interfaces.annotations.Secured;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import static org.seedstack.seed.core.utils.SeedReflectionUtils.allParametersAnnotationsFromAncestors;
import static org.seedstack.seed.core.utils.SeedReflectionUtils.isPresent;
import static org.seedstack.seed.core.utils.SeedReflectionUtils.methodsFromAncestors;


/**
 * @author epo.jemba@ext.mpsa.com
 */
public class BusinessDataSecurityModule extends AbstractModule {

    @Override
    protected void configure() {
        InterfacesSecurityInterceptor interfacesSecurityInterceptor = new InterfacesSecurityInterceptor();

        requestInjection(interfacesSecurityInterceptor);

        bindInterceptor(Matchers.any(), securedMethods(), interfacesSecurityInterceptor);
    }

    /**
     * We match the methods where DTO is given back. either returned, either
     * modified via parameter. So we are matching assembleDtoFromEntity and
     * updateDtoFromEntity.
     */
    private Matcher<? super Method> securedMethods() {
        return new AbstractMatcher<Method>() {

            @Override
            public boolean matches(Method candidate) {

                boolean toBeSecured = isPresent(methodsFromAncestors(candidate), Secured.class);

                if (!toBeSecured) {
                    Set<Annotation[][]> methodsParametersAnnotationsFromAncestors = allParametersAnnotationsFromAncestors(candidate);

                    outer:
                    for (Annotation[][] paramAnnos : methodsParametersAnnotationsFromAncestors) {
                        if (paramAnnos != null) {
                            for (Annotation[] annos : paramAnnos) {
                                if (annos != null) {
                                    for (Annotation anno : annos) {
                                        if (anno.annotationType().equals(Secured.class)) {
                                            toBeSecured = true;
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                return toBeSecured;
            }

        };
    }

}
