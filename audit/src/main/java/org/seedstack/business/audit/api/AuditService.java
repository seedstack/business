/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.api;


/**
 * Service to trail events for auditing purpose.
 * 
 * @author U236838
 */
public interface AuditService {

    /**
     * Creates a new trail initialized with initiator and Host
     * 
     * @return a brand new trail
     */
    Trail createTrail();

    /**
     * Writes an event.
     * 
     * @param message the message to trail
     * @param trail the trail to use
     */
    void trail(String message, Trail trail);
}
