/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.specification;

import static org.seedstack.business.internal.utils.BusinessUtils.getQualifier;
import static org.seedstack.business.internal.utils.BusinessUtils.resolveGenerics;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import java.util.Set;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;

class SpecificationModule extends PrivateModule {

    private final Set<Class<? extends SpecificationTranslator>> specificationTranslatorClasses;
    private final Set<Class<? extends SpecificationConverter>> specificationConverterClasses;

    SpecificationModule(Set<Class<? extends SpecificationTranslator>> specificationTranslatorClasses,
            Set<Class<? extends SpecificationConverter>> specificationConverterClasses) {
        this.specificationTranslatorClasses = specificationTranslatorClasses;
        this.specificationConverterClasses = specificationConverterClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        specificationTranslatorClasses.forEach(translatorClass -> {
            Key<SpecificationTranslator> key = buildKey(SpecificationTranslator.class, translatorClass);
            bind(key).to(translatorClass);
            expose(key);
        });
        specificationConverterClasses.forEach(
                converterClass -> bind(buildKey(SpecificationConverter.class, converterClass)).to(converterClass));

        bind(SpecificationBuilder.class).to(SpecificationBuilderImpl.class);
        expose(SpecificationBuilder.class);
    }

    @SuppressWarnings("unchecked")
    private <T> Key<T> buildKey(Class<T> someInterface, Class<? extends T> someClass) {
        return getQualifier(someClass).map(annotation -> Key.get((TypeLiteral<T>) TypeLiteral.get(
                Types.newParameterizedType(someInterface, resolveGenerics(someInterface, someClass))), annotation))
                .orElse(Key.get((TypeLiteral<T>) TypeLiteral.get(
                        Types.newParameterizedType(someInterface, resolveGenerics(someInterface, someClass)))));
    }
}
