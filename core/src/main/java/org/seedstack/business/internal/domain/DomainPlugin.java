/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;
import static org.seedstack.business.internal.utils.PluginUtils.associateInterfacesToImplementations;

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
import java.util.stream.Collectors;
import org.kametic.specifications.Specification;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.DomainEventInterceptor;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessSpecifications;
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
    
    private final Collection<Class<? extends DomainEventInterceptor>> domainInterceptorClasses = new HashSet<>();    

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
                    .specification(BusinessSpecifications.DOMAIN_EVENT_INTERCEPTOR)
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
            Map<Specification, Collection<Class<?>>> classesBySpec = initContext.scannedTypesBySpecification();

            // Scan interfaces
            streamClasses(classesBySpec.get(BusinessSpecifications.AGGREGATE_ROOT), Object.class)
                    .forEach(aggregateClasses::add);
            LOGGER.debug("Aggregate roots => {}", aggregateClasses);

            streamClasses(classesBySpec.get(BusinessSpecifications.ENTITY), Object.class)
                    .forEach(entityClasses::add);
            LOGGER.debug("Entities => {}", entityClasses);

            streamClasses(classesBySpec.get(BusinessSpecifications.VALUE_OBJECT), Object.class)
                    .forEach(valueObjectClasses::add);
            LOGGER.debug("Value objects => {}", valueObjectClasses);

            streamClasses(classesBySpec.get(BusinessSpecifications.REPOSITORY), Object.class)
                    .forEach(repositoryInterfaces::add);
            LOGGER.debug("Repositories => {}", repositoryInterfaces);

            streamClasses(classesBySpec.get(BusinessSpecifications.FACTORY), Object.class)
                    .forEach(factoryInterfaces::add);
            LOGGER.debug("Factories => {}", factoryInterfaces);

            streamClasses(classesBySpec.get(BusinessSpecifications.SERVICE), Object.class)
                    .forEach(serviceInterfaces::add);
            LOGGER.debug("Services => {}", serviceInterfaces);

            streamClasses(classesBySpec.get(BusinessSpecifications.POLICY), Object.class)
                    .forEach(policyInterfaces::add);
            LOGGER.debug("Policies => {}", policyInterfaces);

            streamClasses(classesBySpec.get(BusinessSpecifications.DEFAULT_REPOSITORY), Repository.class)
                    .forEach(defaultRepositoryClasses::add);
            LOGGER.debug("Default repositories => {}", defaultRepositoryClasses);

            streamClasses(classesBySpec.get(BusinessSpecifications.DOMAIN_EVENT_HANDLER),
                    DomainEventHandler.class)
                    .forEach(eventHandlerClasses::add);
            LOGGER.debug("Domain event handlers => {}", eventHandlerClasses);
            
            streamClasses(classesBySpec.get(BusinessSpecifications.DOMAIN_EVENT_INTERCEPTOR),
                    DomainEventInterceptor.class)
                    .forEach(domainInterceptorClasses::add);
            LOGGER.debug("Domain event interceptors => {}", domainInterceptorClasses);
            
            streamClasses(classesBySpec.get(BusinessSpecifications.IDENTITY_GENERATOR),
                    IdentityGenerator.class)
                    .forEach(identityGeneratorClasses::add);
            LOGGER.debug("Identity generators => {}", identityGeneratorClasses);

            return InitState.NON_INITIALIZED;
        } else {
            // Then add bindings for explicit implementations
            bindings.putAll(
                    associateInterfacesToImplementations(initContext, repositoryInterfaces, repositorySpecs,
                            false));
            overridingBindings.putAll(
                    associateInterfacesToImplementations(initContext, repositoryInterfaces, repositorySpecs,
                            true));
            bindings.putAll(
                    associateInterfacesToImplementations(initContext, factoryInterfaces, factorySpecs,
                            false));
            overridingBindings.putAll(
                    associateInterfacesToImplementations(initContext, factoryInterfaces, factorySpecs,
                            true));
            bindings.putAll(
                    associateInterfacesToImplementations(initContext, serviceInterfaces, serviceSpecs,
                            false));
            overridingBindings.putAll(
                    associateInterfacesToImplementations(initContext, serviceInterfaces, serviceSpecs,
                            true));
            bindings.putAll(associateInterfacesToImplementations(initContext, policyInterfaces, policySpecs,
                    false));
            overridingBindings.putAll(
                    associateInterfacesToImplementations(initContext, policyInterfaces, policySpecs, true));

            // Then add bindings for default repositories
            DefaultRepositoryCollector defaultRepositoryCollector = new DefaultRepositoryCollector(
                    getApplication(),
                    bindings,
                    defaultRepositoryClasses
            );
            bindingStrategies.addAll(defaultRepositoryCollector.collectFromAggregates(
                    streamClasses(aggregateClasses, AggregateRoot.class).collect(Collectors.toSet())));
            bindingStrategies.addAll(defaultRepositoryCollector.collectFromInterfaces(
                    streamClasses(repositoryInterfaces, Repository.class).collect(Collectors.toSet())));

            // Then add bindings for default factories when no explicit factory has been defined
            DefaultFactoryCollector defaultFactoryCollector = new DefaultFactoryCollector(bindings);
            bindingStrategies.addAll(defaultFactoryCollector.collect(aggregateClasses, valueObjectClasses));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new DomainModule(bindings, bindingStrategies, identityGeneratorClasses,
                domainInterceptorClasses, eventHandlerClasses);
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

    @Override
    public Collection<Class<?>> repositories() {
        return Collections.unmodifiableCollection(repositoryInterfaces);
    }

    @Override
    public Collection<Class<?>> factories() {
        return Collections.unmodifiableCollection(factoryInterfaces);
    }

    @Override
    public Collection<Class<?>> services() {
        return Collections.unmodifiableCollection(serviceInterfaces);
    }

    @Override
    public Collection<Class<?>> policies() {
        return Collections.unmodifiableCollection(policyInterfaces);
    }
}
