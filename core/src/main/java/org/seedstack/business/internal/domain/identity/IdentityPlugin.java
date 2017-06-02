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
package org.seedstack.business.internal.domain.identity;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;

/**
 * Plugin handling identity generation.
 */
public class IdentityPlugin extends AbstractSeedPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityPlugin.class);
    private final Collection<Class<? extends IdentityHandler>> identityHandlerClasses = new HashSet<>();

    @Override
    public String name() {
        return "business-identity";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .specification(BusinessSpecifications.IDENTITY_HANDLER)
                .build();
    }

    @Override
    public InitState initialize(InitContext initContext) {
        streamClasses(initContext, BusinessSpecifications.IDENTITY_HANDLER, IdentityHandler.class).forEach(identityHandlerClasses::add);
        LOGGER.debug("Identity handler classes => {}", identityHandlerClasses);
        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new IdentityModule(identityHandlerClasses);
    }
}
