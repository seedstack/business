/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Binder;
import org.fest.reflect.core.Reflection;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.fixtures.event.MyDomainEvent2;
import org.seedstack.business.fixtures.event.MyDomainEventHandlerFailed;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


public class DomainEventModuleTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test_configure_method() {
        ArrayListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> multimap = ArrayListMultimap.create();
        multimap.put(MyDomainEvent2.class, MyDomainEventHandlerFailed.class);
        List<Class<? extends DomainEventHandler>> eventHandlerClasses = new ArrayList<>();
        eventHandlerClasses.add(MyDomainEventHandlerFailed.class);
        EventModule underTest = new EventModule(multimap, eventHandlerClasses);
        Binder b = mock(Binder.class, Mockito.RETURNS_MOCKS);
        Reflection.field("binder").ofType(Binder.class).in(underTest).set(b);
        underTest.configure();
    }
}
