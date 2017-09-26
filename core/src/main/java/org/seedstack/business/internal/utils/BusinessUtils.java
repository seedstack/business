/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import io.nuun.kernel.api.plugin.context.InitContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.inject.Qualifier;
import net.jodah.typetools.TypeResolver;
import org.kametic.specifications.Specification;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.core.internal.guice.ProxyUtils;
import org.seedstack.shed.ClassLoaders;
import org.seedstack.shed.reflect.AnnotationPredicates;
import org.seedstack.shed.reflect.Annotations;

public final class BusinessUtils {

    private BusinessUtils() {
        // no instantiation allowed
    }

    /**
     * Resolve generics of a class relatively to a superclass.
     *
     * @param superType the superclass.
     * @param subType   the subclass.
     * @param <T>       the type of the superclass.
     * @return the resolved types.
     */
    public static <T> Type[] resolveGenerics(Class<T> superType, Class<? extends T> subType) {
        checkNotNull(superType, "superType should not be null");
        checkNotNull(subType, "subType should not be null");
        Class<?> subTypeWithoutProxy = ProxyUtils.cleanProxy(subType);
        return TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(superType, subTypeWithoutProxy),
                subTypeWithoutProxy);
    }

    /**
     * Returns the identifier class for an aggregate root class.
     */
    @SuppressWarnings("unchecked")
    public static <A extends AggregateRoot<I>, I> Class<I> getAggregateIdClass(Class<A> aggregateRootClass) {
        checkNotNull(aggregateRootClass, "aggregateRootClass should not be null");
        return (Class<I>) resolveGenerics(AggregateRoot.class, aggregateRootClass)[0];
    }

    /**
     * Returns an arrays of all identifier class corresponding the the given array of aggregate root
     * classes.
     */
    public static Class<?>[] getAggregateIdClasses(Class<? extends AggregateRoot<?>>[] aggregateRootClasses) {
        checkNotNull(aggregateRootClasses, "aggregateRootClasses should not be null");
        Class<?>[] result = new Class<?>[aggregateRootClasses.length];
        for (int i = 0; i < aggregateRootClasses.length; i++) {
            result[i] = (Class<?>) resolveGenerics(AggregateRoot.class, aggregateRootClasses[i])[0];
        }
        return result;
    }

    /**
     * Checks that classes satisfying a specification are assignable to a base class and return a
     * typed stream of it.
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<Class<? extends T>> streamClasses(InitContext initContext, Specification<Class<?>> spec,
            Class<T> baseClass) {
        Map<Specification, Collection<Class<?>>> scannedTypesBySpecification = initContext
                .scannedTypesBySpecification();
        return scannedTypesBySpecification.get(spec)
                .stream()
                .filter(baseClass::isAssignableFrom)
                .map(c -> (Class<T>) c);
    }

    /**
     * Optionally returns the qualifier annotation of a class.
     */
    public static Optional<Annotation> getQualifier(Class<?> someClass) {
        return Annotations.on(ProxyUtils.cleanProxy(someClass))
                .findAll()
                .filter(AnnotationPredicates.annotationAnnotatedWith(Qualifier.class, false))
                .findFirst();
    }

    /**
     * Returns the Guice key qualified with the default qualifier configured for the specified class.
     */
    @SuppressWarnings("unchecked")
    public static Key<?> defaultQualifier(Application application, String key, Class<?> aggregateClass,
            TypeLiteral<?> genericInterface) {
        Key<?> defaultKey = null;
        ClassConfiguration<?> configuration = application.getConfiguration(aggregateClass);
        if (configuration != null && !configuration.isEmpty()) {
            String qualifierName = configuration.get(key);
            if (qualifierName != null && !"".equals(qualifierName)) {
                try {
                    ClassLoader classLoader = ClassLoaders.findMostCompleteClassLoader(BusinessUtils.class);
                    Class<?> qualifierClass = classLoader.loadClass(qualifierName);
                    if (Annotation.class.isAssignableFrom(qualifierClass)) {
                        defaultKey = Key.get(genericInterface, (Class<? extends Annotation>) qualifierClass);
                    } else {
                        throw BusinessException.createNew(BusinessErrorCode.CLASS_IS_NOT_AN_ANNOTATION)
                                .put("aggregateClass", aggregateClass.getName())
                                .put("qualifierClass", qualifierName);
                    }
                } catch (ClassNotFoundException e) {
                    defaultKey = Key.get(genericInterface, Names.named(qualifierName));
                }
            }
        }
        return defaultKey;
    }

    /**
     * Walks the class hierarchy of each class in the given collection and adds its superclasses to
     * the mix.
     */
    public static Set<Class<?>> includeSuperClasses(Collection<Class<?>> aggregateClasses) {
        Set<Class<?>> results = new HashSet<>();
        for (Class<?> aggregateClass : aggregateClasses) {
            Class<?> classToAdd = aggregateClass;
            while (classToAdd != null) {
                if (AggregateRoot.class.isAssignableFrom(classToAdd) && !classToAdd.equals(
                        BaseAggregateRoot.class) && !classToAdd.equals(AggregateRoot.class)) {
                    results.add(classToAdd);
                    classToAdd = classToAdd.getSuperclass();
                } else {
                    break;
                }
            }
        }
        return results;
    }
}
