/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.internal.identity;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.seedstack.business.domain.GenericFactory;
import org.seedstack.business.domain.Create;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.domain.identity.IdentityService;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 
 */
class IdentityModule extends AbstractModule {

	private Collection<Class<?>> identityHandlerClasses;

	/**
     * Constructor.
     *
	 * @param identityHandlerClasses the collection if identity handler classes
	 */
	IdentityModule(Collection<Class<?>> identityHandlerClasses) {
		this.identityHandlerClasses = identityHandlerClasses;
	}

	@Override
	protected void configure() {
		bindIdentityHandler();
		bind(IdentityService.class).to(IdentityServiceInternal.class);
		IdentityInterceptor identityInterceptor = new IdentityInterceptor();

		requestInjection(identityInterceptor);
		bindInterceptor(Matchers.subclassesOf(GenericFactory.class), factoryMethods(), identityInterceptor);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void bindIdentityHandler() {
		for (Class identityHandlerClass : identityHandlerClasses) {
			bind(identityHandlerClass);
			if (identityHandlerClass.isAnnotationPresent(Named.class)) {
				Named name = (Named) identityHandlerClass.getAnnotation(Named.class);
				bind(getIdentityHandlerInterface(identityHandlerClass))
                        .annotatedWith(Names.named(name.value())).to(identityHandlerClass);
			}
		}
	}

    /**
     * Go through the given class and its superclass to find an interface assignable to IdentityHandler.
     *
     * @param identityHandlerClass the identityHandler class to check
     * @return the interface found
     */
	private Class<?> getIdentityHandlerInterface(Class<?> identityHandlerClass) {
		for (Class<?> inter : identityHandlerClass.getInterfaces()) {
			if (IdentityHandler.class.isAssignableFrom(inter)) {
				return inter;
			}
		}
		if(identityHandlerClass.getSuperclass() !=null){
			return getIdentityHandlerInterface(identityHandlerClass.getSuperclass());
		}
		throw new IllegalArgumentException("parameter should have a superClass");
	}

	Matcher<Method> factoryMethods() {

		return new AbstractMatcher<Method>() {

			@Override
			public boolean matches(Method candidate) {

                return GenericFactory.class.isAssignableFrom(candidate.getDeclaringClass())
                        && (SeedReflectionUtils.getMetaAnnotationFromAncestors(candidate, Create.class) != null
                        || SeedReflectionUtils.getMetaAnnotationFromAncestors(candidate.getDeclaringClass(), Create.class) != null);
			}

		};
	}
}
