/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.utils;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import org.kametic.specifications.Specification;
import org.seedstack.seed.core.internal.guice.BindingUtils;
import org.seedstack.seed.core.internal.utils.SpecificationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.seedstack.shed.reflect.ClassPredicates.classIsDescendantOf;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;

public final class PluginUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginUtils.class);

    private PluginUtils() {
        // no instantiation allowed
    }

    /**
     * Builds a ClasspathScanRequest to find all the descendant of the given interfaces.
     *
     * @param <T>                         the class of the interface.
     * @param classpathScanRequestBuilder the Nuun classpath scan request builder.
     * @param interfaces                  the interfaces.
     * @return a map where the key is an interface and the value is a specification matching descendants of this interface.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Class<?>> Map<T, Specification<? extends T>> classpathRequestForDescendantTypesOf(ClasspathScanRequestBuilder classpathScanRequestBuilder, Collection<T> interfaces) {
        Map<T, Specification<? extends T>> specsByInterface = new HashMap<>();
        for (T anInterface : interfaces) {
            LOGGER.trace("Request implementations of: {}", anInterface.getName());
            Specification<Class<?>> spec = new SpecificationBuilder<>(classIsDescendantOf(anInterface).and(classIsInterface().negate()).and(classModifierIs(Modifier.ABSTRACT).negate())).build();
            classpathScanRequestBuilder = classpathScanRequestBuilder.specification(spec);
            specsByInterface.put(anInterface, (Specification<? extends T>) spec);
        }
        return specsByInterface;
    }

    /**
     * Associates scanned interfaces to their implementations. It also handles qualified bindings in the case where
     * there is multiple implementation for the same interface.
     * <p>
     * This is the "default mode" for binding in the business framework.
     * </p>
     *
     * @param <T>              the class of the interface.
     * @param initContext      the context containing the implementations
     * @param interfaces       the interfaces to bind
     * @param specsByInterface the descendant specifications indexed by their common interface.
     * @return the map of interface/implementation to bind
     * @see BindingUtils#resolveBindingDefinitions(Class, Class, Class[])
     */
    @SuppressWarnings("unchecked")
    public static <T extends Class> Map<Key<T>, ? extends T> associateInterfaceToImplementations(InitContext initContext, Collection<T> interfaces, Map<T, Specification<? extends T>> specsByInterface) {
        Map<Key<T>, ? extends T> keyMap = new HashMap<>();
        for (T anInterface : interfaces) {
            Collection<Class<?>> subTypes = initContext.scannedTypesBySpecification().get(specsByInterface.get(anInterface));
            keyMap.putAll(BindingUtils.resolveBindingDefinitions(anInterface, subTypes));
        }
        return keyMap;
    }
}
