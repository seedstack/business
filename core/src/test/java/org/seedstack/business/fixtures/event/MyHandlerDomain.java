/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event;

import org.seedstack.business.domain.DomainEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHandlerDomain implements DomainEventHandler<SomeDomainEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyHandlerDomain.class);

    @Override
    public void onEvent(SomeDomainEvent event) {
        LOGGER.info("MyHandler gets a MyEvent event.");
    }

    @Override
    public Class<SomeDomainEvent> getEventClass() {
        return SomeDomainEvent.class;
    }
}
