/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
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
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import net.jodah.typetools.TypeResolver;
import org.kametic.specifications.Specification;
import org.seedstack.business.BusinessConfig;
import org.seedstack.business.Event;
import org.seedstack.business.EventHandler;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.seedstack.seed.core.utils.BaseClassSpecifications.ancestorImplements;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classIsAbstract;
import static org.seedstack.seed.core.utils.BaseClassSpecifications.classIsInterface;

/**
 * This plugin scans all EventHandler, then passes them to the EventModule.
 * It also determines the strategy to adopt for event: will they be sync or async ?
 */
public class EventPlugin extends AbstractSeedPlugin {
    private final Specification<Class<?>> eventHandlerSpecification = and(ancestorImplements(EventHandler.class), not(classIsInterface()), not(classIsAbstract()));
    private final Specification<Class<?>> eventSpecification = and(classImplements(Event.class));

    private Multimap<Class<? extends Event>, Class<? extends EventHandler>> eventHandlersByEvent = ArrayListMultimap.create();

    private List<Class<? extends EventHandler>> eventHandlerClasses = new ArrayList<>();
    private boolean watchRepo;

    @Override
    public String name() {
        return "business-events";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder().specification(eventHandlerSpecification).specification(eventSpecification).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public InitState initialize(InitContext initContext) {
        BusinessConfig.EventConfig eventConfiguration = getConfiguration(BusinessConfig.EventConfig.class);

        watchRepo = eventConfiguration.isPublishRepositoryEvents();
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
    public Object nativeUnitModule() {
        return new EventModule(ImmutableListMultimap.copyOf(eventHandlersByEvent), ImmutableList.copyOf(eventHandlerClasses), watchRepo);
    }

}
