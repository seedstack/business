/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.spi;

import org.seedstack.business.audit.api.AuditEvent;

/**
 * Interface used to write an event in its final form (log, bdd...)
 * 
 * @author U236838
 */
public interface TrailWriter {

    /**
     * Writes the event
     * 
     * @param auditEvent the event to write
     */
    void writeEvent(AuditEvent auditEvent);
}
