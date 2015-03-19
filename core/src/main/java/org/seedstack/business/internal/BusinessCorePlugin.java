/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequestBuilder;
import io.nuun.kernel.core.AbstractPlugin;
import org.kametic.specifications.AbstractSpecification;
import org.kametic.specifications.Specification;
import org.seedstack.business.api.Producible;
import org.seedstack.business.api.application.GenericApplicationService;
import org.seedstack.business.api.application.annotations.ApplicationService;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.GenericDomainPolicy;
import org.seedstack.business.api.domain.GenericDomainService;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.domain.annotations.DomainFactory;
import org.seedstack.business.api.domain.annotations.DomainPolicy;
import org.seedstack.business.api.domain.annotations.DomainRepository;
import org.seedstack.business.api.domain.annotations.DomainRepositoryImpl;
import org.seedstack.business.api.domain.annotations.DomainService;
import org.seedstack.business.api.domain.meta.specifications.DomainSpecifications;
import org.seedstack.business.api.interfaces.GenericInterfacesService;
import org.seedstack.business.api.interfaces.annotations.InterfacesService;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.query.finder.Finder;
import org.seedstack.business.core.domain.FactoryInternal;
import org.seedstack.business.internal.strategy.BindingStrategy;
import org.seedstack.business.internal.strategy.FactoryPatternBindingStrategy;
import org.seedstack.business.internal.strategy.GenericBindingStrategy;
import org.seedstack.business.internal.strategy.ProviderFactory;
import org.seedstack.seed.core.utils.SeedBindingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * This plugin is a multi round plugin.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
@SuppressWarnings("unchecked")
public class BusinessCorePlugin extends AbstractPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCorePlugin.class);
    private static final String INTERFACE_REQUESTS = " interface requests : ";

    private final Specification<Class<?>> domainRepoSpec = and(DomainSpecifications.ancestorMetaAnnotatedWith(DomainRepository.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()));

    private Collection<Class<?>> repositoriesInterfaces;

    private final Specification<Class<?>> domainServiceSpec = and(DomainSpecifications.ancestorMetaAnnotatedWith(DomainService.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()), not(classIs(GenericDomainService.class)));

    private Collection<Class<?>> domainServiceInterfaces;

    private final Specification<Class<?>> applicationServiceSpec = and(DomainSpecifications.ancestorMetaAnnotatedWith(ApplicationService.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()), not(classIs(GenericApplicationService.class)));
    private Collection<Class<?>> applicationServiceInterfaces;

    private final Specification<Class<?>> finderServiceSpecs = and(DomainSpecifications.ancestorMetaAnnotatedWith(Finder.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()));

    private Collection<Class<?>> finderServiceInterfaces;

    private final Specification<Class<?>> policySpecs = and(DomainSpecifications.ancestorMetaAnnotatedWith(DomainPolicy.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()), not(classIs(GenericDomainPolicy.class)));

    private Collection<Class<?>> policyInterfaces;

    private final Specification<Class<?>> interfacesServiceSpecs = and(DomainSpecifications.ancestorMetaAnnotatedWith(InterfacesService.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()), not(classIs(GenericInterfacesService.class)));

    private Collection<Class<?>> interfacesServiceInterfaces;

    private final Specification<Class<?>> domainFactorySpecs = and(DomainSpecifications.ancestorMetaAnnotatedWith(DomainFactory.class),
            DomainSpecifications.classIsInterface(), not(DomainSpecifications.classIsAnnotation()));

    private Collection<Class<?>> domainFactoryInterfaces;

    private final Specification<Class<?>> assemblerSpecs = and(not(DomainSpecifications.classIsInterface()),
            not(DomainSpecifications.classIsAbstract()), descendantOf(Assembler.class));

    private Collection<Class<?>> assemblersClasses;

    private final Specification<Class<?>> aggregateRootSpec = DomainSpecifications.aggregateSpecification();
    private Collection<Class<?>> aggregateClasses;


    private final Specification<Class<?>> valueObjectSpec = DomainSpecifications.valueObjectSpecification();
    private Collection<Class<?>> valueObjectClasses;

    private final Specification<Class<?>> domainRepoImplSpec = and(classAnnotatedWith(DomainRepositoryImpl.class),
            not(DomainSpecifications.classIsInterface()), not(DomainSpecifications.classIsAbstract()), not(DomainSpecifications.classIsAnnotation()));

    private Collection<Class<?>> domainRepoImpls;

    private Map<Class<?>, Specification<Class<?>>> interfaceScanSpecification = new HashMap<Class<?>, Specification<Class<?>>>();

    private Collection<BindingStrategy> bindingStrategies = new ArrayList<BindingStrategy>();
    private Map<Key<?>, Class<?>> bindings = new HashMap<Key<?>, Class<?>>();

    @Override
    public String name() {
        return "seed-business-support";
    }

    @Override
    public Object nativeUnitModule() {
        return new BusinessModule(assemblersClasses, bindings, bindingStrategies);
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {

        if (roundEnvironment.firstRound()) {
            return classpathScanRequestBuilder().specification(aggregateRootSpec).specification(valueObjectSpec).specification(domainRepoImplSpec)
                    .specification(domainRepoSpec).specification(domainServiceSpec).specification(applicationServiceSpec)
                    .specification(interfacesServiceSpecs).specification(finderServiceSpecs).specification(policySpecs)
                    .specification(domainFactorySpecs).specification(assemblerSpecs).build();
        } else {
            // ROUND 2 ===========================
            return descendantTypesOf(repositoriesInterfaces, domainServiceInterfaces, applicationServiceInterfaces,
                    interfacesServiceInterfaces, finderServiceInterfaces, finderServiceInterfaces, policyInterfaces,
                    domainFactoryInterfaces).build();
        }
    }

    /**
     * Builds a ClasspathScanRequest to find all the descendant of the given interfaces.
     *
     * @param interfacesArgs the interfaces
     */
    private ClasspathScanRequestBuilder descendantTypesOf(Collection<Class<?>>... interfacesArgs) {
        ClasspathScanRequestBuilder classpathScanRequestBuilder = classpathScanRequestBuilder();
        for (Collection<Class<?>> interfaces : interfacesArgs) {
            for (Class<?> anInterface : interfaces) {
                LOGGER.debug(INTERFACE_REQUESTS + anInterface.getName());
                Specification<Class<?>> spec = and(descendantOf(anInterface), not(DomainSpecifications.classIsInterface()), not(DomainSpecifications.classIsAbstract()));
                classpathScanRequestBuilder = classpathScanRequestBuilder.specification(spec);
                interfaceScanSpecification.put(anInterface, spec);
            }
        }
        return classpathScanRequestBuilder;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public InitState init(InitContext initContext) {
        if (roundEnvironment.firstRound()) {
            LOGGER.debug(" ================================== ");
            // Domain Repositories
            Map<Specification, Collection<Class<?>>> scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            repositoriesInterfaces = scannedTypesBySpecification.get(domainRepoSpec);
            LOGGER.debug(" Repository Interface => {}", repositoriesInterfaces);

            // Domain Repository implementations
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            domainRepoImpls = scannedTypesBySpecification.get(domainRepoImplSpec);
            LOGGER.debug(" Domain repository default implementation => {}", domainRepoImpls);

            // Domain aggregate root
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            aggregateClasses = scannedTypesBySpecification.get(aggregateRootSpec);
            LOGGER.debug(" Aggregate roots => {}", aggregateClasses);

            // Domain value objects
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            valueObjectClasses = scannedTypesBySpecification.get(valueObjectSpec);
            LOGGER.debug(" Aggregate roots => {}", valueObjectClasses);

            // Domain Service Specs
            domainServiceInterfaces = scannedTypesBySpecification.get(domainServiceSpec);
            LOGGER.debug(" Domain Service Interface => {}", domainServiceInterfaces);

            // Application services specs
            applicationServiceInterfaces = scannedTypesBySpecification.get(applicationServiceSpec);
            LOGGER.debug(" Application Service Interface => {}", applicationServiceInterfaces);

            interfacesServiceInterfaces = scannedTypesBySpecification.get(interfacesServiceSpecs);
            LOGGER.debug(" Interfaces Service Interface => {}", interfacesServiceInterfaces);

            // Finders specs
            finderServiceInterfaces = scannedTypesBySpecification.get(finderServiceSpecs);
            LOGGER.debug(" Finder Interface => {}", finderServiceInterfaces);

            LOGGER.debug(" Finder Interface cleans ");
            LOGGER.debug(" Finder Interface clean => {}", finderServiceInterfaces);

            // Policy specs
            policyInterfaces = scannedTypesBySpecification.get(policySpecs);
            LOGGER.debug(" Policy Interface => {}", policyInterfaces);

            // Factories specs
            domainFactoryInterfaces = scannedTypesBySpecification.get(domainFactorySpecs);
            LOGGER.debug(" Factory Interface => {}", domainFactoryInterfaces);

            // Assemblers
            assemblersClasses = scannedTypesBySpecification.get(assemblerSpecs);
            LOGGER.debug("Assembler class => {}", assemblersClasses);


            return InitState.NON_INITIALIZED;
        } else {
            // ROUND 2

            // Classic bindings
            List<Collection<Class<?>>> collections = Lists.newArrayList(domainServiceInterfaces, applicationServiceInterfaces,
                    interfacesServiceInterfaces, finderServiceInterfaces, policyInterfaces, domainFactoryInterfaces, repositoriesInterfaces);
            for (Collection<Class<?>> interfaces : collections) {
                bindings.putAll(buildBindings(initContext, interfaces));
            }

            // Bindings for auto repositories
            bindingStrategies = buildDefaultRepositoryBindings();

            // Bindings for auto factories
            BindingStrategy factoryStrategy = buildAggregateDefaultFactoryBindings();
            if (factoryStrategy != null) {
                bindingStrategies.add(factoryStrategy);
            }
            bindingStrategies.add(buildDefaultFactoryBindings());


            return InitState.INITIALIZED;
        }
    }

    private Map<Key<?>, Class<?>> buildBindings(InitContext initContext, Collection<Class<?>> interfaces) {
        Map<Key<?>, Class<?>> keyMap = new HashMap<Key<?>, Class<?>>();
        for (Class<?> anInterface : interfaces) {
            Collection<Class<?>> subTypes = initContext.scannedTypesBySpecification().get(interfaceScanSpecification.get(anInterface));
            keyMap.putAll(SeedBindingUtils.resolveBindingDefinitions(anInterface, subTypes));
        }
        return keyMap;
    }

    private BindingStrategy buildAggregateDefaultFactoryBindings() {
        Collection<Class<?>[]> generics = new ArrayList<Class<?>[]>();
        if (aggregateClasses != null && !aggregateClasses.isEmpty()) {
            for (Class<?> aggregateClass : aggregateClasses) {
                generics.add(new Class<?>[]{aggregateClass});
            }
        }
        if (valueObjectClasses != null && !valueObjectClasses.isEmpty()) {
            for (Class<?> valueObjectClass : valueObjectClasses) {
                generics.add(new Class<?>[]{valueObjectClass});
            }
        }
        if (!generics.isEmpty()) {
            return new GenericBindingStrategy(generics, Factory.class, FactoryInternal.class, new ProviderFactory<Factory>());
        }
        return null;
    }


    private BindingStrategy buildDefaultFactoryBindings() {
        Specification<Class<?>> creatable = and(descendantOf(Producible.class), descendantOf(DomainObject.class));
        // iterate on all the domain element to bind
        Multimap<Type, Class<?>> defaultFactoryToBind = ArrayListMultimap.create();
        for (Map.Entry<Key<?>, Class<?>> keyClassEntry : bindings.entrySet()) {
            // filter on those which are creatable by a domain factory
            if (creatable.isSatisfiedBy(keyClassEntry.getKey().getTypeLiteral().getRawType())) {
                defaultFactoryToBind.put(keyClassEntry.getKey().getTypeLiteral().getType(), keyClassEntry.getValue());
            }
        }
        return new FactoryPatternBindingStrategy(defaultFactoryToBind, Factory.class, FactoryInternal.class, new ProviderFactory<Factory>());
    }

    @SuppressWarnings("rawtypes")
    private Collection<BindingStrategy> buildDefaultRepositoryBindings() {
        Collection<BindingStrategy> bindingStrategies = new ArrayList<BindingStrategy>();
        Collection<Class<?>[]> generics = new ArrayList<Class<?>[]>();
        for (Class<?> aggregateClass : aggregateClasses) {
            Class<?> keyType = TypeToken.of(aggregateClass).resolveType(AggregateRoot.class.getTypeParameters()[0]).getRawType();
            generics.add(new Class<?>[]{aggregateClass, keyType});
        }
        for (Class<?> domainRepoImpl : domainRepoImpls) {
            bindingStrategies.add(new GenericBindingStrategy(generics, Repository.class, domainRepoImpl, new ProviderFactory<Repository>()));
        }
        return bindingStrategies;
    }

    protected Specification<Class<?>> classIs(final Class<?> attendee) {
        return new AbstractSpecification<Class<?>>() {

            @Override
            public boolean isSatisfiedBy(Class<?> candidate) {

                return candidate != null && candidate.equals(attendee);
            }

        };
    }
}
