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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;
import org.seedstack.business.audit.api.TrailExceptionHandler;
import org.seedstack.business.audit.spi.TrailWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads the configuration to deduce the classes to use
 * 
 * @author U236838
 */
public class AuditConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditConfigurer.class);

    private static final String TRAIL_WRITERS_KEY = "writers";

    private static final String TRAIL_EXCEPTION_HANDLERS_KEY = "exceptionHandlers";

    private Configuration configuration;

    private Map<Class<?>, Collection<Class<?>>> auditClasses;

    /**
     * Constructor
     * 
     * @param configuration configuration for audit
     * @param auditClasses scanned classes
     */
    public AuditConfigurer(Configuration configuration, Map<Class<?>, Collection<Class<?>>> auditClasses) {
        this.configuration = configuration;
        this.auditClasses = auditClasses;
    }

    /**
     * Finds the trail writers as configured in the props.
     * 
     * @return a collection of trail writer classes.
     */
    public Set<Class<? extends TrailWriter>> findTrailWriters() {
        String[] trailWriters = configuration.getStringArray(TRAIL_WRITERS_KEY);
        Collection<Class<?>> scannedTrailWriters = auditClasses.get(TrailWriter.class);
        if (trailWriters.length == 0) {
            LOGGER.info("No TrailWriter specified");
            return Collections.emptySet();
        }
        return findClasses(trailWriters, scannedTrailWriters, TrailWriter.class);
    }

    /**
     * Finds the exception handlers as configured in the props.
     * 
     * @return a collection of exception handler classes.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Set<Class<? extends TrailExceptionHandler>> findTrailExceptionHandlers() {
        String[] trailExceptionHandlers = configuration.getStringArray(TRAIL_EXCEPTION_HANDLERS_KEY);
        Collection<Class<?>> scannedTrailExceptionHandlers = auditClasses.get(TrailExceptionHandler.class);
        if (trailExceptionHandlers.length == 0) {
            LOGGER.info("No audit TrailExceptionHandler specified, using every handler found");
            Set<Class<? extends TrailExceptionHandler>> foundExceptionHandlers = new HashSet<Class<? extends TrailExceptionHandler>>();
            for (Class<?> class1 : scannedTrailExceptionHandlers) {
                foundExceptionHandlers.add((Class<? extends TrailExceptionHandler<?>>) class1);
                LOGGER.info("Registered audit exception handler {}", class1);
            }
            return foundExceptionHandlers;
        }
        return findClasses(trailExceptionHandlers, scannedTrailExceptionHandlers, TrailExceptionHandler.class);
    }

    @SuppressWarnings("unchecked")
    private <E> Set<Class<? extends E>> findClasses(String[] configuration, Collection<Class<?>> scannedClasses, Class<? extends E> type) {
        Set<Class<? extends E>> foundClasses = new HashSet<Class<? extends E>>();
        for (Class<?> class1 : scannedClasses) {
            if (ArrayUtils.contains(configuration, class1.getSimpleName()) || ArrayUtils.contains(configuration, class1.getName())) {
                foundClasses.add((Class<E>) class1);
                LOGGER.info("Registered {} {}", type.getSimpleName(), class1);
            }
        }
        if (foundClasses.isEmpty()) {
            LOGGER.warn(
                    "No {} as defined in the configuration could be found. Make sure the configuration match the name (or simple name) of the class",
                    type.getSimpleName());
        }
        return foundClasses;
    }
}
