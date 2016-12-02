/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import com.google.inject.Injector;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.Event;
import org.seedstack.business.fixtures.event.SomeEvent;
import org.seedstack.business.fixtures.event.MyHandler;
import org.seedstack.business.fixtures.event.MyHandler2;
import org.seedstack.business.fixtures.event.SomeService;
import org.seedstack.business.test.events.EventFixture;
import org.seedstack.business.EventHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.seed.SeedException;

import java.util.HashMap;

import static org.mockito.Mockito.mock;


public class ServiceFixtureTest {

    public static final String SOME_PARAM = "someParam";

    private ContextLink contextLink = mock(ContextLink.class);

    private EventFixture underTest;

    private SomeEvent event = new SomeEvent(SOME_PARAM);


    @Before
    public void setUp() {
        underTest = new EventFixtureInternal();
        Injector injector = mock(Injector.class);
        Mockito.when(injector.getInstance(SomeService.class)).thenReturn(mock(SomeService.class));
        Whitebox.setInternalState(underTest, "contextLink", contextLink);
        Whitebox.setInternalState(underTest, "injector", injector);

        HashMap<Class<? extends EventHandler>, Event> value = new HashMap<>();
        value.put(MyHandler.class, event);
        value.put(MyHandler2.class, event);
        Mockito.when(contextLink.peek()).thenReturn(value);
    }

    @Test
    public void expect_handler_was_called_with_event() throws Exception {
        underTest.given(SomeService.class)
                .whenCalled("callBusinessStuff", SOME_PARAM)
                .eventWasHandledBy(event, MyHandler.class);
    }

    @Test(expected = SeedException.class)
    public void failed_method_was_not_equal() throws Exception {
        underTest.given(SomeService.class)
                .whenCalled("unknownMethod", SOME_PARAM)
                .eventWasHandledBy(new SomeEvent("foobar"), MyHandler.class);
    }

    @Test(expected = SeedException.class)
    public void failed_method_was_not_found() throws Exception {
        underTest.given(SomeService.class)
                .whenCalled("unknownMethod", SOME_PARAM)
                .eventWasHandledBy(event, MyHandler.class);
    }

    @Test(expected = SeedException.class)
    public void failed_event_was_null() throws Exception {
        underTest.given(SomeService.class)
                .whenCalled("callBusinessStuff", SOME_PARAM)
                .eventWasHandledBy(null, MyHandler.class);
    }

    @Test(expected = SeedException.class)
    public void failed_event_was_not_fired() throws Exception {
        HashMap<Class<? extends EventHandler>, Event> value = new HashMap<>();
        Mockito.when(contextLink.peek()).thenReturn(value);
        underTest.given(SomeService.class)
                .whenCalled("doNothing")
                .eventWasHandledBy(event, MyHandler.class);
    }
}
