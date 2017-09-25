/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.spi;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import javax.inject.Inject;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.MethodMatcher;
import org.seedstack.shed.reflect.ReflectUtils;

/**
 * An helper base class that can be extended to create an implementation of {@link
 * DtoInfoResolver}.
 */
public abstract class BaseDtoInfoResolver implements DtoInfoResolver {

  @Inject
  private DomainRegistry domainRegistry;

  @Override
  public <D, I> I resolveId(D dto, Class<I> aggregateIdClass) {
    return resolveId(dto, aggregateIdClass, -1);
  }

  @Override
  public <D, A extends AggregateRoot<?>> A resolveAggregate(D dto, Class<A> aggregateRootClass) {
    return resolveAggregate(dto, aggregateRootClass, -1);
  }

  /**
   * Implements the logic to create an aggregate identifier, using a factory if the identifier class
   * implements {@link Producible}.
   *
   * @param aggregateIdClass the identifier class.
   * @param id               the existing identifier if any.
   * @param parameters       the parameters to pass to the factory if any.
   * @param <I>              the type of the aggregate root identifier.
   * @return the identifier.
   */
  @SuppressWarnings("unchecked")
  protected <I> I createIdentifier(Class<I> aggregateIdClass, I id, Object... parameters) {
    if (id != null) {
      if (aggregateIdClass.isAssignableFrom(id.getClass())) {
        return id;
      } else {
        throw BusinessException.createNew(BusinessErrorCode.RESOLVED_DTO_ID_IS_INVALID)
            .put("dtoIdClass", id.getClass().getName())
            .put("aggregateIdClass", aggregateIdClass.getName());
      }
    } else {
      if (!Producible.class.isAssignableFrom(aggregateIdClass)) {
        throw BusinessException.createNew(BusinessErrorCode.RESOLVED_DTO_ID_IS_NOT_PRODUCIBLE)
            .put("aggregateIdClass", aggregateIdClass.getName());
      } else {
        return (I) domainRegistry.getFactory(aggregateIdClass.asSubclass(Producible.class))
            .create(parameters);
      }
    }
  }

  /**
   * Implements the logic to create an aggregate.
   *
   * @param aggregateClass the aggregate class.
   * @param parameters     the parameters to pass to the factory if any.
   * @param <A>            the type of the aggregate root.
   * @return the aggregate root.
   */
  protected <A extends AggregateRoot<?>> A createFromFactory(Class<A> aggregateClass,
      Object... parameters) {
    checkNotNull(aggregateClass);
    checkNotNull(parameters);

    Factory<A> factory = domainRegistry.getFactory(aggregateClass);

    // Find the method in the factory which match the signature determined with the previously
    // extracted parameters
    Method factoryMethod;
    boolean useDefaultFactory = false;
    try {
      factoryMethod = MethodMatcher
          .findMatchingMethod(factory.getClass(), aggregateClass, parameters);
      if (factoryMethod == null) {
        useDefaultFactory = true;
      }
    } catch (Exception e) {
      throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_FIND_FACTORY_METHOD)
          .put("aggregateClass", aggregateClass.getName())
          .put("parameters", Arrays.toString(parameters));
    }

    // Invoke the factory to create the aggregate root
    try {
      if (useDefaultFactory) {
        return factory.create(parameters);
      } else {
        if (parameters.length == 0) {
          return ReflectUtils.invoke(factoryMethod, factory);
        } else {
          return ReflectUtils.invoke(factoryMethod, factory, parameters);
        }
      }
    } catch (Exception e) {
      throw BusinessException.wrap(e, BusinessErrorCode.UNABLE_TO_INVOKE_FACTORY_METHOD)
          .put("aggregateClass", aggregateClass.getName())
          .put("factoryClass", factory.getClass().getName())
          .put("factoryMethod",
              Optional.ofNullable(factoryMethod).map(Method::getName).orElse("create"))
          .put("parameters", Arrays.toString(parameters));
    }
  }
}
