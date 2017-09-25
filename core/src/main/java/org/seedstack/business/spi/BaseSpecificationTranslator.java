/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.spi;

import static org.seedstack.business.internal.utils.BusinessUtils.getQualifier;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Inject;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.SubstitutableSpecification;

/**
 * An helper base class that can be extended for implementing {@link SpecificationTranslator}s.
 * Handles the invocation of the relevant {@link SpecificationConverter} in the {@link
 * #convert(Specification, Object)} method.
 *
 * @param <C> the type of the translation context.
 * @param <T> the type of the target object.
 */
public abstract class BaseSpecificationTranslator<C, T> implements SpecificationTranslator<C, T> {

  private final Class<C> contextClass;
  private final Class<T> targetClass;
  private final Annotation qualifier;
  @Inject
  private Injector injector;

  /**
   * Creates a base specification translator. Actual classes used for translation are determined by
   * reflection.
   */
  @SuppressWarnings("unchecked")
  protected BaseSpecificationTranslator() {
    Type[] generics = BusinessUtils.resolveGenerics(BaseSpecificationTranslator.class, getClass());
    this.contextClass = (Class<C>) generics[0];
    this.targetClass = (Class<T>) generics[1];
    this.qualifier = getQualifier(getClass()).orElse(null);
  }

  /**
   * Find and invoke the relevant {@link SpecificationConverter} for the given specification to
   * convert it into an object of type {@link T}.
   *
   * @param specification the specification to convert.
   * @param context       the translation context.
   * @param <S>           the type of the specification to convert.
   * @return the converted target object representing the given specification.
   */
  protected <S extends Specification<?>> T convert(S specification, C context) {
    if (specification instanceof SubstitutableSpecification) {
      return convert(((SubstitutableSpecification<?>) specification).getSubstitute(), context);
    } else {
      SpecificationConverter<S, C, T> converter;
      Class<? extends Specification> specificationClass = specification.getClass();
      try {
        converter = injector.getInstance(buildKey(specificationClass));
      } catch (ConfigurationException e) {
        throw BusinessException.wrap(e, BusinessErrorCode.NO_CONVERTER_FOUND)
            .put("contextClass", contextClass)
            .put("targetClass", targetClass)
            .put("specificationClass", specificationClass);
      }
      return converter.convert(specification, context, this);
    }
  }

  @Override
  public Class<C> getContextClass() {
    return contextClass;
  }

  @Override
  public Class<T> getTargetClass() {
    return targetClass;
  }

  @SuppressWarnings("unchecked")
  private <S extends Specification<?>> Key<SpecificationConverter<S, C, T>> buildKey(
      Class<? extends Specification> specificationClass) {
    if (qualifier != null) {
      return Key.get((TypeLiteral<SpecificationConverter<S, C, T>>) TypeLiteral
              .get(Types
                  .newParameterizedType(SpecificationConverter.class, specificationClass,
                      contextClass,
                      targetClass)),
          qualifier);
    } else {
      return Key.get((TypeLiteral<SpecificationConverter<S, C, T>>) TypeLiteral
          .get(
              Types.newParameterizedType(SpecificationConverter.class, specificationClass,
                  contextClass, targetClass)));
    }
  }
}
