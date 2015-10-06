/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.fixtures.event.MyEvent;
import org.seedstack.business.fixtures.event.MyHandler;
import org.seedstack.business.fixtures.event.MyService;
import org.seedstack.business.test.event.EventFixture;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@RunWith(SeedITRunner.class)
public class ServiceFixturesIT {

    public static final String SOME_PARAM = "someParam";

    @Inject
    private EventFixture fixtures;

    @Test
    public void expect_handler_was_called_with_event() throws Exception {
        fixtures.given(MyService.class)
                .whenCalled("callBusinessStuff", SOME_PARAM)
                .eventWasHandledBy(new MyEvent(SOME_PARAM), MyHandler.class);
    }
}
