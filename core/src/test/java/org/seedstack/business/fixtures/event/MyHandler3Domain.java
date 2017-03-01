/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.event;

import org.seedstack.business.domain.DomainEventHandler;

public class MyHandler3Domain implements DomainEventHandler<DummyDomainEvent> {
    @Override
    public void handle(DummyDomainEvent event) {
        // do nothing
    }

    @Override
    public Class<DummyDomainEvent> getEventClass() {
        return DummyDomainEvent.class;
    }
}
