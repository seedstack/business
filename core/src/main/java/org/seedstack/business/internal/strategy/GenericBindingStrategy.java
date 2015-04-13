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
import java.util.Collection;
import java.util.Set;

/**
 * GenericBindingStrategy resolves bindings for generic classes to implementations with unresolved type variables.
 * <p/>
 * For instance it is possible to bind the following classes:
 * <pre>
 * class MyClass{@literal <I, J>} { }
 *
 * class MyImplClass{@literal <I,J>} extends MyClass { }
 * </pre>
 * For all the possible type variables (for instance for all the aggregate with their key).
 * <pre>
 * Collection{@literal <Class<?>[]>} typeVariables = Lists.newArrayList();
 * typeVariables.add(new Object[]{MyAggregate1.class, MyKey1.class});
 * typeVariables.add(new Object[]{MyAggregate2.class, MyKey2.class});
 *
 * GenericBindingStrategy bindingStrategy = new GenericBindingStrategy(typeVariables, MyClass.class,
 *         MyImplClass.class, new ProviderFactory<MyClass>());
 * </pre>
 * This will allow to inject as follows:
 * <pre>
 * {@literal @}Inject
 * MyClass{@literal <MyAggregate1, MyKey1>} mySuperClass; // inject instance of MyImplClass{@literal <MyAggregate1, MyKey1>}
 * </pre>
 *
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public class GenericBindingStrategy implements BindingStrategy {

    private static final Class<?> FACTORY_CLASS = GenericImplementationFactory.class;

    private Collection<Class<?>[]> typeVariableClasses;
    private Collection<Type[]> typeVariables;
    private final Class<?> injecteeClass;

    private final Class<?> genericImplClass;

    private final ProviderFactory<?> providerFactory;

    /**
     * Constructors.
     *
     * @param typeVariableClasses    the collection of resolved typeVariables
     * @param injecteeClass    the class to bind
     * @param genericImplClass the implementation to bind with unresolved typeVariables
     * @param providerFactory  the provider factory
     */
    public GenericBindingStrategy(Collection<Class<?>[]> typeVariableClasses, Class<?> injecteeClass, Class<?> genericImplClass,
                                  ProviderFactory<?> providerFactory) {
        this.typeVariableClasses = typeVariableClasses;
        this.injecteeClass = injecteeClass;
        this.genericImplClass = genericImplClass;
        this.providerFactory = providerFactory;
    }

    /**
     * Constructors.
     *
     * @param typeVariables    the collection of resolved typeVariables
     * @param injecteeClass    the class to bind
     * @param genericImplClass the implementation to bind with unresolved typeVariables
     * @param providerFactory  the provider factory
     */
    public GenericBindingStrategy(Set<Type[]> typeVariables, Class<?> injecteeClass, Class<?> genericImplClass,
                                  ProviderFactory<?> providerFactory) {
        this.typeVariables = typeVariables;
        this.injecteeClass = injecteeClass;
        this.genericImplClass = genericImplClass;
        this.providerFactory = providerFactory;
    }

    @Override
    public void resolve(Binder binder, BindingContext bindingContext) {
        // Bind all the possible types for one class or interface.
        // For instance: Repository<Customer,String>, Repository<Order, Long>, etc.
        FactoryModuleBuilder factoryBuilder = new FactoryModuleBuilder();
        if (typeVariableClasses != null) {
            for (Class[] classes : typeVariableClasses) {
                bindKey(binder, bindingContext, factoryBuilder, classes);
            }
        } else {
            for (Type[] typeVariable : typeVariables) {
                bindKey(binder, bindingContext, factoryBuilder, typeVariable);
            }
        }
        TypeLiteral<?> factoryInterface = TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, genericImplClass));
        if (!bindingContext.isExcluded(factoryInterface)) {
            // Install the factory once
            binder.install(factoryBuilder.build(factoryInterface));
            // Now that the class is bound, add it on the exclusion list
            bindingContext.excluded(Key.get(factoryInterface));
        }
    }

    @SuppressWarnings("unchecked")
    private void bindKey(Binder binder, BindingContext bindingContext, FactoryModuleBuilder factoryBuilder, Type[] classes) {
        // Get the key to bind
        Key<?> key = SeedBindingUtils.resolveKey(injecteeClass, genericImplClass, classes);

        // check for exclusion, for instance if the key is already bound
        if (!bindingContext.isExcluded(key.getTypeLiteral())) {
            // Prepare the Guice provider
            Provider<?> provider = providerFactory.createProvider(genericImplClass, classes);
            binder.requestInjection(provider);
            binder.bind(key).toProvider((Provider) provider);

            // Prepare the factory for assisted injection
            factoryBuilder.implement(key, (Class) genericImplClass);

            // bind the key
            bindingContext.bound(key);
        }
    }
}
