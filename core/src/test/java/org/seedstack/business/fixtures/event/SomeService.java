/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.event;

import javax.inject.Inject;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.seed.Bind;

@Bind
public class SomeService {
    @Inject
    private DomainEventPublisher domainEventPublisher;

    public void callBusinessStuff(String someParam) {
        domainEventPublisher.publish(new SomeDomainEvent(someParam));
    }

    public void doNothing() {
        // do nothing
    }
}
