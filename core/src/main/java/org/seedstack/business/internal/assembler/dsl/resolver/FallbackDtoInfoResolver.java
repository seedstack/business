/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.resolver;

import org.seedstack.business.Producible;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.spi.assembler.DtoInfoResolver;
import org.seedstack.business.spi.assembler.DtoInfoResolverPriority;

import javax.annotation.Priority;
import javax.inject.Inject;

import static org.seedstack.shed.reflect.Classes.instantiateDefault;

@Priority(DtoInfoResolverPriority.FALLBACK)
public class FallbackDtoInfoResolver implements DtoInfoResolver {
    @Inject
    private DomainRegistry domainRegistry;

    @Override
    public <D> boolean supports(D dto) {
        return true;
    }

    @Override
    public <D, ID> ID resolveId(D dto, Class<ID> aggregateIdClass) {
        return createObject(aggregateIdClass);
    }

    @Override
    public <D, ID> ID resolveId(D dto, Class<ID> aggregateIdClass, int position) {
        return createObject(aggregateIdClass);
    }

    @Override
    public <D, A extends AggregateRoot<ID>, ID> A resolveAggregate(D dto, Class<A> aggregateRootClass) {
        return createObject(aggregateRootClass);
    }

    @Override
    public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass, int position) {
        return this.createObject(aggregateRootClass);
    }

    @SuppressWarnings("unchecked")
    private <T> T createObject(Class<T> classToProduce) {
        if (!Producible.class.isAssignableFrom(classToProduce)) {
            return instantiateDefault(classToProduce);
        } else {
            return (T) domainRegistry.getFactory(classToProduce.asSubclass(Producible.class)).create();
        }
    }
}
