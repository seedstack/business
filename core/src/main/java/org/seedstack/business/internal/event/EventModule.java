/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.Multimap;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.business.EventService;
import org.seedstack.business.domain.Repository;
import org.seedstack.seed.core.internal.utils.MethodMatcherBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Event module. Bind EventHandlers, EventService and optionally add an interceptor on repositories.
 */
@EventConcern
class EventModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventModule.class);
    private static final TypeLiteral<Multimap<Class<? extends Event>, Class<? extends EventHandler>>> EVENT_HANDLER_MAP_TYPE_LITERAL = new TypeLiteral<Multimap<Class<? extends Event>, Class<? extends EventHandler>>>() {
    };
    private final Multimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlersByEvent;
    private final List<Class<? extends EventHandler>> eventHandlerClasses;
    private final boolean watchRepo;

    EventModule(Multimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlersByEvent, List<Class<? extends EventHandler>> eventHandlerClasses, boolean watchRepo) {
        this.eventHandlersByEvent = eventHandlersByEvent;
        this.eventHandlerClasses = eventHandlerClasses;
        this.watchRepo = watchRepo;
    }

    @Override
    protected void configure() {
        for (Class<? extends EventHandler> eventHandlerClass : eventHandlerClasses) {
            bind(eventHandlerClass);
            LOGGER.debug("Binding event handler {}", eventHandlerClass);
        }
        bind(EVENT_HANDLER_MAP_TYPE_LITERAL).toInstance(eventHandlersByEvent);
        bind(EventService.class).to(EventServiceInternal.class).in(Scopes.SINGLETON);
        if (watchRepo) {
            RepositoryMethodInterceptor interceptor = new RepositoryMethodInterceptor();
            requestInjection(interceptor);
            bindInterceptor(Matchers.subclassesOf(Repository.class), handlerMethod(), interceptor);
        }
    }

    private Matcher<Method> handlerMethod() {
        return new MethodMatcherBuilder(ReadResolver.INSTANCE
                .or(DeleteResolver.INSTANCE)
                .or(PersistResolver.INSTANCE)
        ).build();
    }
}
