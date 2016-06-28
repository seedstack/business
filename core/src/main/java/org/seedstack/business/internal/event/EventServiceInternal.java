/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;
import org.seedstack.business.domain.events.EventErrorCodes;
import org.seedstack.seed.SeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Internal implementation of EventService.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
class EventServiceInternal implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceInternal.class);

    private final ImmutableListMultimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlerClassesByEvent;

    private final Injector injector;

    private static final ThreadLocal<Multimap<Class<? extends Event>, Event>> context = new ThreadLocal<Multimap<Class<? extends Event>, Event>>() {
        @Override
        protected Multimap<Class<? extends Event>, Event> initialValue() {
            return ArrayListMultimap.create();
        }
    };

    @Inject
    EventServiceInternal(Injector injector, Multimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlerClassesByEvent) {
        this.injector = injector;
        this.eventHandlerClassesByEvent = ImmutableListMultimap.copyOf(eventHandlerClassesByEvent);
    }

    @Override
    public <E extends Event> void fire(E event) {
        LOGGER.debug("Synchronously fired {}", event.getClass().getName());
        for (Class<? extends Event> eventClass : eventHandlerClassesByEvent.keys().elementSet()) {
            if (eventClass.isAssignableFrom(event.getClass())) {
                checkCyclicCall(eventClass, event);
                Multimap<Class<? extends Event>, Event> currentEventClasses = context.get();

                boolean isFirstCall = currentEventClasses.isEmpty();

                context.get().put(eventClass, event);
                try {
                    notifyHandlers(eventClass, event);
                } catch (Exception e) {
                    throw SeedException.wrap(e, EventErrorCodes.HANDLER_EXECUTION_FAILED).put("event", eventClass);
                } finally {
                    if (isFirstCall) {
                        context.remove();
                    }
                }
            }
        }
    }

    private void checkCyclicCall(Class<? extends Event> eventClass, Event event) {
        if (context.get().get(eventClass).contains(event)) {
            throw SeedException.createNew(EventErrorCodes.CYCLE_WAS_DETECTED_IN_FIRED_EVENT).put("event", eventClass);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void notifyHandlers(Class<? extends E> eventClass, E event) {
        Collection<Class<? extends EventHandler>> eventHandlers = eventHandlerClassesByEvent.get(eventClass);
        for (Class<? extends EventHandler> eventHandlerClass : eventHandlers) {
            LOGGER.debug("Notify handler {}", eventHandlerClass.getName());
            EventHandler eventHandler = injector.getInstance(eventHandlerClass);
            eventHandler.handle(event);
        }
    }
}
