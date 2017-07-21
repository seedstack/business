/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.seedstack.business.BusinessException;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventPublisher;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.utils.MethodMatcherBuilder;
import org.seedstack.shed.exception.BaseException;
import org.seedstack.shed.reflect.ClassPredicates;
import org.seedstack.shed.reflect.Classes;
import org.seedstack.shed.reflect.ExecutablePredicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

class DomainModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainModule.class);
    private final Map<Key<?>, Class<?>> bindings;
    private final Collection<BindingStrategy> bindingStrategies;
    private final Collection<Class<? extends IdentityGenerator>> identityGeneratorClasses;
    private final Collection<Class<? extends DomainEventHandler>> eventHandlerClasses;

    DomainModule(Map<Key<?>, Class<?>> bindings, Collection<BindingStrategy> bindingStrategies, Collection<Class<? extends IdentityGenerator>> identityGeneratorClasses, Collection<Class<? extends DomainEventHandler>> eventHandlerClasses) {
        this.bindings = bindings;
        this.bindingStrategies = bindingStrategies;
        this.identityGeneratorClasses = identityGeneratorClasses;
        this.eventHandlerClasses = eventHandlerClasses;
    }

    @Override
    protected void configure() {
        bind(DomainRegistry.class).to(DomainRegistryImpl.class);

        // Simple bindings
        for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
            LOGGER.trace("Binding {} to {}", binding.getKey(), binding.getValue().getSimpleName());
            bind(binding.getKey()).to(cast(binding.getValue()));
        }

        // Binding strategies
        for (BindingStrategy bindingStrategy : bindingStrategies) {
            bindingStrategy.resolve(binder());
        }

        // Identity generation
        for (Class<? extends IdentityGenerator> identityGeneratorClass : identityGeneratorClasses) {
            bind(identityGeneratorClass);
            Named named = identityGeneratorClass.getAnnotation(Named.class);
            if (named != null) {
                bind(findIdentityGeneratorInterface(identityGeneratorClass)).annotatedWith(Names.named(named.value())).to(identityGeneratorClass);
            }
        }
        bind(IdentityService.class).to(IdentityServiceImpl.class);
        IdentityInterceptor identityInterceptor = new IdentityInterceptor();
        requestInjection(identityInterceptor);
        bindInterceptor(Matchers.subclassesOf(Factory.class), factoryMethods(), identityInterceptor);

        // Domain events
        Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlersByEvent = HashMultimap.create();
        for (Class<? extends DomainEventHandler> eventHandlerClass : eventHandlerClasses) {
            eventHandlersByEvent.put(getEventClass(eventHandlerClass), eventHandlerClass);
            bind(eventHandlerClass);
        }
        bind(new EventHandlersByEventTypeLiteral()).toInstance(eventHandlersByEvent);
        bind(DomainEventPublisher.class).to(DomainEventPublisherImpl.class).in(Scopes.SINGLETON);
    }

    @SuppressWarnings("unchecked")
    private <C extends Class<?>> C cast(Class<?> aClass) {
        return (C) aClass;
    }

    @SuppressWarnings("unchecked")
    private Class<DomainEvent> getEventClass(Class<? extends DomainEventHandler> domainEventHandlerClass) {
        return (Class<DomainEvent>) BusinessUtils.resolveGenerics(DomainEventHandler.class, domainEventHandlerClass)[0];
    }

    @SuppressWarnings("unchecked")
    private Class<IdentityGenerator> findIdentityGeneratorInterface(Class<? extends IdentityGenerator> identityGeneratorClass) {
        return (Class<IdentityGenerator>) Classes.from(identityGeneratorClass)
                .traversingInterfaces()
                .traversingSuperclasses()
                .classes()
                .filter(ClassPredicates.classIsInterface().and(ClassPredicates.classIsAssignableFrom(IdentityGenerator.class)))
                .findFirst()
                .<BaseException>orElseThrow(() -> BusinessException.createNew(BusinessErrorCode.ILLEGAL_IDENTITY_GENERATOR).put("class", identityGeneratorClass));
    }

    private Matcher<Method> factoryMethods() {
        return new MethodMatcherBuilder(ExecutablePredicates.<Method>executableBelongsToClassAssignableTo(Factory.class)
                .and(CreateResolver.INSTANCE)
        ).build();
    }

    private static class EventHandlersByEventTypeLiteral extends TypeLiteral<Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>>> {
    }
}
