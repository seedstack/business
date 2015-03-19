/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.events.fixtures.cyclic;

import org.seedstack.business.api.EventHandler;
import org.seedstack.business.api.EventService;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 11/06/2014
 */
public class Handler2 implements EventHandler<Event2> {

    @Inject
    private EventService eventService;

    @Override
    public void handle(Event2 event) {
        eventService.fire(new Event1());
    }
}
