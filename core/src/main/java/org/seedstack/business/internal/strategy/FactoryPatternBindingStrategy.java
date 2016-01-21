/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
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
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.seedstack.business.internal.utils.BindingUtils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * FactoryPatternBindingStrategy resolves bindings for generic factories.
 * <p>
 * For instance, given the following multimap:
 * </p>
 * <pre>
 * Multimap&lt;Class&lt;?&gt;, Class&lt;?&gt;&gt; producedTypeMap = ArrayListMultimap.create();
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
 * Factory&lt;MyPolicy&gt; f; &lt;= new FactoryInternal&lt;MyPolicy&gt;(MyPolicyImpl.class);
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
public class FactoryPatternBindingStrategy<T> implements BindingStrategy {

    private static final Class<?> FACTORY_CLASS = GenericGuiceFactory.class;

    private final Multimap<Type, Class<?>> typeVariables;

    private final Class<?> injecteeClass;

    private final Class<?> injectedClass;
    private final boolean bindAssistedFactory;

    /**
     * Constructors.
     *
     * @param injecteeClass   the class to bind
     * @param injectedClass   the implementation to bind with unresolved producedTypeMap
     * @param producedTypeMap the map of produced type and produced type implementation
     */
    public FactoryPatternBindingStrategy(Class<?> injecteeClass, Class<?> injectedClass,
                                         Multimap<Type, Class<?>> producedTypeMap) {
        this.typeVariables = producedTypeMap;
        this.injecteeClass = injecteeClass;
        this.injectedClass = injectedClass;
        this.bindAssistedFactory = true;
    }

    /**
     * Constructors.
     *
     * @param injecteeClass       the class to bind
     * @param injectedClass       the implementation to bind with unresolved producedTypeMap
     * @param producedTypeMap     the map of produced type and produced type implementation
     * @param bindAssistedFactory allow to control the binding of the Guice assisted factory
     */
    public FactoryPatternBindingStrategy(Class<?> injecteeClass, Class<?> injectedClass,
                                         Multimap<Type, Class<?>> producedTypeMap, boolean bindAssistedFactory) {
        this.typeVariables = producedTypeMap;
        this.injecteeClass = injecteeClass;
        this.injectedClass = injectedClass;
        this.bindAssistedFactory = bindAssistedFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resolve(Binder binder) {
        FactoryModuleBuilder guiceFactoryBuilder = new FactoryModuleBuilder();
        for (Map.Entry<Type, Class<?>> classes : typeVariables.entries()) {
            Type producedType = classes.getKey();
            Class<?> producedImplementationType = classes.getValue();

            Key<?> key = BindingUtils.resolveKey(injecteeClass, producedImplementationType, producedType);
            Provider<?> provider = new GenericGuiceProvider<T>(injectedClass, producedImplementationType);
            binder.requestInjection(provider);
            binder.bind(key).toProvider((Provider) provider);
            guiceFactoryBuilder.implement(key, (Class) injectedClass);
        }

        // Assisted factory should not be bound twice
        if (bindAssistedFactory) {
            TypeLiteral<?> guiceAssistedFactory = TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, injectedClass));
            binder.install(guiceFactoryBuilder.build(guiceAssistedFactory));
        }
    }
}
