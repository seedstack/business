/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event.cyclic;

import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;

import javax.inject.Inject;

public class Handler1Domain implements DomainEventHandler<Event1> {

    @Inject
    private DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(Event1 event) {
        domainEventPublisher.publish(new Event2());
    }

    @Override
    public Class<Event1> getEventClass() {
        return Event1.class;
    }
}
