/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.utils;

import net.jodah.typetools.TypeResolver;
import org.seedstack.business.domain.AggregateRoot;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;


public final class BusinessUtils {
    private BusinessUtils() {
        // no instantiation allowed
    }

    public static Class<?> getAggregateIdClass(Class<? extends AggregateRoot<?>> aggregateRootClass) {
        checkNotNull(aggregateRootClass, "aggregateRootClass should not be null");
        return TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(AggregateRoot.class, aggregateRootClass), aggregateRootClass)[0];
    }

    public static <T> Collection<Class<? extends T>> convertClassCollection(Class<T> target, Collection<Class<?>> collection) {
        return collection.stream().map((Function<Class<?>, Class<? extends T>>) x -> x.asSubclass(target)).collect(Collectors.toList());
    }
}
