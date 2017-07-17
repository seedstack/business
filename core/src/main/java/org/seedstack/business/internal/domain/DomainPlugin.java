/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
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
import org.kametic.specifications.Specification;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainEventHandler;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.domain.ValueObject;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;
import static org.seedstack.business.internal.utils.PluginUtils.associateInterfaceToImplementations;
import static org.seedstack.business.internal.utils.PluginUtils.classpathRequestForDescendantTypesOf;

/**
 * This plugins detects base building blocks of the business framework: aggregates, value objects, repositories, factories,
 * services, policies and finders. It also handles default repositories and default factories.
 */
public class DomainPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainPlugin.class);
    private final Collection<Class<? extends AggregateRoot<?>>> aggregateClasses = new HashSet<>();
    private final Collection<Class<? extends ValueObject>> valueObjectClasses = new HashSet<>();

    private final Collection<Class<? extends Repository>> repositoryInterfaces = new HashSet<>();
    private final Collection<Class<? extends Repository>> defaultRepositoryClasses = new HashSet<>();
    private final Map<Class<? extends Repository>, Specification<? extends Class<? extends Repository>>> repositorySpecs = new HashMap<>();

    private final Collection<Class<? extends Factory>> factoryInterfaces = new HashSet<>();
    private final Map<Class<? extends Factory>, Specification<? extends Class<? extends Factory>>> factorySpecs = new HashMap<>();

    private final Collection<Class<?>> serviceInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> serviceSpecs = new HashMap<>();

    private final Collection<Class<?>> policyInterfaces = new HashSet<>();
    private final Map<Class<?>, Specification<? extends Class<?>>> policySpecs = new HashMap<>();

    private final Collection<Class<? extends IdentityGenerator>> identityGeneratorClasses = new HashSet<>();

    private final Collection<Class<? extends DomainEventHandler>> eventHandlerClasses = new HashSet<>();

    private final Map<Key<?>, Class<?>> bindings = new HashMap<>();
    private final Collection<BindingStrategy> bindingStrategies = new ArrayList<>();

    @Override
    public String name() {
        return "business-domain";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        if (round.isFirst()) {
            return classpathScanRequestBuilder()
                    .specification(BusinessSpecifications.AGGREGATE_ROOT)
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
            repositorySpecs.putAll(classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, repositoryInterfaces));
            factorySpecs.putAll(classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, factoryInterfaces));
            serviceSpecs.putAll(classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, serviceInterfaces));
            policySpecs.putAll(classpathRequestForDescendantTypesOf(classpathScanRequestBuilder, policyInterfaces));
            return classpathScanRequestBuilder.build();
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        // The first round is used to scan interfaces
        if (round.isFirst()) {
            streamClasses(initContext, BusinessSpecifications.AGGREGATE_ROOT, AggregateRoot.class).map(aggregateClass -> (Class<AggregateRoot<?>>) aggregateClass).forEach(aggregateClasses::add);
            LOGGER.debug("Aggregate roots => {}", aggregateClasses);

            streamClasses(initContext, BusinessSpecifications.VALUE_OBJECT, ValueObject.class).forEach(valueObjectClasses::add);
            LOGGER.debug("Value objects => {}", valueObjectClasses);

            streamClasses(initContext, BusinessSpecifications.REPOSITORY, Repository.class).forEach(repositoryInterfaces::add);
            LOGGER.debug("Repository interfaces => {}", repositoryInterfaces);

            streamClasses(initContext, BusinessSpecifications.DEFAULT_REPOSITORY, Repository.class).forEach(defaultRepositoryClasses::add);
            LOGGER.debug("Default repositories => {}", defaultRepositoryClasses);

            streamClasses(initContext, BusinessSpecifications.FACTORY, Factory.class).forEach(factoryInterfaces::add);
            LOGGER.debug("Factory interfaces => {}", factoryInterfaces);

            streamClasses(initContext, BusinessSpecifications.IDENTITY_GENERATOR, IdentityGenerator.class).forEach(identityGeneratorClasses::add);
            LOGGER.debug("Identity generator classes => {}", identityGeneratorClasses);

            streamClasses(initContext, BusinessSpecifications.SERVICE, Object.class).forEach(serviceInterfaces::add);
            LOGGER.debug("Service interfaces => {}", serviceInterfaces);

            streamClasses(initContext, BusinessSpecifications.POLICY, Object.class).forEach(policyInterfaces::add);
            LOGGER.debug("Policy interfaces => {}", policyInterfaces);

            streamClasses(initContext, BusinessSpecifications.DOMAIN_EVENT_HANDLER, DomainEventHandler.class).forEach(eventHandlerClasses::add);
            LOGGER.debug("Event handler => {}", eventHandlerClasses);

            return InitState.NON_INITIALIZED;
        } else {
            // The second round is used to scan implementations of the previously scanned interfaces
            bindings.putAll(associateInterfaceToImplementations(initContext, repositoryInterfaces, repositorySpecs));
            bindings.putAll(associateInterfaceToImplementations(initContext, factoryInterfaces, factorySpecs));
            bindings.putAll(associateInterfaceToImplementations(initContext, serviceInterfaces, serviceSpecs));
            bindings.putAll(associateInterfaceToImplementations(initContext, policyInterfaces, policySpecs));

            // Bindings for default repositories
            bindingStrategies.addAll(new DefaultRepositoryCollector(defaultRepositoryClasses, getApplication()).collect(aggregateClasses));

            // Bindings for default factories
            bindingStrategies.addAll(new DefaultFactoryCollector(bindings).collect(aggregateClasses, valueObjectClasses));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new DomainModule(bindings, bindingStrategies, identityGeneratorClasses, eventHandlerClasses);
    }
}
