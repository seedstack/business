/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.infrastructure.logback;

import org.seedstack.business.audit.api.AuditEvent;
import org.seedstack.business.audit.spi.TrailWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TrailWriter implementation for Logback.
 */
public class LogbackTrailWriter implements TrailWriter {

    private Logger auditLogger = LoggerFactory.getLogger("AUDIT_LOGGER");

    @Override
    public void writeEvent(AuditEvent auditEvent) {
        auditLogger.info("", auditEvent);
    }

}
