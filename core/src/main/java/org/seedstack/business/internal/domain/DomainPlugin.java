/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.kametic.specifications.Specification;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.internal.utils.PluginUtils;
import org.seedstack.business.spi.DomainProvider;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This plugins detects base building blocks of the business framework: aggregates, value objects,
 * repositories, factories, services, policies and finders. It also handles default repositories and
 * default factories.
 */
public class DomainPlugin extends AbstractSeedPlugin implements DomainProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainPlugin.class);

    private final Collection<Class<?>> aggregateClasses = new HashSet<>();
    private final Collection<Class<?>> entityClasses = new HashSet<>();
    private final Collection<Class<?>> valueObjectClasses = new HashSet<>();

    private final Collection<Class<?>> repositoryInterfaces = new HashSet<>();
    private final Collection<Class<? extends Repository>> defaultRepositoryClasses = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> repositorySpecs = new HashMap<>();

    private final Collection<Class<?>> factoryInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> factorySpecs = new HashMap<>();

    private final Collection<Class<?>> serviceInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> serviceSpecs = new HashMap<>();

    private final Collection<Class<?>> policyInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> policySpecs = new HashMap<>();

    private final Collection<Class<? extends IdentityGenerator>> identityGeneratorClasses = new HashSet<>();

    private final Collection<Class<? extends DomainEventHandler>> eventHandlerClasses = new HashSet<>();

    private final Map<Key<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Key<?>, Class<?>> overridingBindings = new HashMap<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

    @Override
    public String name() {
        return "business-domain";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        if (round.isFirst()) {
            return classpathScanRequestBuilder().specification(BusinessSpecifications.AGGREGATE_ROOT)
                    .specification(BusinessSpecifications.ENTITY)
                    .specification(BusinessSpecifications.VALUE_OBJECT)
                    .specification(BusinessSpecifications.REPOSITORY)
                    .specification(BusinessSpecifications.DEFAULT_REPOSITORY)
                    .specification(BusinessSpecifications.FACTORY)
                    .specification(BusinessSpecifications.IDENTITY_GENERATOR)
                    .specification(BusinessSpecifications.SERVICE)
                    .specification(BusinessSpecifications.POLICY)
                    .specification(BusinessSpecifications.DOMAIN_EVENT)
                    .specification(BusinessSpecifications.DOMAIN_EVENT_HANDLER)
                    .build();
        } else {
            ClasspathScanRequestBuilder classpathScanRequestBuilder = classpathScanRequestBuilder();
            repositorySpecs.putAll(PluginUtils.classpathRequestForDescendantTypesOf(classpathScanRequestBuilder,
                    repositoryInterfaces));
            factorySpecs.putAll(
                    PluginUtils.classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, factoryInterfaces));
            serviceSpecs.putAll(
                    PluginUtils.classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, serviceInterfaces));
            policySpecs.putAll(
                    PluginUtils.classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, policyInterfaces));
            return classpathScanRequestBuilder.build();
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        if (round.isFirst()) {
            // Scan interfaces
            BusinessUtils.streamClasses(initContext, BusinessSpecifications.AGGREGATE_ROOT, Object.class)
                    .forEach(aggregateClasses::add);
            LOGGER.debug("Aggregate roots => {}", aggregateClasses);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.ENTITY, Object.class)
                    .forEach(entityClasses::add);
            LOGGER.debug("Entities => {}", entityClasses);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.VALUE_OBJECT, Object.class)
                    .forEach(valueObjectClasses::add);
            LOGGER.debug("Value objects => {}", valueObjectClasses);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.REPOSITORY, Object.class)
                    .forEach(repositoryInterfaces::add);
            LOGGER.debug("Repositories => {}", repositoryInterfaces);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.FACTORY, Object.class)
                    .forEach(factoryInterfaces::add);
            LOGGER.debug("Factories => {}", factoryInterfaces);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.SERVICE, Object.class)
                    .forEach(serviceInterfaces::add);
            LOGGER.debug("Services => {}", serviceInterfaces);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.POLICY, Object.class)
                    .forEach(policyInterfaces::add);
            LOGGER.debug("Policies => {}", policyInterfaces);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.DEFAULT_REPOSITORY, Repository.class)
                    .forEach(defaultRepositoryClasses::add);
            LOGGER.debug("Default repositories => {}", defaultRepositoryClasses);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.DOMAIN_EVENT_HANDLER,
                    DomainEventHandler.class)
                    .forEach(eventHandlerClasses::add);
            LOGGER.debug("Domain event handlers => {}", eventHandlerClasses);

            BusinessUtils.streamClasses(initContext, BusinessSpecifications.IDENTITY_GENERATOR, IdentityGenerator.class)
                    .forEach(identityGeneratorClasses::add);
            LOGGER.debug("Identity generators => {}", identityGeneratorClasses);

            return InitState.NON_INITIALIZED;
        } else {
            // Then add bindings for explicit implementations
            bindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, repositoryInterfaces, repositorySpecs,
                            false));
            overridingBindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, repositoryInterfaces, repositorySpecs,
                            true));
            bindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, factoryInterfaces, factorySpecs,
                            false));
            overridingBindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, factoryInterfaces, factorySpecs,
                            true));
            bindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, serviceInterfaces, serviceSpecs,
                            false));
            overridingBindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, serviceInterfaces, serviceSpecs,
                            true));
            bindings.putAll(PluginUtils.associateInterfacesToImplementations(initContext, policyInterfaces, policySpecs,
                    false));
            overridingBindings.putAll(
                    PluginUtils.associateInterfacesToImplementations(initContext, policyInterfaces, policySpecs, true));

            // Then add bindings for default repositories
            bindingStrategies.addAll(new DefaultRepositoryCollector(defaultRepositoryClasses, getApplication()).collect(
                    aggregateClasses));

            // Then add bindings for default factories when no explicit factory has been defined
            bindingStrategies.addAll(
                    new DefaultFactoryCollector(bindings).collect(aggregateClasses, valueObjectClasses));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new DomainModule(bindings, bindingStrategies, identityGeneratorClasses, eventHandlerClasses);
    }

    @Override
    public Object nativeOverridingUnitModule() {
        return new DomainOverrideModule(overridingBindings);
    }

    @Override
    public Collection<Class<?>> aggregateRoots() {
        return Collections.unmodifiableCollection(aggregateClasses);
    }

    @Override
    public Collection<Class<?>> entities() {
        return Collections.unmodifiableCollection(entityClasses);
    }

    @Override
    public Collection<Class<?>> valueObjects() {
        return Collections.unmodifiableCollection(valueObjectClasses);
    }
}
