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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import org.seedstack.business.api.Event;
import org.seedstack.business.api.EventHandler;
import io.nuun.kernel.api.Plugin;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.core.AbstractPlugin;
import org.apache.commons.configuration.Configuration;
import org.jodah.typetools.TypeResolver;
import org.kametic.specifications.Specification;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This plugin scans all EventHandler, then passes them to the EventModule.
 * It also determines the strategy to adopt for event: will they be sync or async ?
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 04/06/2014
 */
public class EventPlugin extends AbstractPlugin {

    public static final String PREFIX = "org.seedstack.business.event";
    private final Specification<Class<?>> eventHandlerSpecification = and(classImplements(EventHandler.class));
    private final Specification<Class<?>> eventSpecification = and(classImplements(Event.class));

    private Multimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlersByEvent = ArrayListMultimap.create();

    private List<Class<? extends EventHandler>> eventHandlerClasses = new ArrayList<Class<? extends EventHandler>>();
    private boolean watchRepo;

    @Override
    public String name() {
        return "seed-business-event";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder().specification(eventHandlerSpecification).specification(eventSpecification).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public InitState init(InitContext initContext) {
        ApplicationPlugin applicationPlugin = (ApplicationPlugin) initContext.pluginsRequired().iterator().next();
        Configuration eventConfiguration = applicationPlugin.getApplication().getConfiguration().subset(PREFIX);

        watchRepo = eventConfiguration.getBoolean("domain.watch", false);
        Collection<Class<?>> scannedEventHandlerClasses = initContext.scannedTypesBySpecification().get(eventHandlerSpecification);

        for (Class<?> scannedEventHandlerClass : scannedEventHandlerClasses) {
            if (EventHandler.class.isAssignableFrom(scannedEventHandlerClass)) {
                eventHandlerClasses.add((Class<EventHandler>) scannedEventHandlerClass);
                Class<Event> typeParameterClass = (Class<Event>) TypeResolver.resolveRawArguments(EventHandler.class, (Class<EventHandler>) scannedEventHandlerClass)[0];
                eventHandlersByEvent.put(typeParameterClass, (Class<EventHandler>) scannedEventHandlerClass);
            }
        }

        return InitState.INITIALIZED;
    }

    @Override
    public Collection<Class<? extends Plugin>> requiredPlugins() {
        Collection<Class<? extends Plugin>> plugins = new ArrayList<Class<? extends Plugin>>();
        plugins.add(ApplicationPlugin.class);
        return plugins;
    }

    @Override
    public Object nativeUnitModule() {
        return new EventModule(ImmutableListMultimap.copyOf(eventHandlersByEvent), ImmutableList.copyOf(eventHandlerClasses), watchRepo);
    }

}
