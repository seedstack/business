/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import com.google.common.collect.Multimap;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.util.Types;
import java.lang.reflect.Type;
import java.util.Map;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.BindingUtils;
import org.seedstack.seed.core.internal.guice.GenericGuiceFactory;
import org.seedstack.seed.core.internal.guice.GenericGuiceProvider;

/**
 * FactoryPatternBindingStrategy resolves bindings for generic factories. <p> For instance, given the following
 * multimap: </p>
 * <pre>
 * Multimap&lt;Class&lt;?&gt;, Class&lt;?&gt;&gt; producedTypeMap = ArrayListMultimap.create();
 * // MyPolicy is the produced type
 * // MyPolicyImpl is the produced type implementation.
 * producedTypeMap.put(MyPolicy.class, MyPolicyImpl.class);
 * </pre>
 * <p> We use the multimap to create the strategy as follows. </p>
 * <pre>
 * new FactoryPatternBindingStrategy(producedTypeMap, Factory.class, FactoryInternal.class, new
 * ProviderFactory{@literal <Factory>}());
 * </pre>
 * This allows to inject a factory of {@code MyPolicy}.
 * <pre>
 * {@literal @}Inject
 * Factory&lt;MyPolicy&gt; f; &lt;= new FactoryInternal&lt;MyPolicy&gt;(MyPolicyImpl.class);
 * </pre>
 * The injected object will be a {@code FactoryInternal} of {@code MyPolicy} with {@code MyPolicyImpl.class} passed to
 * the constructor. Like this, the factory will be a factory of type {@code MyPolicy}, but all the further reflection
 * will be done on the implementation ({@code MyPolicyImpl.class}). <p> Notice that if {@code MyPolicyImpl} is
 * qualified, the factory injectee point should be also qualified. </p>
 */
class DefaultFactoryBindingStrategy<T> implements BindingStrategy {

  private static final Class<?> FACTORY_CLASS = GenericGuiceFactory.class;
  private final Class<T> injecteeClass;
  private final Class<? extends T> injectedClass;
  private final Multimap<Type, Class<?>> typeVariables;
  private final boolean bindGuiceFactory;

  /**
   * Constructors.
   *
   * @param injecteeClass    the class to bind
   * @param injectedClass    the implementation to bind with unresolved producedTypeMap
   * @param producedTypeMap  the map of produced type and produced type implementation
   * @param bindGuiceFactory allow to control the binding of the Guice assisted factory
   */
  DefaultFactoryBindingStrategy(Class<T> injecteeClass, Class<? extends T> injectedClass,
    Multimap<Type, Class<?>> producedTypeMap, boolean bindGuiceFactory) {
    this.injecteeClass = injecteeClass;
    this.injectedClass = injectedClass;
    this.typeVariables = producedTypeMap;
    this.bindGuiceFactory = bindGuiceFactory;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void resolve(Binder binder) {
    FactoryModuleBuilder guiceFactoryBuilder = new FactoryModuleBuilder();
    for (Map.Entry<Type, Class<?>> classes : typeVariables.entries()) {
      Type producedType = classes.getKey();
      Class<Object> producedImplementationType = (Class<Object>) classes.getValue();

      Key<Object> key = BindingUtils
        .resolveKey((Class<Object>) injecteeClass, producedImplementationType, producedType);
      Provider<Object> provider = new GenericGuiceProvider<>(injectedClass, producedImplementationType);
      binder.requestInjection(provider);
      binder.bind(key).toProvider(provider);
      guiceFactoryBuilder.implement(key, injectedClass);
    }

    // Assisted factory should not be bound twice
    if (bindGuiceFactory) {
      TypeLiteral<?> guiceAssistedFactory = TypeLiteral.get(Types.newParameterizedType(FACTORY_CLASS, injectedClass));
      binder.install(guiceFactoryBuilder.build(guiceAssistedFactory));
    }
  }
}
