/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.fixtures.event.MyDomainEvent;
import org.seedstack.business.fixtures.event.MyDomainEventHandler;
import org.seedstack.business.fixtures.event.SomeDomainEvent;

@RunWith(MockitoJUnitRunner.class)
public class DomainDomainEventPublisherImplTest {
    private DomainEventPublisher underTest;
    @Mock
    private Injector injector;

    @Test
    public void fire_event() {
        ArrayListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> multiMap =
                ArrayListMultimap.create();
        multiMap.put(SomeDomainEvent.class, MyDomainEventHandler.class);

        underTest = new DomainEventPublisherImpl(injector, multiMap);
        underTest.publish(new MyDomainEvent());
    }

    @Test
    public void fire_event_not_received() {
        ArrayListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> multiMap =
                ArrayListMultimap.create();
        // no handler provided
        underTest = new DomainEventPublisherImpl(injector, multiMap);
        underTest.publish(new MyDomainEvent());
    }
}
