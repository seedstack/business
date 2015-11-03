/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.events;

import org.seedstack.business.EventService;
import org.seedstack.business.events.fixtures.cyclic.Event1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@RunWith(SeedITRunner.class)
public class CyclicIT {

    @Inject
    private EventService eventService;

    @Test(expected = SeedException.class)
    public void fire_cyclic_events() {
        eventService.fire(new Event1());
    }
}
