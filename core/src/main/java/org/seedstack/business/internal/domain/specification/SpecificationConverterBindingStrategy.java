/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.domain.specification.SpecificationConverter;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.shed.reflect.AnnotationPredicates;
import org.seedstack.shed.reflect.Annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.util.Collection;

class SpecificationConverterBindingStrategy implements BindingStrategy {
    private static final int TRANSLATOR_CRITERIA_BUILDER_INDEX = 1;
    private static final int TRANSLATOR_CRITERIA_INDEX = 2;
    private static final int CONVERTER_CRITERIA_INDEX = 3;
    private static final int CONVERTER_CRITERIA_BUILDER_INDEX = 2;
    private final Class<? extends AggregateRoot> aggregateClass;
    private final Class<? extends SpecificationTranslator> specificationTranslatorClass;
    private final Collection<Class<? extends SpecificationConverter>> converterClasses;
    private final Annotation qualifier;
    private final Class<?> criteriaBuilderClass;
    private final Class<?> criteriaClass;

    SpecificationConverterBindingStrategy(Class<? extends AggregateRoot> aggregateClass, Class<? extends SpecificationTranslator> specificationTranslatorClass, Collection<Class<? extends SpecificationConverter>> converterClasses) {
        this.aggregateClass = aggregateClass;
        this.specificationTranslatorClass = specificationTranslatorClass;
        this.converterClasses = converterClasses;
        this.qualifier = getQualifier(specificationTranslatorClass);

        // Determine generics of translator
        Class<?>[] translatorGenerics = BusinessUtils.resolveGenerics(SpecificationTranslator.class, specificationTranslatorClass);
        criteriaBuilderClass = translatorGenerics[TRANSLATOR_CRITERIA_BUILDER_INDEX];
        criteriaClass = translatorGenerics[TRANSLATOR_CRITERIA_INDEX];
    }

    @Override
    @SuppressWarnings("unchecked")
    public void resolve(Binder binder) {
        for (Class<? extends SpecificationConverter> converterClass : converterClasses) {
            if (qualifier.equals(getQualifier(converterClass))) {
                Class<?>[] converterGenerics = BusinessUtils.resolveGenerics(SpecificationConverter.class, converterClass);
                if (!converterGenerics[CONVERTER_CRITERIA_INDEX].equals(criteriaClass) || !converterGenerics[CONVERTER_CRITERIA_BUILDER_INDEX].equals(criteriaBuilderClass)) {
                    throw new IllegalStateException("Converter " + converterClass.getName() + " is not compatible with translator " + specificationTranslatorClass.getName());
                }
                converterGenerics[0] = aggregateClass;
                binder.bind(Key.get(
                        TypeLiteral.get(
                                Types.newParameterizedType(
                                        SpecificationConverter.class,
                                        converterGenerics)
                        ),
                        qualifier)
                ).to((Class) converterClass);
            }
        }
    }

    private Annotation getQualifier(Class<?> someClass) {
        return Annotations.on(someClass)
                .findAll()
                .filter(AnnotationPredicates.annotationAnnotatedWith(Qualifier.class, false))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The class " + someClass.getName() + " has no qualifier"));
    }
}
