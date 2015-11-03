/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event.test;

import com.google.inject.Injector;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;
import org.seedstack.business.test.*;
import org.seedstack.seed.SeedException;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;


class EventFixtureInternal implements EventFixture {

    static final String EVENT = "event";
    static final String EXPECTED = "expected";
    static final String HANDLERS = "handlers";
    static final String HANDLER = "handler";

    @Inject
    private Injector injector;

    @Inject
    private ContextLink contextLink;

    @Inject
    private EventService eventService;

    /////////////////////////////// Test on event handler ////////////////

    @Override
    public EventProvider given(Event event) {
        return new EventProviderInternal(event);
    }

    class EventProviderInternal implements EventProvider, EventServiceProvider {

        private final Event event;

        EventProviderInternal(Event event) {
            this.event = event;
        }

        @Override
        public EventServiceProvider whenFired() {
            eventService.fire(this.event);
            return this;
        }

        @Override
        public void wasHandledBy(Class<? extends EventHandler>... handlers) {
            Set<Class<? extends EventHandler>> calledHandlers = contextLink.peek().keySet();

            for (Class<? extends EventHandler> handler : handlers) {
                if (!calledHandlers.contains(handler)) {
                    throw SeedException.createNew(EventTestErrorCodes.EVENT_WAS_NOT_HANDLER_BY)
                            .put(EVENT, event).put(HANDLER, handler);
                }
            }
        }

        @Override
        public void wasHandledExactlyBy(Class<? extends EventHandler>... handlers) {
            Set<Class<? extends EventHandler>> calledHandlers = contextLink.peek().keySet();

            for (Class<? extends EventHandler> handler : handlers) {
                if (!calledHandlers.contains(handler)) {
                    throw SeedException.createNew(EventTestErrorCodes.EVENT_WAS_NOT_EXACTLY_HANDLER_BY)
                            .put(EVENT, event).put(EXPECTED, handlers).put(HANDLERS, calledHandlers);
                }
            }
            if (handlers.length != calledHandlers.size()) {
                throw SeedException.createNew(EventTestErrorCodes.EVENT_WAS_NOT_EXACTLY_HANDLER_BY)
                        .put(EVENT, event).put(EXPECTED, handlers).put(HANDLERS, calledHandlers);
            }
        }

        @Override
        public void wasNotHandledBy(Class<? extends EventHandler>... handlers) {
            Set<Class<? extends EventHandler>> calledHandlers = contextLink.peek().keySet();

            for (Class<? extends EventHandler> handler : handlers) {
                if (calledHandlers.contains(handler)) {
                    throw SeedException.createNew(EventTestErrorCodes.EVENT_WAS_HANDLER_BY)
                            .put(EVENT, event).put(HANDLERS, calledHandlers).put(EXPECTED, handlers);
                }
            }
        }
    }

    /////////////////////////////// Test on service method ////////////////

    @Override
    public ServiceProvider given(Class aClass) {
        return new ServiceProviderInternal(aClass);
    }

    class ServiceProviderInternal implements ServiceProvider, HandlerProvider {

        private List<Class> argClasses;

        private final Class underTest;

        private String method;

        private Object[] args;

        ServiceProviderInternal(Class underTest) {
            this.underTest = underTest;
        }


        @Override
        public HandlerProvider whenCalled(String method, Object... args) {
            this.method = method;
            this.args = args;
            this.argClasses = new ArrayList<Class>(args.length);
            for (Object arg : args) {
                argClasses.add(arg.getClass());
            }
            return this;
        }

        @Override
        public void eventWasHandledBy(Event event, Class<? extends EventHandler> handler) {
            Map<Class<? extends EventHandler>, Event> handlerMap = new HashMap<Class<? extends EventHandler>, Event>();
            handlerMap.put(handler, event);
            eventWasHandledBy(handlerMap);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void eventWasHandledBy(Map<Class<? extends EventHandler>, Event> handlerMap) {
            try {
                Method declaredMethod = this.underTest.getDeclaredMethod(this.method, this.argClasses.toArray(new Class[this.args.length]));
                Object instance = injector.getInstance(underTest);
                declaredMethod.invoke(instance, args);
            } catch (Exception e) {
                throw SeedException.wrap(e, EventTestErrorCodes.FAILED_TO_INVOKE_METHOD);
            }
            Map<Class<? extends EventHandler>, Event> callMap = contextLink.peek();
            for (Map.Entry<Class<? extends EventHandler>, Event> entry : handlerMap.entrySet()) {
                Event event = callMap.get(entry.getKey());
                if (event == null) {
                    throw SeedException.createNew(EventTestErrorCodes.HANDLER_WAS_NOT_CALLED)
                            .put(HANDLER, entry.getKey());
                }

                if (entry.getValue() == null || !entry.getValue().equals(event)) {
                    throw SeedException.createNew(EventTestErrorCodes.HANDLER_WAS_NOT_CALLED_WITH_EXPECTED_EVENT)
                            .put(HANDLER, entry.getKey()).put(EVENT, entry.getValue());
                }
            }
        }
    }
}
