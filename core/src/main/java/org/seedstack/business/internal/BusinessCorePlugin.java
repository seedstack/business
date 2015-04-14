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
import org.javatuples.Tuple;
import org.kametic.specifications.Specification;
import org.seedstack.business.api.Producible;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.core.domain.FactoryInternal;
import org.seedstack.business.core.interfaces.AutomaticAssembler;
import org.seedstack.business.core.interfaces.AutomaticTupleAssembler;
import org.seedstack.business.core.interfaces.DefaultAssembler;
import org.seedstack.business.core.interfaces.DefaultTupleAssembler;
import org.seedstack.business.helpers.Tuples;
import org.seedstack.business.internal.strategy.FactoryPatternBindingStrategy;
import org.seedstack.business.internal.strategy.GenericBindingStrategy;
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.seedstack.business.internal.strategy.api.ProviderFactory;
import org.seedstack.seed.core.utils.SeedBindingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static org.seedstack.business.api.domain.meta.specifications.BaseClassSpecifications.classIsAbstract;
import static org.seedstack.business.api.domain.meta.specifications.BaseClassSpecifications.classIsInterface;
import static org.seedstack.business.api.domain.meta.specifications.DomainSpecifications.*;

/**
 * This plugin is a multi round plugin.
 * <p/>
 * It uses two round because it needs to scan user interfaces, for instance those annotated with {@code @Finder}.
 * Then in the second round, it scan the implementations of the scanned interfaces.
 * <p/>
 * This plugin also bind default implementation for repository, factory and assembler. For this, it uses the
 * {@link org.seedstack.business.internal.strategy.api.BindingStrategy}.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
@SuppressWarnings("unchecked")
public class BusinessCorePlugin extends AbstractPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCorePlugin.class);
    private static final String INTERFACE_REQUESTS = " interface requests : ";

    private Collection<Class<?>> repositoriesInterfaces;
    private Collection<Class<?>> domainServiceInterfaces;
    private Collection<Class<?>> applicationServiceInterfaces;
    private Collection<Class<?>> finderServiceInterfaces;
    private Collection<Class<?>> policyInterfaces;
    private Collection<Class<?>> interfacesServiceInterfaces;
    private Collection<Class<?>> domainFactoryInterfaces;
    private Collection<Class<?>> assemblersClasses;
    private Collection<Class<?>> aggregateClasses;
    private Collection<Class<?>> valueObjectClasses;
    private Collection<Class<?>> domainRepoImpls;
    private Map<Type, Class<?>> linksForDefaultAssemblers = new HashMap<Type, Class<?>>();
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
            return classpathScanRequestBuilder()
                    .specification(aggregateRootSpecification)
                    .specification(valueObjectSpecification)
                    .specification(domainRepoImplSpecification)
                    .specification(domainRepoSpecification)
                    .specification(domainServiceSpecification)
                    .specification(applicationServiceSpecification)
                    .specification(interfacesServiceSpecification)
                    .specification(finderServiceSpecification)
                    .specification(policySpecification)
                    .specification(domainFactorySpecification)
                    .specification(assemblerSpecification)
                    .specification(dtoWithDefaultAssemblerSpecification).build();
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
                Specification<Class<?>> spec = and(descendantOf(anInterface), not(classIsInterface()), not(classIsAbstract()));
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
            repositoriesInterfaces = scannedTypesBySpecification.get(domainRepoSpecification);
            LOGGER.debug(" Repository Interface => {}", repositoriesInterfaces);

            // Domain Repository implementations
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            domainRepoImpls = scannedTypesBySpecification.get(domainRepoImplSpecification);
            LOGGER.debug(" Domain repository default implementation => {}", domainRepoImpls);

            // Domain aggregate root
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            aggregateClasses = scannedTypesBySpecification.get(aggregateRootSpecification);
            LOGGER.debug(" Aggregate roots => {}", aggregateClasses);

            // Domain value objects
            scannedTypesBySpecification = initContext.scannedTypesBySpecification();
            valueObjectClasses = scannedTypesBySpecification.get(valueObjectSpecification);
            LOGGER.debug(" Aggregate roots => {}", valueObjectClasses);

            // Domain Service Specs
            domainServiceInterfaces = scannedTypesBySpecification.get(domainServiceSpecification);
            LOGGER.debug(" Domain Service Interface => {}", domainServiceInterfaces);

            // Application services specs
            applicationServiceInterfaces = scannedTypesBySpecification.get(applicationServiceSpecification);
            LOGGER.debug(" Application Service Interface => {}", applicationServiceInterfaces);

            interfacesServiceInterfaces = scannedTypesBySpecification.get(interfacesServiceSpecification);
            LOGGER.debug(" Interfaces Service Interface => {}", interfacesServiceInterfaces);

            // Finders specs
            finderServiceInterfaces = scannedTypesBySpecification.get(finderServiceSpecification);
            LOGGER.debug(" Finder Interface => {}", finderServiceInterfaces);

            LOGGER.debug(" Finder Interface cleans ");
            LOGGER.debug(" Finder Interface clean => {}", finderServiceInterfaces);

            // Policy specs
            policyInterfaces = scannedTypesBySpecification.get(policySpecification);
            LOGGER.debug(" Policy Interface => {}", policyInterfaces);

            // Factories specs
            domainFactoryInterfaces = scannedTypesBySpecification.get(domainFactorySpecification);
            LOGGER.debug(" Factory Interface => {}", domainFactoryInterfaces);

            // Assemblers
            assemblersClasses = scannedTypesBySpecification.get(assemblerSpecification);
            LOGGER.debug("Assembler class => {}", assemblersClasses);

            // default assembler
            Collection<Class<?>> dtoWithDefaultAssemblerClasses = scannedTypesBySpecification.get(dtoWithDefaultAssemblerSpecification);
            for (Class<?> dtoWithDefaultAssemblerClass : dtoWithDefaultAssemblerClasses) {
                DtoOf dtoOf = dtoWithDefaultAssemblerClass.getAnnotation(DtoOf.class);
                if (dtoOf.value().length == 1) {
                    linksForDefaultAssemblers.put(dtoOf.value()[0], dtoWithDefaultAssemblerClass);
                } else if (dtoOf.value().length > 1) {
                    linksForDefaultAssemblers.put(Tuples.typeOfTuple(dtoOf.value()), dtoWithDefaultAssemblerClass);
                }
            }

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

            bindingStrategies.addAll(buildDefaultAssemblerBindings(linksForDefaultAssemblers));

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
        // this method support multiple default implementation for repository (one for each persistence technology).

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

    private Collection<BindingStrategy> buildDefaultAssemblerBindings(Map<Type, Class<?>> linksForDefaultAssemblers) {
        Set<Type[]> autoAssemblerGenerics = new HashSet<Type[]>();
        Set<Type[]> autoTupleAssemblerGenerics = new HashSet<Type[]>();
        for (Map.Entry<Type, Class<?>> entry : linksForDefaultAssemblers.entrySet()) {
            if (entry.getKey() instanceof ParameterizedType && Tuple.class.isAssignableFrom((Class) ((ParameterizedType) entry.getKey()).getRawType())) {
                autoTupleAssemblerGenerics.add(new Type[]{entry.getKey(), entry.getValue()});
            } else {
                autoAssemblerGenerics.add(new Type[]{entry.getKey(), entry.getValue()});
            }
        }

        Collection<BindingStrategy> bs = new ArrayList<BindingStrategy>();
        bs.add(new GenericBindingStrategy(autoAssemblerGenerics, AutomaticAssembler.class, DefaultAssembler.class, new ProviderFactory<AutomaticAssembler>()));
        bs.add(new GenericBindingStrategy(autoTupleAssemblerGenerics, AutomaticTupleAssembler.class, DefaultTupleAssembler.class, new ProviderFactory<AutomaticAssembler>()));
        return bs;
    }
}
