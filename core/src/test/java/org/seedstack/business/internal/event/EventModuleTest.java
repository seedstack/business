/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Binder;
import org.fest.reflect.core.Reflection;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.fixtures.event.MyEvent2;
import org.seedstack.business.fixtures.event.MyEventHandlerFailed;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


public class EventModuleTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test_configure_method() {
        ArrayListMultimap<Class<? extends Event>, Class<? extends EventHandler>> multimap = ArrayListMultimap.create();
        multimap.put(MyEvent2.class, MyEventHandlerFailed.class);
        List<Class<? extends EventHandler>> eventHandlerClasses = new ArrayList<>();
        eventHandlerClasses.add(MyEventHandlerFailed.class);
        EventModule underTest = new EventModule(multimap, eventHandlerClasses, false);
        Binder b = mock(Binder.class, Mockito.RETURNS_MOCKS);
        Reflection.field("binder").ofType(Binder.class).in(underTest).set(b);
        underTest.configure();
    }
}
