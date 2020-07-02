/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.utils;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.seedstack.shed.reflect.AnnotationPredicates.annotationAnnotatedWith;
import static org.seedstack.shed.reflect.AnnotationPredicates.annotationIsOfClass;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.inject.Qualifier;
import net.jodah.typetools.TypeResolver;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.core.internal.guice.ProxyUtils;
import org.seedstack.shed.ClassLoaders;
import org.seedstack.shed.reflect.Annotations;
import org.seedstack.shed.reflect.ClassPredicates;

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
    public static <T> Class<?>[] resolveGenerics(Class<T> superType, Class<? extends T> subType) {
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
    public static <A extends AggregateRoot<I>, I> Class<I> resolveAggregateIdClass(Class<A> aggregateRootClass) {
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
            result[i] = resolveGenerics(AggregateRoot.class, aggregateRootClasses[i])[0];
        }
        return result;
    }

    /**
     * Checks that classes satisfying a specification are assignable to a base class and return a
     * typed stream of it.
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<Class<? extends T>> streamClasses(Collection<Class<?>> classes,
            Class<? extends T> baseClass) {
        return classes
                .stream()
                .filter(ClassPredicates.classIsDescendantOf(baseClass))
                .map(c -> (Class<T>) c);
    }

    /**
     * Optionally returns the qualifier annotation of a class.
     */
    public static Optional<Annotation> getQualifier(AnnotatedElement annotatedElement) {
        AnnotatedElement cleanedAnnotatedElement;
        if (annotatedElement instanceof Class<?>) {
            cleanedAnnotatedElement = ProxyUtils.cleanProxy((Class<?>) annotatedElement);
        } else {
            cleanedAnnotatedElement = annotatedElement;
        }
        return Annotations.on(cleanedAnnotatedElement)
                .findAll()
                .filter(annotationAnnotatedWith(Qualifier.class, false).or(annotationIsOfClass(Named.class)))
                .findFirst();
    }

    /**
     * Returns the Guice key qualified with the default qualifier configured for the specified class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<Key<T>> resolveDefaultQualifier(Map<Key<?>, Class<?>> bindings,
            ClassConfiguration<?> classConfiguration, String property, Class<?> qualifiedClass,
            TypeLiteral<T> genericInterface) {
        Key<T> key = null;

        if (classConfiguration != null && !classConfiguration.isEmpty()) {
            String qualifierName = classConfiguration.get(property);
            if (qualifierName != null && !"".equals(qualifierName)) {
                try {
                    ClassLoader classLoader = ClassLoaders.findMostCompleteClassLoader(BusinessUtils.class);
                    Class<?> qualifierClass = classLoader.loadClass(qualifierName);
                    if (Annotation.class.isAssignableFrom(qualifierClass)) {
                        key = Key.get(genericInterface, (Class<? extends Annotation>) qualifierClass);
                    } else {
                        throw BusinessException.createNew(BusinessErrorCode.CLASS_IS_NOT_AN_ANNOTATION)
                                .put("class", qualifiedClass)
                                .put("qualifier", qualifierName);
                    }
                } catch (ClassNotFoundException e) {
                    key = Key.get(genericInterface, Names.named(qualifierName));
                }
            }
        }

        if (key == null || bindings.containsKey(Key.get(key.getTypeLiteral()))) {
            return Optional.empty();
        } else {
            return Optional.of(key);
        }
    }

    /**
     * Walks the class hierarchy of each class in the given collection and adds its superclasses to
     * the mix.
     */
    @SuppressWarnings("unchecked")
    public static Set<Class<? extends AggregateRoot<?>>> includeSuperClasses(
            Collection<Class<? extends AggregateRoot>> aggregateClasses) {
        Set<Class<? extends AggregateRoot<?>>> results = new HashSet<>();
        for (Class<?> aggregateClass : aggregateClasses) {
            Class<?> classToAdd = aggregateClass;
            while (classToAdd != null) {
                if (AggregateRoot.class.isAssignableFrom(classToAdd) && !classToAdd.equals(
                        BaseAggregateRoot.class) && !classToAdd.equals(AggregateRoot.class)) {
                    results.add((Class<? extends AggregateRoot<?>>) classToAdd);
                    classToAdd = classToAdd.getSuperclass();
                } else {
                    break;
                }
            }
        }
        return results;
    }
}
