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

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.core.AbstractPlugin;
import org.seedstack.business.DomainSpecifications;

import java.util.Collection;

/**
 * Plugin used for identity management, scan all classes that implements IdentityHandler
 * 
 */
public class IdentityPlugin extends AbstractPlugin {

    private Collection<Class<?>> identityHandlerClasses;

	@Override
	public String name() {
		return "business-identity";
	}

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder().specification(DomainSpecifications.IDENTITY_HANDLER).build();
    }

	@Override
	@SuppressWarnings("rawtypes")
	public InitState init(InitContext initContext) {
		identityHandlerClasses = initContext.scannedTypesBySpecification().get(DomainSpecifications.IDENTITY_HANDLER);
		return InitState.INITIALIZED;
	}

	@Override
	public Object nativeUnitModule() {
		return new IdentityModule(identityHandlerClasses);
	}
}
