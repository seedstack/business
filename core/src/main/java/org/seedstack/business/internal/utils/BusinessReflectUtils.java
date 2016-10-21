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
import org.seedstack.seed.core.utils.SeedCheckUtils;


public final class BusinessReflectUtils {

    BusinessReflectUtils() {
    }

    public static Class<?> getAggregateIdClass(Class<? extends AggregateRoot<?>> aggregateRootClass) {
        SeedCheckUtils.checkIfNotNull(aggregateRootClass, "aggregateRootClass should not be null");
        return TypeResolver.resolveRawArguments(TypeResolver.resolveGenericType(AggregateRoot.class, aggregateRootClass), aggregateRootClass)[0];
    }

}
