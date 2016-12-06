/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;
import org.seedstack.business.fixtures.event.SomeEvent;
import org.seedstack.business.fixtures.event.MyHandler;
import org.seedstack.business.fixtures.event.MyHandler2;
import org.seedstack.business.fixtures.event.MyHandler3;

import java.util.HashMap;

import static org.mockito.Mockito.mock;


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

        event = new SomeEvent("");
        HashMap<Class<? extends EventHandler>, Event> value = new HashMap<>();
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
