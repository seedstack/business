/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.events.fixtures.GenericEventHandler;
import org.seedstack.business.fixtures.event.MyEvent;
import org.seedstack.business.fixtures.event.MyHandler;
import org.seedstack.business.fixtures.event.MyHandler2;
import org.seedstack.business.fixtures.event.MyHandler3;
import org.seedstack.business.test.event.EventFixture;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@RunWith(SeedITRunner.class)
public class EventFixtureIT {

    @Inject
    private EventFixture fixture;

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_handler_by() {
         fixture.given(new MyEvent("")).whenFired().wasHandledBy(MyHandler.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_handler_by_failed() {
        fixture.given(new MyEvent("")).whenFired().wasHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_exactly_handler_by() {
        fixture.given(new MyEvent("")).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_exactly_handler_by_failed() {
        fixture.given(new MyEvent("")).whenFired().wasHandledExactlyBy(MyHandler.class, MyHandler2.class, MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void expect_was_not_handler_by() {
        fixture.given(new MyEvent("")).whenFired().wasNotHandledBy(MyHandler3.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void expect_was_not_handler_by_failed() {
        fixture.given(new MyEvent("")).whenFired().wasNotHandledBy(MyHandler.class);
    }
}
