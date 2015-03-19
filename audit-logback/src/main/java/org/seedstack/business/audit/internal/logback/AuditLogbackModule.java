/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.internal.logback;

import org.seedstack.business.audit.infrastructure.logback.AuditLogbackLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

import com.google.inject.AbstractModule;

/**
 * Audit logback module.
 */
public class AuditLogbackModule extends AbstractModule {

    private static final String AUDIT_APPENDER = "AUDIT_APPENDER";

    private static final String AUDIT_LOGGER = "AUDIT_LOGGER";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogbackModule.class);

    @Override
    protected void configure() {
        // We inject what we can in the audit appender if it exists
        LayoutWrappingEncoder<?> layoutEncoder = null;
        Logger logger = LoggerFactory.getLogger(AUDIT_LOGGER);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
            Appender<ILoggingEvent> appender = logbackLogger.getAppender(AUDIT_APPENDER);
            if (appender instanceof OutputStreamAppender) {
                OutputStreamAppender<?> app = (OutputStreamAppender<?>) appender;
                Encoder<?> encoder = app.getEncoder();
                if (encoder instanceof LayoutWrappingEncoder) {
                    layoutEncoder = (LayoutWrappingEncoder<?>) encoder;
                }
            }
        }
        if (layoutEncoder != null && layoutEncoder.getLayout() instanceof AuditLogbackLayout) {
            requestInjection(layoutEncoder.getLayout());
        } else {
            // We warn that a logback writer is wanted but none is found
            LOGGER.info(
                    "Audit uses logback but no appender/logger found. Make sure an appender is named {}, has a LayoutWrappingEncoder with an AuditLogbackLayout, and that a logger named {} is defined with this appender.",
                    AUDIT_APPENDER, AUDIT_LOGGER);
        }
    }

}
