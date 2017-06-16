/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import net.jodah.typetools.TypeResolver;
import org.kametic.specifications.Specification;
import org.seedstack.business.domain.DomainEvent;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.utils.SpecificationBuilder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.seedstack.shed.reflect.ClassPredicates.classIsAssignableFrom;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;

/**
 * This plugin scans all EventHandler, then passes them to the EventModule.
 * It also determines the strategy to adopt for event: will they be sync or async ?
 */
public class EventPlugin extends AbstractSeedPlugin {
    private static final Specification<Class<?>> eventHandlerSpecification = new SpecificationBuilder<>(
            classIsInterface().negate()
                    .and(classModifierIs(Modifier.ABSTRACT).negate())
                    .and(classIsAssignableFrom(DomainEventHandler.class)))
            .build();

    private static final Specification<Class<?>> eventSpecification = new SpecificationBuilder<>(
            classIsAssignableFrom(DomainEvent.class))
            .build();

    private final Multimap<Class<? extends DomainEvent>, Class<? extends DomainEventHandler>> eventHandlersByEvent = ArrayListMultimap.create();
    private final List<Class<? extends DomainEventHandler>> eventHandlerClasses = new ArrayList<>();

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
        Collection<Class<?>> scannedEventHandlerClasses = initContext.scannedTypesBySpecification().get(eventHandlerSpecification);

        for (Class<?> scannedEventHandlerClass : scannedEventHandlerClasses) {
            if (DomainEventHandler.class.isAssignableFrom(scannedEventHandlerClass)) {
                eventHandlerClasses.add((Class<DomainEventHandler>) scannedEventHandlerClass);
                Class<DomainEvent> typeParameterClass = (Class<DomainEvent>) TypeResolver.resolveRawArguments(DomainEventHandler.class, (Class<DomainEventHandler>) scannedEventHandlerClass)[0];
                eventHandlersByEvent.put(typeParameterClass, (Class<DomainEventHandler>) scannedEventHandlerClass);
            }
        }

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new EventModule(ImmutableListMultimap.copyOf(eventHandlersByEvent), ImmutableList.copyOf(eventHandlerClasses));
    }

}
