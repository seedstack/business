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

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gag.annotation.remark.OhSnap;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import org.seedstack.business.api.Event;
import org.seedstack.business.api.EventHandler;
import org.seedstack.business.api.EventService;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.domain.annotations.stereotypes.Delete;
import org.seedstack.business.api.domain.annotations.stereotypes.Persist;
import org.seedstack.business.api.domain.annotations.stereotypes.Read;
import org.seedstack.seed.core.utils.SeedReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Event module. Bind EventHandlers, EventService and optionally add an interceptor on repositories.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 04/06/2014
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
            LOGGER.debug("binding {} in scope {}", eventHandlerClass, Scopes.NO_SCOPE);
        }
        bind(EVENT_HANDLER_MAP_TYPE_LITERAL).toInstance(eventHandlersByEvent);
        bind(EventService.class).to(EventServiceInternal.class).in(Scopes.SINGLETON);
        if (watchRepo) {
            RepositoryMethodInterceptor interceptor = new RepositoryMethodInterceptor();
            requestInjection(interceptor);
            bindInterceptor(Matchers.subclassesOf(Repository.class), handlerMethod(), interceptor);
        }
    }

    Matcher<Method> handlerMethod() {

        return new AbstractMatcher<Method>() {

            @Override
            public boolean matches(Method candidate) {
                return !candidate.isSynthetic() && (checkAnnotation(candidate, Read.class)
                        || checkAnnotation(candidate, Delete.class)
                        || checkAnnotation(candidate, Persist.class));
            }

        };
    }

    /**
     * Checks if an annotation the present on a method and its ancestors.
     *
     * @param candidate  the candidate method
     * @param annotation the annotation class
     * @return true if the annotation is present, false otherwise
     */
    public static Boolean checkAnnotation(Method candidate, Class<? extends Annotation> annotation) {
        return getMethodAnnotationFromAncestors(candidate, annotation) != null;
    }

    /**
     * Gets an annotation from a method and its ancestors.
     *
     * @param annotatedElement      the annotated element to check
     * @param annotationClassToFind the annotation to find
     * @param <T>                   the annotation type
     * @return the annotation found or null if the annotation is not present
     */
    @SuppressWarnings("unchecked")
    @OhSnap("This method does not work with ancestors that use generic parameters")
    public static <T extends Annotation> T getMethodAnnotationFromAncestors(AnnotatedElement annotatedElement, Class<T> annotationClassToFind) {
        Method annotatedMethod;
        if (Method.class.isAssignableFrom(annotatedElement.getClass())) {
            annotatedMethod = (Method) annotatedElement;
        } else {
            throw new IllegalArgumentException("annotated element should be a method.");
        }

        List<AnnotatedElement> list = Lists.newArrayList();
        list.addAll(Arrays.asList(SeedReflectionUtils.getAllInterfacesAndClasses(annotatedMethod.getDeclaringClass())));

        for (AnnotatedElement element : list) {
            try {
                AnnotatedElement methodElement = ((Class) element).getDeclaredMethod(annotatedMethod.getName(), annotatedMethod.getParameterTypes());
                // element search
                for (Annotation anno : methodElement.getAnnotations()) {
                    if (anno.annotationType().equals(annotationClassToFind)) {
                        return (T) anno;
                    }
                }

                // deep search
                for (Annotation anno : methodElement.getAnnotations()) {
                    T result = (T) SeedReflectionUtils.getAnnotationDeep(anno, annotationClassToFind);
                    if (result != null) {
                        return result;
                    }
                }
            } catch (NoSuchMethodException e) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(e.getMessage(), e);
                }
                // the method is not inherited from following parent classes.
                break;
            }
        }

        return null;
    }
}
