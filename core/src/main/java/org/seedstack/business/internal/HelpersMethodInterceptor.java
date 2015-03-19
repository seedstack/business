/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seedstack.seed.core.api.SeedException;

class HelpersMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object invoked;
        try {
            invoked = invocation.proceed();
        } catch (SeedException e) {
        	throw e;
        } catch (Throwable throwable) { //NOSONAR
            if (throwable.getCause() != null) {
                throw throwable.getCause();
            } else {
                throw throwable;
            }
        }

        return invoked;
    }

}
