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

import java.util.HashSet;
import java.util.Set;

import org.seedstack.business.audit.api.Trail;

/**
 * Local thread context for audit interceptor
 * 
 * @author U236838
 */
class AuditContext {

    private Trail trail;

    private Set<Exception> auditedExceptions = new HashSet<Exception>();

    /** Nested audited sections */
    public int nbNestedAudits;

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public Set<Exception> getAuditedExceptions() {
        return auditedExceptions;
    }
}
