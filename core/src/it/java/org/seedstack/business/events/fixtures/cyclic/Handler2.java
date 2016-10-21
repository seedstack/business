/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.events.fixtures.cyclic;

import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;

import javax.inject.Inject;


public class Handler2 implements EventHandler<Event2> {

    @Inject
    private EventService eventService;

    @Override
    public void handle(Event2 event) {
        eventService.fire(new Event1());
    }
}
