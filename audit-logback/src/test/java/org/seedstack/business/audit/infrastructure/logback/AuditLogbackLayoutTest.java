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
package org.seedstack.business.audit.infrastructure.logback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.seed.el.api.ELContextBuilder;
import org.seedstack.seed.el.api.ELService;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class AuditLogbackLayoutTest {

    private AuditLogbackLayout underTest;

    private ELService elService;

    private ELContextBuilder elContextBuilder;

    private String elExpression;

    @Before
    public void before() {
        underTest = new AuditLogbackLayout();
        elService = mock(ELService.class, RETURNS_MOCKS);
        elContextBuilder = mock(ELContextBuilder.class, RETURNS_MOCKS);
        Whitebox.setInternalState(underTest, "elService", elService);
        Whitebox.setInternalState(underTest, "elContextBuilder", elContextBuilder);
        Whitebox.setInternalState(underTest, "elExpression", elExpression);
    }

    @Test
    public void testLayout() {
        elExpression = "test";
        ILoggingEvent event = mock(ILoggingEvent.class);
        AuditEvent auditEvent = mock(AuditEvent.class, RETURNS_MOCKS);
        when(event.getArgumentArray()).thenReturn(new Object[] { auditEvent });
        String result = underTest.doLayout(event);
        assertThat(result).isNotEmpty();
    }
}
