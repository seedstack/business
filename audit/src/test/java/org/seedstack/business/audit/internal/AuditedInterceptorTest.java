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
 * Creation : 24 juil. 2014
 */
package org.seedstack.business.audit.internal;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.seedstack.business.audit.api.AuditService;
import org.seedstack.business.audit.api.TrailExceptionHandler;
import org.seedstack.business.audit.api.annotations.Audited;
import org.seedstack.business.audit.internal.AuditedInterceptor;
import org.seedstack.seed.el.api.ELContextBuilder;
import org.seedstack.seed.el.api.ELContextBuilder.ELPropertyProvider;
import org.seedstack.seed.el.api.ELService;

public class AuditedInterceptorTest {

    private AuditedInterceptor underTest;

    private AuditService auditService;

    private Set<TrailExceptionHandler> exceptionHandlers;

    private ELService elService;

    private ELContextBuilder elContextBuilder;

    @Before
    public void before() {
        underTest = new AuditedInterceptor();
        auditService = mock(AuditService.class);
        exceptionHandlers = new HashSet<TrailExceptionHandler>();
        elService = mock(ELService.class, RETURNS_MOCKS);
        elContextBuilder = mock(ELContextBuilder.class, RETURNS_MOCKS);

        Whitebox.setInternalState(underTest, "auditService", auditService);
        Whitebox.setInternalState(underTest, "exceptionHandlers", exceptionHandlers);
        Whitebox.setInternalState(underTest, "elService", elService);
        Whitebox.setInternalState(underTest, "elContextBuilder", elContextBuilder);
    }

    @Test
    public void invoke_nominal() throws Throwable {
        MethodInvocation mi = mock(MethodInvocation.class);
        when(mi.getMethod()).thenReturn(SomeClass.class.getMethod("someMethod"));
        when(mi.proceed()).thenReturn(new Object());

        underTest.invoke(mi);
    }

    public void mockElService() {
        ELPropertyProvider propertyProvider = mock(ELPropertyProvider.class);
        when(elContextBuilder.defaultContext()).thenReturn(propertyProvider);
        when(propertyProvider.withProperty(anyString(), any(Object.class))).thenReturn(propertyProvider);

    }

    public static class SomeClass {

        @Audited(messageAfter = "after")
        public String someMethod() {
            return "foo";
        }
    }
}
