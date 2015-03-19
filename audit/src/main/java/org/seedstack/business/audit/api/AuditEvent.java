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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An event to trail
 * 
 * @author U236838
 */
public class AuditEvent {

    private final String message;

    private final Date date;

    private final Trail trail;

    /**
     * Constructor
     * 
     * @param message the message of the event
     * @param trail the trail it belongs to
     */
    public AuditEvent(String message, Trail trail) {
        this.message = message;
        this.trail = trail;
        this.date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Trail getTrail() {
        return trail;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Formats the date of the event with the given format
     * 
     * @param format the format
     * @return the formatted date
     */
    public String getFormattedDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}
