/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.finder;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import org.kametic.specifications.Specification;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;
import static org.seedstack.business.internal.utils.PluginUtils.associateInterfacesToImplementations;
import static org.seedstack.business.internal.utils.PluginUtils.classpathRequestForDescendantTypesOf;

/**
 * This plugins detects base building blocks of the business framework: aggregates, value objects, repositories, factories,
 * services, policies and finders. It also handles default repositories and default factories.
 */
public class FinderPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinderPlugin.class);

    private final Collection<Class<?>> finderInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> finderSpecs = new HashMap<>();

    private final Map<Key<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Key<?>, Class<?>> overridingBindings = new HashMap<>();
    private final List<BindingStrategy> bindingStrategies = new ArrayList<>();

    @Override
    public String name() {
        return "business-finder";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        if (round.isFirst()) {
            return classpathScanRequestBuilder()
                    .specification(BusinessSpecifications.FINDER)
                    .build();
        } else {
            ClasspathScanRequestBuilder classpathScanRequestBuilder = classpathScanRequestBuilder();
            finderSpecs.putAll(classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, finderInterfaces));
            return classpathScanRequestBuilder.build();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        if (round.isFirst()) {
            // Scan interfaces
            streamClasses(initContext, BusinessSpecifications.FINDER, Object.class).forEach(finderInterfaces::add);
            LOGGER.debug("Finders => {}", finderInterfaces);

            return InitState.NON_INITIALIZED;
        } else {
            // Then add bindings for explicit implementations
            bindings.putAll(associateInterfacesToImplementations(initContext, finderInterfaces, finderSpecs, false));
            overridingBindings.putAll(associateInterfacesToImplementations(initContext, finderInterfaces, finderSpecs, true));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new FinderModule(bindings, bindingStrategies);
    }

    @Override
    public Object nativeOverridingUnitModule() {
        return new FinderOverridingModule(overridingBindings);
    }
}
