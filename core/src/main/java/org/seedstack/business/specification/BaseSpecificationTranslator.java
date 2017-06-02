/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.specification.SpecificationConverter;
import org.seedstack.business.spi.specification.SpecificationTranslator;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static org.seedstack.business.internal.utils.BusinessUtils.getQualifier;

public abstract class BaseSpecificationTranslator<B, C> implements SpecificationTranslator<B, C> {
    private final Class<B> criteriaBuilderClass;
    private final Class<C> criteriaClass;
    private final Annotation qualifier;
    @Inject
    private Injector injector;

    @SuppressWarnings("unchecked")
    protected BaseSpecificationTranslator() {
        Type[] generics = BusinessUtils.resolveGenerics(BaseSpecificationTranslator.class, getClass());
        this.criteriaBuilderClass = (Class<B>) generics[0];
        this.criteriaClass = (Class<C>) generics[1];
        this.qualifier = getQualifier(getClass()).orElse(null);
    }

    protected <T> C convert(Specification<T> specification, B builder) {
        if (specification instanceof DelegatingSpecification) {
            return convert(((DelegatingSpecification<T>) specification).getDelegate(), builder);
        } else {
            SpecificationConverter<Specification<T>, B, C> converter;
            Class<? extends Specification> specificationClass = specification.getClass();
            try {
                converter = injector.getInstance(buildKey(specificationClass));
            } catch (ConfigurationException e) {
                throw new RuntimeException("No converter found for " + specificationClass.getName(), e);
            }
            return converter.convert(specification, builder, this);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Key<SpecificationConverter<Specification<T>, B, C>> buildKey(Class<? extends Specification> specificationClass) {
        if (qualifier != null) {
            return Key.get(
                    (TypeLiteral<SpecificationConverter<Specification<T>, B, C>>) TypeLiteral.get(
                            Types.newParameterizedType(
                                    SpecificationConverter.class,
                                    specificationClass,
                                    criteriaBuilderClass,
                                    criteriaClass)
                    ),
                    qualifier);
        } else {
            return Key.get(
                    (TypeLiteral<SpecificationConverter<Specification<T>, B, C>>) TypeLiteral.get(
                            Types.newParameterizedType(
                                    SpecificationConverter.class,
                                    specificationClass,
                                    criteriaBuilderClass,
                                    criteriaClass)
                    ));
        }
    }
}
