/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.strategy.api;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

import javax.inject.Inject;
import java.lang.reflect.Type;

/**
 * This class is a generic provider.
 *
 * It passes an array of object to the provided object constructor. This array contains the generic types of
 * the created object. It will balance the fact that we won't be able to use reflection to get the generic type
 * on the created object.
 * 
 * @author pierre.thirouin@ext.mpsa.com
 * @param <T> Type to get from the generic provider.  
 */
public class GenericImplementationProvider<T> implements Provider<T> {

	private final Class<?> defaultImplClass;

	Type[] genericClasses;

	@Inject
	private Injector injector;

	/**
	 * Constructs a provider for a default repository of a given aggregate.
	 * 
	 * @param defaultImplClass
	 *            the default implementation class
	 * @param genericClasses
	 *            generic array classes
	 */
	public GenericImplementationProvider(Class<?> defaultImplClass, Type... genericClasses) {
		this.defaultImplClass = defaultImplClass;
		this.genericClasses = genericClasses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		Key<?> factoryKey = Key.get(TypeLiteral.get(Types.newParameterizedType(GenericImplementationFactory.class,
				defaultImplClass)));
		GenericImplementationFactory<T> genericImplementationFactory = (GenericImplementationFactory<T>) injector
				.getInstance(factoryKey);
		return genericImplementationFactory.createResolvedInstance(genericClasses);
	}
}
