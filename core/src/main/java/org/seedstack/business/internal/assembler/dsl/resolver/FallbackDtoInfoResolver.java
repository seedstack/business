/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl.resolver;

import static org.seedstack.shed.reflect.Classes.instantiateDefault;

import javax.annotation.Priority;
import javax.inject.Inject;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.business.spi.DtoInfoResolverPriority;

@Priority(DtoInfoResolverPriority.FALLBACK)
public class FallbackDtoInfoResolver implements DtoInfoResolver {

  @Inject
  private DomainRegistry domainRegistry;

  @Override
  public <DtoT> boolean supports(DtoT dto) {
    return true;
  }

  @Override
  public <DtoT, IdT> IdT resolveId(DtoT dto, Class<IdT> aggregateIdClass) {
    return createObject(aggregateIdClass);
  }

  @Override
  public <DtoT, IdT> IdT resolveId(DtoT dto, Class<IdT> aggregateIdClass, int position) {
    return createObject(aggregateIdClass);
  }

  @Override
  public <DtoT, AggregateRootT extends AggregateRoot<?>> AggregateRootT resolveAggregate(DtoT dto,
      Class<AggregateRootT> aggregateRootClass) {
    return createObject(aggregateRootClass);
  }

  @Override
  public <DtoT, AggregateRootT extends AggregateRoot<?>> AggregateRootT resolveAggregate(DtoT dto,
      Class<AggregateRootT> aggregateRootClass, int position) {
    return createObject(aggregateRootClass);
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
