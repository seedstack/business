/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.BindingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;

public class AssemblerPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerPlugin.class);
    private final Map<Key<Assembler>, Class<? extends Assembler>> assemblerBindings = new HashMap<>();
    private final Collection<Class<? extends Assembler>> defaultAssemblerClasses = new HashSet<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();
    private final Collection<Class<?>> dtoOfClasses = new HashSet<>();

    @Override
    public String name() {
        return "business-assemblers";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .specification(BusinessSpecifications.EXPLICIT_ASSEMBLER)
                .specification(BusinessSpecifications.DEFAULT_ASSEMBLER)
                .specification(BusinessSpecifications.DTO_OF)
                .build();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        streamClasses(initContext, BusinessSpecifications.DTO_OF, Object.class).forEach(dtoOfClasses::add);
        LOGGER.debug("DtoOf classes => {}", dtoOfClasses);

        streamClasses(initContext, BusinessSpecifications.DEFAULT_ASSEMBLER, Assembler.class).forEach(defaultAssemblerClasses::add);
        LOGGER.debug("Default assemblers => {}", defaultAssemblerClasses);

        Collection subTypes = initContext.scannedTypesBySpecification().get(BusinessSpecifications.EXPLICIT_ASSEMBLER);
        assemblerBindings.putAll(BindingUtils.resolveBindingDefinitions(Assembler.class, (Collection<Class<? extends Assembler>>) subTypes));

        bindingStrategies.addAll(new DefaultAssemblerCollector(defaultAssemblerClasses).collect(dtoOfClasses));

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new AssemblerModule(assemblerBindings, bindingStrategies);
    }
}
