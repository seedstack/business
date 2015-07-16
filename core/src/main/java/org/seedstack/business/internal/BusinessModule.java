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

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.internal.datasecurity.BusinessDataSecurityModule;
import org.seedstack.business.internal.assembler.dsl.FluentAssemblerImpl;
import org.seedstack.business.internal.assembler.dsl.InternalRegistry;
import org.seedstack.business.internal.assembler.dsl.InternalRegistryInternal;
import org.seedstack.business.internal.strategy.api.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The business module.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 * @author redouane.loulou@ext.mpsa.com
 */
@BusinessConcern
@SuppressWarnings({ "unchecked", "rawtypes" })
class BusinessModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(BusinessModule.class);

	private final Map<Key<?>, Class<?>> bindings;

	private final Collection<Class<?>> assemblersClasses;

	private Collection<BindingStrategy> bindingStrategies;

    /**
	 * Constructor.
	 * 
	 * @param assemblersClasses
	 *            the collection of assembler classes
	 * @param bindings
	 *            the map of interface and class to bind
	 * @param bindingStrategies
	 *            the collection of binding strategy
	 */
	public BusinessModule(Collection<Class<?>> assemblersClasses, Map<Key<?>, Class<?>> bindings,
			Collection<BindingStrategy> bindingStrategies) {
		this.assemblersClasses = assemblersClasses;
		this.bindings = bindings;
		this.bindingStrategies = bindingStrategies;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void configure() {
        bind(FluentAssembler.class).to(FluentAssemblerImpl.class);
        bind(InternalRegistry.class).to(InternalRegistryInternal.class);

		for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
			logger.trace("Binding {} to {}.", binding.getKey(), binding.getValue().getSimpleName());
			bind(binding.getKey()).to((Class) binding.getValue());
		}

		// Bind assembler implementations
		for (Class<?> assembler : assemblersClasses) {
			bind(assembler);
		}

        // Bind strategies
		for (BindingStrategy bindingStrategy : bindingStrategies) {
			bindingStrategy.resolve(binder());
		}

		install(new BusinessDataSecurityModule());
	}

}
