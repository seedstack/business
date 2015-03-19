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
import org.seedstack.business.internal.domain.repository.GenericImplementationFactory;
import org.seedstack.seed.core.utils.SeedBindingUtils;

import java.util.Collection;

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

    private final Collection<Class<?>[]> typeVariables;

    private final Class<?> injecteeClass;

    private final Class<?> genericImplClass;

    private final ProviderFactory<?> providerHandler;

    /**
     * Constructors.
     *
     * @param typeVariables    the collection of resolved typeVariables
     * @param injecteeClass    the class to bind
     * @param genericImplClass the implementation to bind with unresolved typeVariables
     * @param providerFactory  the provider factory
     */
    public GenericBindingStrategy(Collection<Class<?>[]> typeVariables, Class<?> injecteeClass, Class<?> genericImplClass,
                                  ProviderFactory<?> providerFactory) {
        this.typeVariables = typeVariables;
        this.injecteeClass = injecteeClass;
        this.genericImplClass = genericImplClass;
        this.providerHandler = providerFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resolve(Binder binder, BindingContext bindingContext) {
        FactoryModuleBuilder factoryBuilder = new FactoryModuleBuilder();
        for (Class[] classes : typeVariables) {
            Key<?> key = SeedBindingUtils.resolveKey(injecteeClass, genericImplClass, classes);
            if (!bindingContext.isExcluded(key.getTypeLiteral())) {
                Provider<?> provider = providerHandler.createProvider(genericImplClass, classes);
                binder.requestInjection(provider);
                binder.bind(key).toProvider((Provider) provider);
                factoryBuilder.implement(key, (Class) genericImplClass);
                bindingContext.bound(key);
            }
        }
        TypeLiteral<?> factoryInterface = TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, genericImplClass));
        if (!bindingContext.isExcluded(factoryInterface)) {
            binder.install(factoryBuilder.build(factoryInterface));
            bindingContext.excluded(Key.get(factoryInterface));
        }
    }
}
