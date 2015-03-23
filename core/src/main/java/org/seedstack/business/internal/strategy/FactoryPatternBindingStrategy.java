/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.strategy;

import com.google.common.collect.Multimap;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.util.Types;
import org.seedstack.business.internal.strategy.api.BindingContext;
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.seedstack.business.internal.strategy.api.GenericImplementationFactory;
import org.seedstack.business.internal.strategy.api.ProviderFactory;
import org.seedstack.seed.core.utils.SeedBindingUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * FactoryPatternBindingStrategy resolves bindings for generic factories.
 * <p>
 * For instance, given the following multimap:
 * </p>
 * <pre>
 * Multimap{@literal <Class<?>, Class<?>>} producedTypeMap = ArrayListMultimap.create();
 * // MyPolicy is the produced type
 * // MyPolicyImpl is the produced type implementation.
 * producedTypeMap.put(MyPolicy.class, MyPolicyImpl.class);
 * </pre>
 * <p>
 * We use the multimap to create the strategy as follows.
 * </p>
 * <pre>
 * new FactoryPatternBindingStrategy(producedTypeMap, Factory.class, FactoryInternal.class, new ProviderFactory{@literal <Factory>}());
 * </pre>
 * This allows to inject a factory of {@code MyPolicy}.
 * <pre>
 * {@literal @}Inject
 * Factory{@literal <MyPolicy>} f; <= new FactoryInternal{@literal <MyPolicy>}(MyPolicyImpl.class);
 * </pre>
 * The injected object will be a {@code FactoryInternal} of {@code MyPolicy}
 * with {@code MyPolicyImpl.class} passed to the constructor. Like this, the factory will be a factory of type {@code MyPolicy},
 * but all the further reflection will be done on the implementation ({@code MyPolicyImpl.class}).
 * <p>
 * Notice that if {@code MyPolicyImpl} where qualified, the factory injectee point should be also qualified.
 * </p>
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
public class FactoryPatternBindingStrategy implements BindingStrategy {

    private static final Class<?> FACTORY_CLASS = GenericImplementationFactory.class;

    private final Multimap<Type, Class<?>> typeVariables;

    private final Class<?> injecteeClass;

    private final Class<?> injectedClass;

    private final ProviderFactory<?> providerFactory;

    /**
     * Constructors.
     *
     * @param producedTypeMap the map of produced type and produced type implementation
     * @param injecteeClass   the class to bind
     * @param injectedClass   the implementation to bind with unresolved producedTypeMap
     * @param providerFactory the provider factory
     */
    public FactoryPatternBindingStrategy(Multimap<Type, Class<?>> producedTypeMap, Class<?> injecteeClass, Class<?> injectedClass,
                                         ProviderFactory<?> providerFactory) {
        this.typeVariables = producedTypeMap;
        this.injecteeClass = injecteeClass;
        this.injectedClass = injectedClass;
        this.providerFactory = providerFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resolve(Binder binder, BindingContext bindingContext) {
        FactoryModuleBuilder factoryBuilder = new FactoryModuleBuilder();
        for (Map.Entry<Type, Class<?>> classes : typeVariables.entries()) {
            Type producedType = classes.getKey();
            Class<?> producedImplementationType = classes.getValue();

            Key<?> key = SeedBindingUtils.resolveKey(injecteeClass, producedImplementationType, producedType);
            if (!bindingContext.isExcluded(key.getTypeLiteral())) {
                Provider<?> provider = providerFactory.createProvider(injectedClass, producedImplementationType);
                binder.requestInjection(provider);
                binder.bind(key).toProvider((Provider) provider);
                factoryBuilder.implement(key, (Class) injectedClass);
                bindingContext.bound(key);
            }
        }
        TypeLiteral<?> factoryInterface = TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, injectedClass));
        if (!bindingContext.isExcluded(factoryInterface)) {
            binder.install(factoryBuilder.build(factoryInterface));
            bindingContext.excluded(Key.get(factoryInterface));
        }
    }
}
