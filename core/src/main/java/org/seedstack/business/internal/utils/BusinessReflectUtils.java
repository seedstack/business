/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.utils;

import net.jodah.typetools.TypeResolver;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.seed.core.utils.SeedCheckUtils;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public final class BusinessReflectUtils {

    BusinessReflectUtils() {
    }

    public static Class<?> getAggregateIdClass(Class<? extends AggregateRoot<?>> aggregateRootClass) {
        SeedCheckUtils.checkIfNotNull(aggregateRootClass, "aggregateRootClass should not be null");
        return TypeResolver.resolveRawArguments(aggregateRootClass.getGenericSuperclass(), aggregateRootClass)[0];
    }

}
