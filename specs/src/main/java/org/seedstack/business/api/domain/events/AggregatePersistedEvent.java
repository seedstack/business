/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.events;

import org.seedstack.business.api.domain.AggregateRoot;

import java.lang.reflect.Method;

/**
 * Event fired when a method of a subtype of Repository annotated by {@code @Persist} is called.
 * This event will contains the method called of the repository and its arguments.
 * <p>
 * To enable this interception add the following line in a props file:
 * </p>
 * <pre>
 * [org.seedstack.business.event]
 * domain.watch=true
 * </pre>
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
public class AggregatePersistedEvent extends BaseAggregateEvent {

    private static final long serialVersionUID = 4710766369957116577L;

    /**
     * Constructor.
     *
     * @param methodCalled  intercepted method
     * @param args          arguments of the intercepted method
     * @param aggregateRoot aggregate root class concern by the event
     */
    public AggregatePersistedEvent(Method methodCalled, Object[] args, Class<? extends AggregateRoot<?>> aggregateRoot) {
        super(methodCalled, args, aggregateRoot);
    }
}
