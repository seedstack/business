/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event;

import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;

public class MyHandler2Domain implements DomainEventHandler<DomainEvent> {

    @Override
    public void onEvent(DomainEvent domainEvent) {
        // do nothing
    }

    @Override
    public Class<DomainEvent> getEventClass() {
        return DomainEvent.class;
    }
}
