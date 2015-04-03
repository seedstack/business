/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.fixtures;

import org.seedstack.business.api.EventService;
import org.seedstack.seed.it.api.ITBind;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 06/06/2014
 */
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
