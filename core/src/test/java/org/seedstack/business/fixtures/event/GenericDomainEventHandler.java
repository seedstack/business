/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.event;

import org.seedstack.business.DomainEventPublisherIT;
import org.seedstack.business.domain.BaseDomainEventHandler;

public class GenericDomainEventHandler extends BaseDomainEventHandler<AbstractDomainEvent> {

    @Override
    public void onEvent(AbstractDomainEvent event) {
        DomainEventPublisherIT.countGenericEvent++;
    }
}
