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

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.internal.datasecurity.BusinessDataSecurityModule;
import org.seedstack.business.internal.strategy.BindingContext;
import org.seedstack.business.internal.strategy.BindingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

    private BindingContext bindingContext;

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
		for (Entry<Key<?>, Class<?>> binding : bindings.entrySet()) {
			logger.trace("binding {} to {}.", binding.getKey(), binding.getValue().getSimpleName());
			bind(binding.getKey()).to((Class) binding.getValue());
		}

		// Bind assembler implementations
		for (Class<?> assembler : assemblersClasses) {
			bind(assembler);
		}

		resolveBindingStrategies();

		// TODO AOP for post treatment on entities
		// client sub classes of Factories and Repositories
        // TODO control over good and bad injection inside Domain objects

		install(new BusinessDataSecurityModule());
	}

	private void resolveBindingStrategies() {
        // The bound types are included in the context, so they will be ignored by the strategy.
        // For instance if a Repository<Customer, CustomerId> was already bound to a user's repository,
        // no default repository will be added.
        // On the other side, the keys bound by the strategy wont be added in the context so that all the default
        // repositories will be bound.
        bindingContext = new BindingContext();
        bindingContext.bound(bindings.keySet()).excluded(bindings.keySet());
		for (BindingStrategy bindingStrategy : bindingStrategies) {
			bindingStrategy.resolve(binder(), bindingContext);
		}
    }

	/**
	 * @return the set of assembler classes
	 */
	@Provides
	@Singleton
	@Named("assemblersTypes")
	public Set<Class> assemblers() {
		Set<Class> assemblerSet = new HashSet<Class>();
		assemblerSet.addAll(assemblersClasses);
		return assemblerSet;
	}

	/**
	 * @return the set of repository classes
	 */
	@Provides
	@Singleton
	@Named("repositoriesTypes")
	public Set<Key> repositories() {
		Set<Key> repositories = Sets.newHashSet();
		// Note: Default repositories keys are added as they are typeLiteral of GenericRepository!
		repositories.addAll(bindingContext.boundForClass(Repository.class));
        return repositories;
    }

	/**
	 * @return the set of factory classes
	 */
	@Provides
	@Singleton
	@Named("factoriesTypes")
	public Set<Key<?>> factories() {
		Set<Key<?>> factories = Sets.newHashSet();
		for (Key candidateInterface : bindings.keySet()) {
			Class interfaceClass = candidateInterface.getTypeLiteral().getRawType();
			if (!GenericFactory.class.equals((Class<?>)interfaceClass) && GenericFactory.class.isAssignableFrom(interfaceClass)) {
				factories.add(candidateInterface);
			}
			// Note:
			// Default Factories keys are not added because they're not typeLiteral of GenericFactory but Factory.
			// Cause a duplicate problem in factories for assemblers.
			// Unique use case due to GenericFactory having no method to implements
		}
		return factories;
	}

}
