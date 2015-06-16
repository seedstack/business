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
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.seedstack.business.internal.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

/**
 * GenericBindingStrategy resolves bindings for generic classes to implementations with unresolved type variables.
 * <p/>
 * For instance it is possible to bind the following classes:
 * <pre>
 * class MyClass&lt;I, J&gt; { }
 *
 * class MyImplClass&lt;I,J&gt; extends MyClass { }
 * </pre>
 * For all the possible type variables (for instance for all the aggregate with their key).
 * <pre>
 * Collection&lt;Class&lt;?&gt;[]&gt; typeVariables = Lists.newArrayList();
 * typeVariables.add(new Object[]{MyAggregate1.class, MyKey1.class});
 * typeVariables.add(new Object[]{MyAggregate2.class, MyKey2.class});
 *
 * GenericBindingStrategy bindingStrategy = new GenericBindingStrategy(typeVariables, MyClass.class,
 *         MyImplClass.class, new ProviderFactory&lt;MyClass&gt;());
 * </pre>
 * This will allow to inject as follows:
 * <pre>
 * {@literal @}Inject
 * MyClass&lt;MyAggregate1, MyKey1&gt; mySuperClass; // inject instance of MyImplClass&lt;MyAggregate1, MyKey1&gt;
 * </pre>
 *
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 */
public class GenericBindingStrategy<T> implements BindingStrategy {

    /**
     * This class is the generic Guice assisted factory.
     */
    private static final Class<?> DEFAULT_IMPL_FACTORY_CLASS = GenericGuiceFactory.class;

    private final Class<?> injecteeClass;
    private final Class<?> genericImplClass;

    private Collection<Class<?>[]> typeVariableClasses;
    private Collection<Type[]> typeVariables;


    /**
     * Constructors.
     *
     * @param injecteeClass       the class to bind
     * @param genericImplClass    the implementation to bind with unresolved typeVariables
     * @param typeVariableClasses the collection of resolved typeVariables
     */
    public GenericBindingStrategy(Class<T> injecteeClass, Class<? extends T> genericImplClass, Collection<Class<?>[]> typeVariableClasses) {
        this.typeVariableClasses = typeVariableClasses;
        this.injecteeClass = injecteeClass;
        this.genericImplClass = genericImplClass;
    }

    /**
     * Constructors.
     *
     * @param injecteeClass    the class to bind
     * @param genericImplClass the implementation to bind with unresolved typeVariables
     * @param typeVariables    the collection of resolved typeVariables
     */
    public GenericBindingStrategy(Class<?> injecteeClass, Class<?> genericImplClass, Set<Type[]> typeVariables) {
        this.typeVariables = typeVariables;
        this.injecteeClass = injecteeClass;
        this.genericImplClass = genericImplClass;
    }

    @Override
    public void resolve(Binder binder) {
        // Bind all the possible types for one class or interface.
        // For instance: Repository<Customer,String>, Repository<Order, Long>, etc.
        FactoryModuleBuilder guiceFactoryBuilder = new FactoryModuleBuilder();
        if (typeVariableClasses != null) {
            for (Class[] classes : typeVariableClasses) {
                bindKey(binder, guiceFactoryBuilder, classes);
            }
        } else {
            for (Type[] typeVariable : typeVariables) {
                bindKey(binder, guiceFactoryBuilder, typeVariable);
            }
        }

        TypeLiteral<?> guiceAssistedFactory = TypeLiteral.get(Types.newParameterizedType(DEFAULT_IMPL_FACTORY_CLASS, genericImplClass));
        binder.install(guiceFactoryBuilder.build(guiceAssistedFactory));
    }

    @SuppressWarnings("unchecked")
    private void bindKey(Binder binder, FactoryModuleBuilder factoryBuilder, Type[] classes) {
        // Get the key to bind
        Key<?> key = BindingUtils.resolveKey(injecteeClass, genericImplClass, classes);

        // Prepare the Guice provider
        Provider<?> provider = new GenericGuiceProvider<T>(genericImplClass, classes);
        binder.requestInjection(provider);
        binder.bind(key).toProvider((Provider) provider);

        // Prepare the factory for assisted injection
        factoryBuilder.implement(key, (Class) genericImplClass);
    }
}
