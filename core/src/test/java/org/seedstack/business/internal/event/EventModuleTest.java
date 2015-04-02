/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Binder;
import org.seedstack.business.api.Event;
import org.seedstack.business.api.EventHandler;
import org.seedstack.business.events.fixtures.MyEvent2;
import org.seedstack.business.events.fixtures.MyEventHandlerFailed;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 19/08/2014
 */
public class EventModuleTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test_configure_method() {
        ArrayListMultimap<Class<? extends Event>, Class<? extends EventHandler>> multimap = ArrayListMultimap.create();
        multimap.put(MyEvent2.class, MyEventHandlerFailed.class);
        List<Class<? extends EventHandler>> eventHandlerClasses = new ArrayList<Class<? extends EventHandler>>();
        eventHandlerClasses.add(MyEventHandlerFailed.class);
        EventModule underTest = new EventModule(multimap, eventHandlerClasses, false);
        Binder b = mock(Binder.class, Mockito.RETURNS_MOCKS);
        Whitebox.setInternalState(underTest, "binder", b);
        underTest.configure();
    }
}
