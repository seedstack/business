/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.utils;

import static org.seedstack.shed.reflect.ClassPredicates.classIsDescendantOf;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.kametic.specifications.Specification;
import org.seedstack.business.Overriding;
import org.seedstack.seed.core.internal.guice.BindingUtils;
import org.seedstack.seed.core.internal.utils.SpecificationBuilder;
import org.seedstack.shed.reflect.Annotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @return a map where the key is an interface and the value is a specification matching
     *         descendants of this interface.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Class<?>> Map<T, Specification<? extends T>> classpathRequestForDescendantTypesOf(
            ClasspathScanRequestBuilder classpathScanRequestBuilder, Collection<T> interfaces) {
        Map<T, Specification<? extends T>> specsByInterface = new HashMap<>();
        for (T anInterface : interfaces) {
            LOGGER.trace("Request implementations of: {}", anInterface.getName());
            Specification<Class<?>> spec = new SpecificationBuilder<>(
                    classIsDescendantOf(anInterface).and(classIsInterface().negate())
                            .and(classModifierIs(Modifier.ABSTRACT).negate())).build();
            classpathScanRequestBuilder.specification(spec);
            specsByInterface.put(anInterface, (Specification<? extends T>) spec);
        }
        return specsByInterface;
    }

    /**
     * Associates scanned interfaces to their implementations. It also handles qualified bindings in
     * the case where there is multiple implementation for the same interface. <p> This is the
     * "default mode" for binding in the business framework. </p>
     *
     * @param initContext      the context containing the implementations.
     * @param interfaces       the interfaces to bind.
     * @param specsByInterface the specifications used to scan implementations, indexed by
     *                         interfaces.
     * @param overridingMode   if true, only implementations that satisfy {@link
     *                         #isOverridingImplementation()} are bound, if false only implementations
     *                         that don't satisfy {@link #isOverridingImplementation()} are bound.
     * @param <T>              supertype of all interfaces to bind.
     * @return the map of interface/implementation to bind.
     * @see BindingUtils#resolveBindingDefinitions(Class, Class, Class[])
     */
    @SuppressWarnings("unchecked")
    public static <T extends Class> Map<Key<T>, ? extends T> associateInterfacesToImplementations(
            InitContext initContext, Collection<T> interfaces, Map<T, Specification<? extends T>> specsByInterface,
            boolean overridingMode) {
        Map<Key<T>, ? extends T> keyMap = new HashMap<>();
        for (T anInterface : interfaces) {
            keyMap.putAll(associateInterfaceToImplementations(anInterface, initContext.scannedTypesBySpecification()
                    .get(specsByInterface.get(anInterface)), overridingMode));

        }
        return keyMap;
    }

    /**
     * Associates scanned interfaces to their implementations. It also handles qualified bindings in
     * the case where there is multiple implementation for the same interface. <p> This is the
     * "default mode" for binding in the business framework. </p>
     *
     * @param <T>             the class of the interface.
     * @param anInterface     the interface to bind
     * @param implementations the classes implementing the interface.
     * @param overridingMode  if true, only implementations that satisfy {@link
     *                        #isOverridingImplementation()} are bound, if false only implementations
     *                        that don't satisfy {@link #isOverridingImplementation()} are bound.
     * @return the map of interface/implementation to bind
     * @see BindingUtils#resolveBindingDefinitions(Class, Class, Class[])
     */
    public static <T> Map<Key<T>, Class<? extends T>> associateInterfaceToImplementations(Class<T> anInterface,
            Collection<Class<? extends T>> implementations, boolean overridingMode) {
        return BindingUtils.resolveBindingDefinitions(anInterface, implementations.stream()
                .filter(overridingMode ? isOverridingImplementation() : isOverridingImplementation().negate())
                .collect(Collectors.toList()));
    }

    private static Predicate<Class<?>> isOverridingImplementation() {
        return (item) -> Annotations.on(item)
                .traversingSuperclasses()
                .includingMetaAnnotations()
                .find(Overriding.class)
                .isPresent();
    }
}
