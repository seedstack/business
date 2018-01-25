/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.event.cyclic;

import javax.inject.Inject;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;

public class Handler1Domain implements DomainEventHandler<Event1> {

    @Inject
    private DomainEventPublisher domainEventPublisher;

    @Override
    public void onEvent(Event1 event) {
        domainEventPublisher.publish(new Event2());
    }

    @Override
    public Class<Event1> getEventClass() {
        return Event1.class;
    }
}
