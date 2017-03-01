/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.fixtures.event.MyDomainEvent;
import org.seedstack.business.fixtures.event.MyDomainEventHandler;
import org.seedstack.business.fixtures.event.SomeDomainEvent;

/**
 * Tests the event service.
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainDomainEventPublisherInternalTest {

    private DomainEventPublisher underTest;
    @Mock
    private Injector injector;
    @Mock
    private MyDomainEventHandler myEventHandler;

    @Test
    public void fire_event() {
        ArrayListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> multiMap = ArrayListMultimap.create();
        multiMap.put(SomeDomainEvent.class, MyDomainEventHandler.class);

        // provide an handler of MyEvent
        Mockito.when(injector.getInstance(MyDomainEventHandler.class)).thenReturn(myEventHandler);

        underTest = new DomainEventPublisherInternal(injector, multiMap);
        underTest.publish(new MyDomainEvent());
    }

    @Test
    public void fire_event_not_received() {
        ArrayListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> multiMap = ArrayListMultimap.create();
        // no handler provided
        underTest = new DomainEventPublisherInternal(injector, multiMap);
        underTest.publish(new MyDomainEvent());
    }
}
