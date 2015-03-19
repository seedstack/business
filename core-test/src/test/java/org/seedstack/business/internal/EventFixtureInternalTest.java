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

import com.google.inject.Injector;
import org.seedstack.business.api.Event;
import org.seedstack.business.api.EventHandler;
import org.seedstack.business.api.EventService;
import org.seedstack.business.api.fixtures.MyEvent;
import org.seedstack.business.api.fixtures.MyHandler;
import org.seedstack.business.api.fixtures.MyHandler2;
import org.seedstack.business.api.fixtures.MyHandler3;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;

import static org.mockito.Mockito.mock;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 18/08/2014
 */
public class EventFixtureInternalTest {

    private ContextLink contextLink = mock(ContextLink.class);
    private EventFixtureInternal underTest;
    private Event event;

    @Before
    public void setUp() {
        underTest = new EventFixtureInternal();
        Whitebox.setInternalState(underTest, "injector", mock(Injector.class));
        Whitebox.setInternalState(underTest, "eventService", mock(EventService.class));
        Whitebox.setInternalState(underTest, "contextLink", contextLink);

        event = new MyEvent("");
        HashMap<Class<? extends EventHandler>, Event> value = new HashMap<Class<? extends EventHandler>, Event>();
        value.put(MyHandler.class, event);
        value.put(MyHandler2.class, event);
        Mockito.when(contextLink.peek()).thenReturn(value);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_handler_by() {
         underTest.given(event).whenFired().wasHandledBy(MyHandler.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_handler_by_failed() {
        underTest.given(event).whenFired().wasHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_exactly_handler_by() {
        underTest.given(event).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_exactly_handler_by_failed() {
        underTest.given(event).whenFired().wasHandledExactlyBy(MyHandler.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_exactly_handler_by_failed_again() {
        underTest.given(event).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class, MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_not_handler_by() {
        underTest.given(event).whenFired().wasNotHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_not_handler_by_failed() {
        underTest.given(event).whenFired().wasNotHandledBy(MyHandler.class);
    }
}
