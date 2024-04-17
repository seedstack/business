/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event.interceptors;

import org.seedstack.business.domain.DomainEventHandler;

/***
 * Test fixture handler that has no priority specified, so it uses the default value 0
 *
 */
public class DefaultPriorityEventHandler implements DomainEventHandler<PriorizedFixtureEvent> {

    public static int lastEvent;

    @Override
    public void onEvent(PriorizedFixtureEvent event) {
        lastEvent = event.increment();
    }

    @Override
    public Class<PriorizedFixtureEvent> getEventClass() {
        return PriorizedFixtureEvent.class;
    }

}
