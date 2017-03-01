/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.internal.domain.specification;

import com.google.common.collect.Lists;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.spi.DomainProvider;
import org.seedstack.business.spi.domain.specification.SpecificationConverter;
import org.seedstack.business.spi.domain.specification.SpecificationTranslator;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;

/**
 * Plugin handling for identity management.
 */
public class SpecificationPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificationPlugin.class);
    private final Collection<Class<? extends SpecificationTranslator>> defaultTranslatorClasses = new HashSet<>();
    private final Collection<Class<? extends SpecificationConverter>> converterClasses = new HashSet<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

    @Override
    public String name() {
        return "business-specification";
    }

    @Override
    protected Collection<Class<?>> dependencies() {
        return Lists.newArrayList(DomainProvider.class);
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .specification(BusinessSpecifications.DEFAULT_SPECIFICATION_TRANSLATOR)
                .specification(BusinessSpecifications.SPECIFICATION_CONVERTER)
                .build();
    }

    @Override
    public InitState initialize(InitContext initContext) {
        // The first round is used to scan interfaces
        if (round.isFirst()) {
            streamClasses(initContext, BusinessSpecifications.DEFAULT_SPECIFICATION_TRANSLATOR, SpecificationTranslator.class).forEach(defaultTranslatorClasses::add);
            LOGGER.debug("Default specification translators => {}", defaultTranslatorClasses);

            streamClasses(initContext, BusinessSpecifications.SPECIFICATION_CONVERTER, SpecificationConverter.class).forEach(converterClasses::add);
            LOGGER.debug("Specification converters => {}", converterClasses);

            return InitState.NON_INITIALIZED;
        } else {
            DomainProvider domainProvider = initContext.dependency(DomainProvider.class);
            bindingStrategies.addAll(new DefaultSpecificationTranslatorCollector(defaultTranslatorClasses, converterClasses, getApplication()).collect(domainProvider.aggregateRoots()));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new SpecificationModule(bindingStrategies);
    }
}
