/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event.interceptors;

import java.util.concurrent.atomic.AtomicInteger;
import org.seedstack.business.fixtures.event.AbstractDomainEvent;

public class PriorizedFixtureEvent extends AbstractDomainEvent {

    protected static final AtomicInteger timesHandled = new AtomicInteger();

    public static void reset() {
        timesHandled.set(0);
    }

    public static Integer peekInvocationCount() {
        return timesHandled.get();
    }

    public Integer increment() {
        return timesHandled.incrementAndGet();
    }
}
