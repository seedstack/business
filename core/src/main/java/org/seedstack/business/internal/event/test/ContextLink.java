/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import org.seedstack.business.api.Event;
import org.seedstack.business.api.EventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a shared context between the fixtures and the interceptor.
 * It registers the handlers called with their corresponding event.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
class ContextLink {

    static final ThreadLocal<Map<Class<? extends EventHandler>, Event>> context = new ThreadLocal<Map<Class<? extends EventHandler>, Event>>() {
        @Override
        protected Map<Class<? extends EventHandler>, Event> initialValue() {
            return new HashMap<Class<? extends EventHandler>, Event>();
        }
    };

    /**
     * Adds an handler call to the context, ie. store the handler called and the given event.
     *
     * @param handlerClass the handler called
     * @param event        the event fired
     */
    public void put(Class<? extends EventHandler> handlerClass, Event event) {
        context.get().put(handlerClass, event);
    }

    /**
     * Gets the context map and clean it.
     *
     * @return map of handler called with their event
     */
    public Map<Class<? extends EventHandler>, Event> peek() {
        Map<Class<? extends EventHandler>, Event> classEventMap = context.get();
        context.remove();
        return classEventMap;
    }
}
