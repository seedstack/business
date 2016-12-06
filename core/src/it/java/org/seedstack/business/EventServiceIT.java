/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import org.seedstack.business.fixtures.event.MyEvent;
import org.seedstack.business.fixtures.event.MyEvent2;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.event.cyclic.Event1;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

import static org.junit.Assert.fail;


/**
 * Tests fire event whether the handler throws an exception or not.
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

    @Test(expected = SeedException.class)
    public void fire_cyclic_events() {
        eventService.fire(new Event1());
    }
}
