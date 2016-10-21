/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Injector;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;
import org.seedstack.business.events.fixtures.MyEvent;
import org.seedstack.business.events.fixtures.MyEventHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the event service.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceInternalTest {

    private EventService underTest;
    @Mock
    private Injector injector;
    @Mock
    private MyEventHandler myEventHandler;

    @Test
    public void fire_event() {
        ArrayListMultimap<Class<? extends Event>, Class<? extends EventHandler>> multiMap = ArrayListMultimap.create();
        multiMap.put(MyEvent.class, MyEventHandler.class);

        // provide an handler of MyEvent
        Mockito.when(injector.getInstance(MyEventHandler.class)).thenReturn(myEventHandler);

        underTest = new EventServiceInternal(injector, multiMap);
        underTest.fire(new MyEvent());
    }

    @Test
    public void fire_event_not_received() {
        ArrayListMultimap<Class<? extends Event>, Class<? extends EventHandler>> multiMap = ArrayListMultimap.create();
        // no handler provided
        underTest = new EventServiceInternal(injector, multiMap);
        underTest.fire(new MyEvent());
    }
}
