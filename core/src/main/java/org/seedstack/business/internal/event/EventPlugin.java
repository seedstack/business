/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.event;

import com.google.common.collect.*;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.core.AbstractPlugin;
import net.jodah.typetools.TypeResolver;
import org.apache.commons.configuration.Configuration;
import org.kametic.specifications.Specification;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This plugin scans all EventHandler, then passes them to the EventModule.
 * It also determines the strategy to adopt for event: will they be sync or async ?
 *
 * @author pierre.thirouin@ext.mpsa.com
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
        Configuration eventConfiguration = initContext.dependency(ConfigurationProvider.class)
                .getConfiguration().subset(PREFIX);

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
    public Collection<Class<?>> requiredPlugins() {
        return Lists.<Class<?>>newArrayList(ConfigurationProvider.class);
    }

    @Override
    public Object nativeUnitModule() {
        return new EventModule(ImmutableListMultimap.copyOf(eventHandlersByEvent), ImmutableList.copyOf(eventHandlerClasses), watchRepo);
    }

}
