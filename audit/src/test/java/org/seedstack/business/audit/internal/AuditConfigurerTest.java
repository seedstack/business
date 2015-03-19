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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.business.audit.api.TrailExceptionHandler;
import org.seedstack.business.audit.internal.AuditConfigurer;
import org.seedstack.business.audit.internal.SecurityTrailExceptionHandler;
import org.seedstack.business.audit.spi.TrailWriter;

public class AuditConfigurerTest {

    private AuditConfigurer underTest;

    private Configuration configuration;

    private Map<Class<?>, Collection<Class<?>>> auditClasses;

    @Before
    public void before() {
        configuration = mock(Configuration.class);
        auditClasses = new HashMap<Class<?>, Collection<Class<?>>>();
        underTest = new AuditConfigurer(configuration, auditClasses);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findTrailWriters_nominal() {
        when(configuration.getStringArray("writers")).thenReturn(
                new String[] { DummyTrailWriter.class.getSimpleName(), DummyTrailWriter2.class.getName() });
        Collection<Class<?>> trailWriterClasses = new ArrayList<Class<?>>();
        trailWriterClasses.add(DummyTrailWriter.class);
        trailWriterClasses.add(DummyTrailWriter2.class);
        auditClasses.put(TrailWriter.class, trailWriterClasses);

        Set<Class<? extends TrailWriter>> result = underTest.findTrailWriters();

        assertThat(result).isNotEmpty();
        assertThat(result).containsOnly(DummyTrailWriter.class, DummyTrailWriter2.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findTrailWriters_none_defined() {
        when(configuration.getStringArray("writers")).thenReturn(new String[0]);
        Collection<Class<?>> trailWriterClasses = new ArrayList<Class<?>>();
        trailWriterClasses.add(DummyTrailWriter.class);
        auditClasses.put(TrailWriter.class, trailWriterClasses);

        Set<Class<? extends TrailWriter>> result = underTest.findTrailWriters();

        assertThat(result).isEmpty();
    }

    @Test
    public void findTrailWriters_defined_trailers_dont_exist() {
        when(configuration.getStringArray("writers")).thenReturn(new String[] { "titi", "toto" });
        Collection<Class<?>> trailWriterClasses = new ArrayList<Class<?>>();
        trailWriterClasses.add(DummyTrailWriter.class);
        trailWriterClasses.add(DummyTrailWriter2.class);
        auditClasses.put(TrailWriter.class, trailWriterClasses);

        Set<Class<? extends TrailWriter>> result = underTest.findTrailWriters();

        assertThat(result).isEmpty();
    }

    @Test
    public void findTrailExceptionHandlers_nominal() {
        when(configuration.getStringArray("exceptionHandlers")).thenReturn(
                new String[] { DummyExceptionHandler.class.getSimpleName(), DummyExceptionHandler2.class.getName() });
        Collection<Class<?>> trailExceptionHandlerClasses = new ArrayList<Class<?>>();
        trailExceptionHandlerClasses.add(SecurityTrailExceptionHandler.class);
        trailExceptionHandlerClasses.add(DummyExceptionHandler.class);
        trailExceptionHandlerClasses.add(DummyExceptionHandler2.class);
        auditClasses.put(TrailExceptionHandler.class, trailExceptionHandlerClasses);

        Set<Class<? extends TrailExceptionHandler>> result = underTest.findTrailExceptionHandlers();

        assertThat(result).isNotEmpty();
        assertThat(result).containsOnly(DummyExceptionHandler.class, DummyExceptionHandler2.class);
    }

    @Test
    public void findTrailExceptionHandlers_no_handler_defined() {
        when(configuration.getStringArray("exceptionHandlers")).thenReturn(new String[0]);
        Collection<Class<?>> trailExceptionHandlerClasses = new ArrayList<Class<?>>();
        trailExceptionHandlerClasses.add(SecurityTrailExceptionHandler.class);
        trailExceptionHandlerClasses.add(DummyExceptionHandler.class);
        trailExceptionHandlerClasses.add(DummyExceptionHandler2.class);
        auditClasses.put(TrailExceptionHandler.class, trailExceptionHandlerClasses);

        Set<Class<? extends TrailExceptionHandler>> result = underTest.findTrailExceptionHandlers();

        assertThat(result).isNotEmpty();
        assertThat(result).containsOnly(DummyExceptionHandler.class, DummyExceptionHandler2.class, SecurityTrailExceptionHandler.class);
    }

    public static class DummyTrailWriter implements TrailWriter {
        @Override
        public void writeEvent(AuditEvent auditEvent) {
        }
    }

    public static class DummyTrailWriter2 implements TrailWriter {
        @Override
        public void writeEvent(AuditEvent auditEvent) {
        }
    }

    public static class DummyExceptionHandler implements TrailExceptionHandler<Exception> {
        @Override
        public String describeException(Exception e) {
            return "dummy";
        }

    }

    public static class DummyExceptionHandler2 implements TrailExceptionHandler<Exception> {
        @Override
        public String describeException(Exception e) {
            return "dummy2";
        }

    }
}
