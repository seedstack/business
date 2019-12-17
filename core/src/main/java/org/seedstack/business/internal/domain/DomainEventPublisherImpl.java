/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventInterceptor;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.domain.PriorizedEvent;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DomainEventPublisherImpl implements DomainEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisherImpl.class);
    private static final ThreadLocal<Multimap<Class<? extends DomainEvent>, DomainEvent>> context = ThreadLocal
            .withInitial(ArrayListMultimap::create);
    private final Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlerClassesByEvent;
    private final Injector injector;

    @Inject
    DomainEventPublisherImpl(Injector injector,
            Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlerClassesByEvent) {
        this.injector = injector;
        this.eventHandlerClassesByEvent = eventHandlerClassesByEvent;
    }

    @Override
    public <E extends DomainEvent> void publish(E event) {
        LOGGER.debug("Firing event {} synchronously", event.getClass()
                .getName());
        for (Class<? extends DomainEvent> eventClass : eventHandlerClassesByEvent.keys()
                .elementSet()) {
            if (eventClass.isAssignableFrom(event.getClass())) {
                checkCyclicCall(eventClass, event);
                Multimap<Class<? extends DomainEvent>, DomainEvent> currentEventClasses = context
                        .get();
                boolean isFirstCall = currentEventClasses.isEmpty();
                context.get()
                        .put(eventClass, event);

                try {
                    notifyHandlers(eventClass, event);
                } catch (Exception e) {
                    throw BusinessException.wrap(e,
                            BusinessErrorCode.EXCEPTION_OCCURRED_DURING_EVENT_HANDLER_INVOCATION)
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
        if (context.get()
                .get(eventClass)
                .contains(domainEvent)) {
            throw BusinessException.createNew(BusinessErrorCode.EVENT_CYCLE_DETECTED)
                    .put("event", eventClass);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends DomainEvent> void notifyHandlers(Class<? extends E> eventClass, E event) {

        DomainEventInterceptor interceptor = getInterceptor(event);
        List<DomainEventHandler> handlers = eventHandlerClassesByEvent.get(eventClass)
                .stream().map(injector::getInstance)
                .collect(Collectors.toList());

        handlers = interceptor.interceptDomainHandler(handlers);

        for (DomainEventHandler domainEventHandler : handlers) {
            LOGGER.debug("Notifying event handler {}", domainEventHandler.getClass().getName());
            domainEventHandler.onEvent(event);
        }
    }

    private <E extends DomainEvent> DomainEventInterceptor getInterceptor(E event) {
        
        PriorizedEvent annotation = event.getClass().getAnnotation(PriorizedEvent.class);
        if (annotation != null && annotation.priorizator() != null) {
            return injector.getInstance(annotation.priorizator());
        }
        return injector.getInstance(PriorityEventHandlerInterceptor.class);
    }
}
