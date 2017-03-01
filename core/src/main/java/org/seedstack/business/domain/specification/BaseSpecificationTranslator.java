/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.domain.specification.AggregateSpecification;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.domain.specification.SpecificationConverter;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;
import org.seedstack.seed.core.internal.guice.ProxyUtils;
import org.seedstack.shed.reflect.AnnotationPredicates;
import org.seedstack.shed.reflect.Annotations;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;

public abstract class BaseSpecificationTranslator<A extends AggregateRoot<?>, B, C> implements SpecificationTranslator<A, B, C> {
    private final Class<A> aggregateRootClass;
    private final Class<B> criteriaBuilderClass;
    private final Class<C> criteriaClass;
    private final Annotation qualifier;
    @Inject
    private Injector injector;

    @SuppressWarnings("unchecked")
    protected BaseSpecificationTranslator() {
        Class<?>[] generics = BusinessUtils.resolveGenerics(BaseSpecificationTranslator.class, getClass());
        this.aggregateRootClass = (Class<A>) generics[0];
        this.criteriaBuilderClass = (Class<B>) generics[1];
        this.criteriaClass = (Class<C>) generics[2];
        this.qualifier = getQualifier();
    }

    protected BaseSpecificationTranslator(Class<A> aggregateRootClass, Class<B> criteriaBuilderClass, Class<C> criteriaClass) {
        this.aggregateRootClass = aggregateRootClass;
        this.criteriaBuilderClass = criteriaBuilderClass;
        this.criteriaClass = criteriaClass;
        this.qualifier = getQualifier();
    }

    @SuppressWarnings("unchecked")
    protected C convert(Specification<A> specification, B builder) {
        SpecificationConverter<A, Specification<A>, B, C> converter;
        Class<? extends Specification> specificationClass = specification.getClass();
        if (specification instanceof AggregateSpecification) {
            return convert(((AggregateSpecification<A>) specification).getSpecification(), builder);
        } else {
            try {
                converter = injector.getInstance(Key.get(
                        (TypeLiteral<SpecificationConverter<A, Specification<A>, B, C>>) TypeLiteral.get(
                                Types.newParameterizedType(
                                        SpecificationConverter.class,
                                        aggregateRootClass,
                                        specificationClass,
                                        criteriaBuilderClass,
                                        criteriaClass)
                        ),
                        qualifier)
                );
            } catch (ConfigurationException e) {
                throw new RuntimeException("No converter found for " + specificationClass.getName());
            }
        }
        return converter.convert(specification, builder, this);
    }

    private Annotation getQualifier() {
        Class<?> theClass = ProxyUtils.cleanProxy(getClass());
        return Annotations.on(theClass)
                .findAll()
                .filter(AnnotationPredicates.annotationAnnotatedWith(Qualifier.class, false))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The specification translator " + theClass.getName() + " must have a qualifier"));
    }
}
