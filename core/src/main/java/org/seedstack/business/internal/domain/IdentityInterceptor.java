/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.internal.domain;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Interceptor used for identity management when creating a new entity using a factory.
 */
class IdentityInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityInterceptor.class);
    @Inject
    private IdentityService identityService;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object object = invocation.proceed();
        if (object instanceof Entity) {
            LOGGER.trace("Invoking identity service to identify an entity of class {}", object.getClass());
            return identityService.identify(((Entity<?>) object));
        } else {
            return object;
        }
    }
}
