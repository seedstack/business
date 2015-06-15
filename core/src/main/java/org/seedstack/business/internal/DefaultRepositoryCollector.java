/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.common.reflect.TypeToken;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.internal.strategy.GenericBindingStrategy;
import org.seedstack.business.internal.strategy.api.BindingStrategy;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
class DefaultRepositoryCollector {

    private final Collection<Class<?>> aggregateClasses;
    private final Collection<Class<?>> domainRepoImpls;

    public DefaultRepositoryCollector(Collection<Class<?>> aggregateClasses, Collection<Class<?>> domainRepoImpls) {
        this.aggregateClasses = aggregateClasses;
        this.domainRepoImpls = domainRepoImpls;
    }

    /**
     * Prepares the binding strategies which bind default repositories. The specificity here is that it could have
     * multiple implementations of default repository, i.e. one per persistence.
     *
     * @return a binding strategy
     */
    public Collection<BindingStrategy> collect() {
        // this method support multiple default implementation for repository (one for each persistence technology).

        Collection<BindingStrategy> bindingStrategies = new ArrayList<BindingStrategy>();
        Collection<Class<?>[]> generics = new ArrayList<Class<?>[]>();
        for (Class<?> aggregateClass : aggregateClasses) {
            Class<?> keyType = TypeToken.of(aggregateClass).resolveType(AggregateRoot.class.getTypeParameters()[0]).getRawType();
            generics.add(new Class<?>[]{aggregateClass, keyType});
        }
        for (Class<?> domainRepoImpl : domainRepoImpls) {
            bindingStrategies.add(new GenericBindingStrategy<Repository>(Repository.class, (Class<? extends Repository>) domainRepoImpl, generics));
        }
        return bindingStrategies;
    }
}
