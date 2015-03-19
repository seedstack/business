/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.internal.identity;

import org.seedstack.business.api.domain.identity.IdentityHandler;
import io.nuun.kernel.api.Plugin;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.core.AbstractPlugin;
import org.kametic.specifications.Specification;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.seedstack.business.api.domain.meta.specifications.DomainSpecifications.classIsAbstract;
import static org.seedstack.business.api.domain.meta.specifications.DomainSpecifications.classIsInterface;

/**
 * Plugin used for identity management, scan all classes that implements IdentityHandler
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
public class IdentityPlugin extends AbstractPlugin {

    @SuppressWarnings("unchecked")
	private final Specification<Class<?>> IdentityHandlerSpecs = and(not(classIsInterface()), not(classIsAbstract()), descendantOf(IdentityHandler.class));

    private Collection<Class<?>> identityHandlerClasses;

	@Override
	public String name() {
		return "seed-business-identity";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public InitState init(InitContext initContext) {
		Map<Specification, Collection<Class<?>>> scannedTypesBySpecification = initContext.scannedTypesBySpecification();
		identityHandlerClasses = scannedTypesBySpecification.get(IdentityHandlerSpecs);
		return InitState.INITIALIZED;
	}

	@Override
	public Collection<ClasspathScanRequest> classpathScanRequests() {
		return classpathScanRequestBuilder().specification(IdentityHandlerSpecs).build();
	}

	@Override
	public Object nativeUnitModule() {
		return new IdentityModule(identityHandlerClasses);
	}

	@Override
	public Collection<Class<? extends Plugin>> dependentPlugins() {
		Collection<Class<? extends Plugin>> plugins = new ArrayList<Class<? extends Plugin>>();
		plugins.add(ApplicationPlugin.class);
		return plugins;
	}
}
