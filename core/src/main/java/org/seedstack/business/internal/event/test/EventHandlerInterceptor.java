/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Inject;

/**
 * This class intercepts the handle method of each EventHandlers and store the calls in a context.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
class EventHandlerInterceptor implements MethodInterceptor {

    @Inject
    private ContextLink contextLink;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        contextLink.put((Class<? extends EventHandler>) invocation.getMethod().getDeclaringClass(), (Event) invocation.getArguments()[0]);
        return invocation.proceed();
    }
}
