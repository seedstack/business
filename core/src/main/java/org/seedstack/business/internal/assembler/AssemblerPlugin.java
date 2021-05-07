/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;
import static org.seedstack.business.internal.utils.PluginUtils.associateInterfaceToImplementations;
import static org.seedstack.shed.misc.PriorityUtils.sortByPriority;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssemblerPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerPlugin.class);
    private final Collection<Class<? extends Assembler>> assemblerClasses = new HashSet<>();
    private final Collection<Class<? extends Assembler>> defaultAssemblerClasses = new HashSet<>();
    private final List<Class<? extends DtoInfoResolver>> dtoInfoResolverClasses = new ArrayList<>();
    private final Collection<Class<?>> dtoOfClasses = new HashSet<>();
    private final Map<Key<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Key<?>, Class<?>> overridingBindings = new HashMap<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

    @Override
    public String name() {
        return "business-assemblers";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .predicate(BusinessSpecifications.EXPLICIT_ASSEMBLER)
                .predicate(BusinessSpecifications.DEFAULT_ASSEMBLER)
                .predicate(BusinessSpecifications.DTO_INFO_RESOLVER)
                .predicate(BusinessSpecifications.DTO_OF)
                .build();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        Map<Predicate<Class<?>>, Collection<Class<?>>> classesBySpec = initContext.scannedTypesByPredicate();

        streamClasses(classesBySpec.get(BusinessSpecifications.EXPLICIT_ASSEMBLER), Assembler.class).forEach(
                assemblerClasses::add);
        LOGGER.debug("Assemblers => {}", assemblerClasses);

        streamClasses(classesBySpec.get(BusinessSpecifications.DEFAULT_ASSEMBLER), Assembler.class).forEach(
                defaultAssemblerClasses::add);
        LOGGER.debug("Default assemblers => {}", defaultAssemblerClasses);

        streamClasses(classesBySpec.get(BusinessSpecifications.DTO_OF), Object.class).forEach(dtoOfClasses::add);
        LOGGER.debug("DTO classes mappable with default assemblers => {}", dtoOfClasses);

        streamClasses(classesBySpec.get(BusinessSpecifications.DTO_INFO_RESOLVER), DtoInfoResolver.class).forEach(
                dtoInfoResolverClasses::add);
        sortByPriority(dtoInfoResolverClasses);
        LOGGER.debug("DTO info resolvers => {}", dtoInfoResolverClasses);

        // Add bindings for explicit assemblers
        bindings.putAll(associateInterfaceToImplementations(Assembler.class, assemblerClasses, false));
        overridingBindings.putAll(associateInterfaceToImplementations(Assembler.class, assemblerClasses, true));

        // Then add bindings for default assemblers
        bindingStrategies.addAll(new DefaultAssemblerCollector(getApplication(), bindings, defaultAssemblerClasses)
                .collect(dtoOfClasses)
        );

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new AssemblerModule(bindings, dtoInfoResolverClasses, bindingStrategies);
    }

    @Override
    public Object nativeOverridingUnitModule() {
        return new AssemblerOverridingModule(overridingBindings);
    }
}
