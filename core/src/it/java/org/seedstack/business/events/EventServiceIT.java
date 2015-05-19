/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.events;

import org.seedstack.business.api.EventService;
import org.seedstack.business.events.fixtures.MyEvent;
import org.seedstack.business.events.fixtures.MyEvent2;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

import static org.junit.Assert.fail;


/**
 * Tests fire event whether the handler throws an exception or not.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
@RunWith(SeedITRunner.class)
public class EventServiceIT {

    public static int countMyEvent = 0;
    public static int countGenericEvent = 0;

    @Inject
    private EventService eventService;

    @Test
    public void fireEvent() {
        countMyEvent = 0;
        eventService.fire(new MyEvent());
        Assertions.assertThat(countMyEvent).isEqualTo(1);
    }

    @Test
    public void fireGenericEvent() {
        countGenericEvent = 0;
        eventService.fire(new MyEvent());
        Assertions.assertThat(countGenericEvent).isEqualTo(1);
    }

    @Test
    public void fireEventThenHandlerFailed() throws InterruptedException {
        try {
            eventService.fire(new MyEvent2());
            fail();
        } catch (Exception e) {
            Assertions.assertThat(e).isNotNull();
        }
    }

}
