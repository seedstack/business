/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 23 juil. 2014
 */
package org.seedstack.business.audit.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.nuun.kernel.api.Plugin;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.seedstack.business.audit.internal.AuditPlugin;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;

public class AuditPluginTest {

    @Test
    public void testPlugin() {
        AuditPlugin underTest = new AuditPlugin();

        assertThat(underTest.name()).isNotNull();
        assertThat(underTest.classpathScanRequests()).isNotNull();
        assertThat(underTest.nativeUnitModule()).isNotNull();
        assertThat(underTest.requiredPlugins()).isNotNull();

        InitContext iniContext = mock(InitContext.class, RETURNS_MOCKS);
        Collection plugins = new ArrayList();
        Plugin app = mock(ApplicationPlugin.class, RETURNS_MOCKS);
        plugins.add(app);
        when(iniContext.pluginsRequired()).thenReturn(plugins);
        InitState state = underTest.init(iniContext);
        assertThat(state).isEqualTo(InitState.INITIALIZED);
    }
}
