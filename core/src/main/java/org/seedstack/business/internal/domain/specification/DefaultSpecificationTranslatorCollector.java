/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.domain.specification.SpecificationConverter;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


class DefaultSpecificationTranslatorCollector {
    private static final String DEFAULT_TRANSLATOR_KEY = "defaultTranslator";
    private final Collection<Class<? extends SpecificationTranslator>> defaultSpecTranslatorClasses;
    private final Collection<Class<? extends SpecificationConverter>> converterClasses;
    private final Application application;

    DefaultSpecificationTranslatorCollector(Collection<Class<? extends SpecificationTranslator>> defaultSpecTranslatorClasses, Collection<Class<? extends SpecificationConverter>> converterClasses, Application application) {
        this.defaultSpecTranslatorClasses = defaultSpecTranslatorClasses;
        this.converterClasses = converterClasses;
        this.application = application;
    }

    /**
     * Prepares the binding strategies which bind default spec translator. The specificity here is that it could have
     * multiple implementations of default repository, i.e. one per persistence.
     *
     * @return a binding strategy
     */
    Collection<BindingStrategy> collect(Collection<Class<? extends AggregateRoot<?>>> aggregateClasses) {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

        // Extract the type variables which will be passed to the constructor
        for (Class<? extends SpecificationTranslator> defaultSpecTranslatorClass : defaultSpecTranslatorClasses) {
            Map<Type[], Key<?>> generics = new HashMap<>();
            Class<?>[] translatorParams = BusinessUtils.resolveGenerics(SpecificationTranslator.class, defaultSpecTranslatorClass);
            for (Class<? extends AggregateRoot> aggregateClass : BusinessUtils.includeSuperClasses(aggregateClasses)) {
                Class<?>[] params = translatorParams.clone();
                params[0] = aggregateClass;

                TypeLiteral<?> genericInterface = TypeLiteral.get(Types.newParameterizedType(SpecificationTranslator.class, params));
                Key<?> defaultKey = BusinessUtils.defaultQualifier(application, DEFAULT_TRANSLATOR_KEY, aggregateClass, genericInterface);

                bindingStrategies.add(new SpecificationConverterBindingStrategy(aggregateClass, defaultSpecTranslatorClass, converterClasses));
                generics.put(params, defaultKey);
            }
            bindingStrategies.add(new GenericBindingStrategy<>(SpecificationTranslator.class, defaultSpecTranslatorClass, generics));
        }

        return bindingStrategies;
    }
}
