/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;

/**
 * Indicates that a repository can't find an aggregate root.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AggregateNotFoundException extends Exception {

    /**
     * Constructor.
     */
    public AggregateNotFoundException() {
    }

    /**
     * Constructor with message.
     *
     * @param message the message to throw
     */
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
