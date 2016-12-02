/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.event.MyHandler;
import org.seedstack.business.fixtures.event.MyHandler2;
import org.seedstack.business.fixtures.event.MyHandler3;
import org.seedstack.business.fixtures.event.SomeEvent;
import org.seedstack.business.fixtures.event.SomeService;
import org.seedstack.business.test.events.EventFixture;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;


@RunWith(SeedITRunner.class)
public class EventFixtureIT {
    public static final String SOME_PARAM = "someParam";

    @Inject
    private EventFixture fixture;

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_handler_by() {
        fixture.given(new SomeEvent("")).whenFired().wasHandledBy(MyHandler.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_handler_by_failed() {
        fixture.given(new SomeEvent("")).whenFired().wasHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_exactly_handler_by() {
        fixture.given(new SomeEvent("")).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_exactly_handler_by_failed() {
        fixture.given(new SomeEvent("")).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class, MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_not_handler_by() {
        fixture.given(new SomeEvent("")).whenFired().wasNotHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_not_handler_by_failed() {
        fixture.given(new SomeEvent("")).whenFired().wasNotHandledBy(MyHandler.class);
    }

    @Test
    public void expect_handler_was_called_with_event() throws Exception {
        fixture.given(SomeService.class)
                .whenCalled("callBusinessStuff", SOME_PARAM)
                .eventWasHandledBy(new SomeEvent(SOME_PARAM), MyHandler.class);
    }
}
