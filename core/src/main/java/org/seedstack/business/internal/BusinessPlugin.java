/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import org.kametic.specifications.Specification;
import org.seedstack.business.BusinessSpecifications;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.BindingUtils;
import org.seedstack.seed.core.internal.utils.SpecificationBuilder;
import org.seedstack.seed.spi.config.ApplicationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.seedstack.business.internal.utils.BusinessUtils.convertClassCollection;
import static org.seedstack.shed.reflect.ClassPredicates.classIs;
import static org.seedstack.shed.reflect.ClassPredicates.classIsDescendantOf;
import static org.seedstack.shed.reflect.ClassPredicates.classIsInterface;
import static org.seedstack.shed.reflect.ClassPredicates.classModifierIs;

/**
 * This plugin is a multi round plugin.
 * <p>
 * It uses two round because it needs to scan user interfaces, for instance those annotated with {@code @Finder}.
 * Then in the second round, it scan the implementations of the scanned interfaces.
 * </p>
 * This plugin also bind default implementation for repository, factory and assembler. For this, it uses the
 * {@link BindingStrategy}.
 */
public class BusinessPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessPlugin.class);

    private Collection<Class<?>> aggregateClasses;
    private Collection<Class<?>> valueObjectClasses;
    private Collection<Class<?>> repositoriesInterfaces;
    private Collection<Class<?>> domainFactoryInterfaces;
    private Collection<Class<?>> serviceInterfaces;
    private Collection<Class<?>> policyInterfaces;
    private Collection<Class<?>> assemblersClasses;
    private Collection<Class<?>> finderServiceInterfaces;

    private Collection<Class<? extends Assembler>> defaultAssemblerClasses;
    private Collection<Class<? extends Repository>> defaultRepositoryClasses;

    private Map<Class<?>, Specification<Class<?>>> specsByInterfaceMap = new HashMap<>();
    private Collection<BindingStrategy> bindingStrategies = new ArrayList<>();
    private Map<Key<?>, Class<?>> bindings = new HashMap<>();

    private Application application;

    private static final Specification<Class<?>> FACTORY_SPEC = BusinessSpecifications.FACTORY.and(
            new SpecificationBuilder<>(classIs(Factory.class).negate()).build()
    );

    @Override
    public String name() {
        return "business-core";
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        if (round.isFirst()) {
            return classpathScanRequestBuilder()
                    .specification(BusinessSpecifications.AGGREGATE_ROOT)
                    .specification(BusinessSpecifications.CLASSIC_ASSEMBLER)
                    .specification(BusinessSpecifications.SERVICE)
                    .specification(FACTORY_SPEC)
                    .specification(BusinessSpecifications.FINDER)
                    .specification(BusinessSpecifications.POLICY)
                    .specification(BusinessSpecifications.REPOSITORY)
                    .specification(BusinessSpecifications.VALUE_OBJECT)

                    .specification(BusinessSpecifications.DEFAULT_ASSEMBLER)
                    .specification(BusinessSpecifications.DEFAULT_REPOSITORY)
                    .specification(BusinessSpecifications.DTO_OF).build();
        } else {
            return classpathRequestForDescendantTypesOf(
                    domainFactoryInterfaces,
                    serviceInterfaces,
                    finderServiceInterfaces,
                    finderServiceInterfaces,
                    policyInterfaces,
                    repositoriesInterfaces).build();
        }
    }

    /**
     * Builds a ClasspathScanRequest to find all the descendant of the given interfaces.
     *
     * @param interfacesArgs the interfaces
     */
    @SuppressWarnings("unchecked")
    private ClasspathScanRequestBuilder classpathRequestForDescendantTypesOf(Collection<Class<?>>... interfacesArgs) {
        ClasspathScanRequestBuilder classpathScanRequestBuilder = classpathScanRequestBuilder();
        for (Collection<Class<?>> interfaces : interfacesArgs) {
            for (Class<?> anInterface : interfaces) {
                LOGGER.trace("Request implementations of: {}", anInterface.getName());
                Specification<Class<?>> spec = new SpecificationBuilder<>(classIsDescendantOf(anInterface).and(classIsInterface().negate()).and(classModifierIs(Modifier.ABSTRACT).negate())).build();
                classpathScanRequestBuilder = classpathScanRequestBuilder.specification(spec);
                specsByInterfaceMap.put(anInterface, spec);
            }
        }
        return classpathScanRequestBuilder;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public InitState initialize(InitContext initContext) {
        Map<Specification, Collection<Class<?>>> spec = initContext.scannedTypesBySpecification();

        // The first round is used to scan interfaces
        if (round.isFirst()) {
            application = initContext.dependency(ApplicationProvider.class).getApplication();

            aggregateClasses = spec.get(BusinessSpecifications.AGGREGATE_ROOT);
            LOGGER.debug("Aggregate root(s) => {}", aggregateClasses);

            assemblersClasses = spec.get(BusinessSpecifications.CLASSIC_ASSEMBLER);
            LOGGER.debug("Assembler class(es) => {}", assemblersClasses);

            domainFactoryInterfaces = spec.get(FACTORY_SPEC);
            LOGGER.debug("Factory Interface(s) => {}", domainFactoryInterfaces);

            serviceInterfaces = spec.get(BusinessSpecifications.SERVICE);
            LOGGER.debug("Domain Service Interface(s) => {}", serviceInterfaces);

            finderServiceInterfaces = spec.get(BusinessSpecifications.FINDER);
            LOGGER.debug("Finder Interface(s) => {}", finderServiceInterfaces);

            policyInterfaces = spec.get(BusinessSpecifications.POLICY);
            LOGGER.debug("Policy Interface(s) => {}", policyInterfaces);

            repositoriesInterfaces = spec.get(BusinessSpecifications.REPOSITORY);
            LOGGER.debug("Repository Interface(s) => {}", repositoriesInterfaces);

            valueObjectClasses = spec.get(BusinessSpecifications.VALUE_OBJECT);
            LOGGER.debug("Value object(s) => {}", valueObjectClasses);

            // Default implementations
            defaultRepositoryClasses = convertClassCollection(Repository.class, spec.get(BusinessSpecifications.DEFAULT_REPOSITORY));
            LOGGER.debug("Default repositories => {}", defaultRepositoryClasses);

            defaultAssemblerClasses = convertClassCollection(Assembler.class, spec.get(BusinessSpecifications.DEFAULT_ASSEMBLER));
            LOGGER.debug("Default assembler(s) => {}", defaultAssemblerClasses);

            return InitState.NON_INITIALIZED;
        } else {
            // The second round is used to scan implementations of the previously scanned interfaces

            // Classic bindings
            // -- add assemblers to the default mode even if they have no client user interfaces
            List<Class<?>> assemblerClass = new ArrayList<>();
            assemblerClass.add(Assembler.class);
            specsByInterfaceMap.put(Assembler.class, BusinessSpecifications.CLASSIC_ASSEMBLER);

            List<Collection<Class<?>>> collections = Lists.newArrayList(
                    domainFactoryInterfaces,
                    serviceInterfaces,
                    finderServiceInterfaces,
                    policyInterfaces,
                    repositoriesInterfaces,
                    assemblerClass
            );
            for (Collection<Class<?>> interfaces : collections) {
                bindings.putAll(associatesInterfaceToImplementations(initContext, interfaces));
            }

            // Bindings for default repositories

            bindingStrategies = new DefaultRepositoryCollector(aggregateClasses, defaultRepositoryClasses, application).collect();

            // Bindings for default factories
            Collection<Class<?>> aggregateOrVOClasses = new ArrayList<>();
            aggregateOrVOClasses.addAll(aggregateClasses);
            aggregateOrVOClasses.addAll(valueObjectClasses);
            bindingStrategies.addAll(new DefaultFactoryCollector(aggregateOrVOClasses, bindings).collect());

            // Bindings for default assemblers
            Collection<Class<?>> dtoWithDefaultAssemblerClasses = spec.get(BusinessSpecifications.DTO_OF);
            bindingStrategies.addAll(new DefaultAssemblerCollector(defaultAssemblerClasses).collect(dtoWithDefaultAssemblerClasses));

            return InitState.INITIALIZED;
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new BusinessModule(assemblersClasses, bindings, bindingStrategies);
    }

    /**
     * Associates scanned interfaces to their implementations. It also handles qualified bindings in the case where
     * there is multiple implementation for the same interface.
     * <p>
     * This is the "default mode" for binding in the business framework.
     * </p>
     *
     * @param initContext the context containing the implementations
     * @param interfaces  the interfaces to bind
     * @return the map of interface/implementation to bind
     * @see BindingUtils#resolveBindingDefinitions(Class, Class, Class[])
     */
    @SuppressWarnings("unchecked")
    private Map<Key<?>, Class<?>> associatesInterfaceToImplementations(InitContext initContext, Collection<Class<?>> interfaces) {
        Map<Key<?>, Class<?>> keyMap = new HashMap<>();
        for (Class<?> anInterface : interfaces) {
            Collection<Class<?>> subTypes = initContext.scannedTypesBySpecification().get(specsByInterfaceMap.get(anInterface));
            keyMap.putAll(BindingUtils.resolveBindingDefinitions((Class<Object>) anInterface, subTypes));
        }
        return keyMap;
    }
}
