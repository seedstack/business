/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event;

import org.seedstack.business.EventService;
import org.seedstack.seed.it.ITBind;

import javax.inject.Inject;


@ITBind
public class MyService {

    @Inject
    private EventService eventService;

    public void callBusinessStuff(String someParam) {
        eventService.fire(new MyEvent(someParam));
    }

    public void doNothing() {
        // do nothing
    }
}
