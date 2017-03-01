/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.seed.SeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

class DomainEventPublisherInternal implements DomainEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisherInternal.class);
    private static final ThreadLocal<Multimap<Class<? extends DomainEvent>, DomainEvent>> context = ThreadLocal.withInitial(ArrayListMultimap::create);
    private final ImmutableListMultimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlerClassesByEvent;
    private final Injector injector;

    @Inject
    DomainEventPublisherInternal(Injector injector, Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlerClassesByEvent) {
        this.injector = injector;
        this.eventHandlerClassesByEvent = ImmutableListMultimap.copyOf(eventHandlerClassesByEvent);
    }

    @Override
    public <E extends DomainEvent> void publish(E event) {
        LOGGER.debug("Firing event {} synchronously", event.getClass().getName());
        for (Class<? extends DomainEvent> eventClass : eventHandlerClassesByEvent.keys().elementSet()) {
            if (eventClass.isAssignableFrom(event.getClass())) {
                checkCyclicCall(eventClass, event);
                Multimap<Class<? extends DomainEvent>, DomainEvent> currentEventClasses = context.get();
                boolean isFirstCall = currentEventClasses.isEmpty();
                context.get().put(eventClass, event);

                try {
                    notifyHandlers(eventClass, event);
                } catch (Exception e) {
                    throw SeedException.wrap(e, BusinessErrorCode.EXCEPTION_OCCURRED_DURING_EVENT_HANDLER_INVOCATION)
                            .put("event", eventClass.getName());
                } finally {
                    if (isFirstCall) {
                        context.remove();
                    }
                }
            }
        }
    }

    private void checkCyclicCall(Class<? extends DomainEvent> eventClass, DomainEvent domainEvent) {
        if (context.get().get(eventClass).contains(domainEvent)) {
            throw SeedException.createNew(BusinessErrorCode.EVENT_CYCLE_DETECTED).put("event", eventClass);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends DomainEvent> void notifyHandlers(Class<? extends E> eventClass, E event) {
        Collection<Class<? extends DomainEventHandler>> eventHandlers = eventHandlerClassesByEvent.get(eventClass);
        for (Class<? extends DomainEventHandler> eventHandlerClass : eventHandlers) {
            LOGGER.debug("Notifying event handler {}", eventHandlerClass.getName());
            DomainEventHandler domainEventHandler = injector.getInstance(eventHandlerClass);
            domainEventHandler.handle(event);
        }
    }
}
