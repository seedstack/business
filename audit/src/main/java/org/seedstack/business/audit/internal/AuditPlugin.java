/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.internal;

import io.nuun.kernel.api.Plugin;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.nuun.kernel.core.AbstractPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.seedstack.business.audit.api.TrailExceptionHandler;
import org.seedstack.business.audit.spi.TrailWriter;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;

/**
 * Plugin for audit
 * 
 * @author U236838
 */
public class AuditPlugin extends AbstractPlugin {

    private Map<Class<?>, Collection<Class<?>>> scannedClasses;

    private AuditConfigurer auditConfigurer;

    public static final String PROPERTIES_PREFIX = "org.seedstack.business.audit";

    @Override
    public String name() {
        return "seed-business-audit";
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder().descendentTypeOf(TrailWriter.class).descendentTypeOf(TrailExceptionHandler.class).build();
    }

    @Override
    public InitState init(InitContext initContext) {
        scannedClasses = initContext.scannedSubTypesByAncestorClass();
        ApplicationPlugin applicationPlugin = (ApplicationPlugin) initContext.pluginsRequired().iterator().next();
        auditConfigurer = new AuditConfigurer(applicationPlugin.getApplication().getConfiguration().subset(PROPERTIES_PREFIX), scannedClasses);
        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new AuditModule(auditConfigurer);
    }

    @Override
    public Collection<Class<? extends Plugin>> requiredPlugins() {
        Collection<Class<? extends Plugin>> requiredPlugins = new ArrayList<Class<? extends Plugin>>();
        requiredPlugins.add(ApplicationPlugin.class);
        return requiredPlugins;
    }

}
