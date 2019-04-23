/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * This exception is thrown when the existence of an identity is an error condition.
 */
public class IdentityExistsException extends RuntimeException {

    /**
     * Creates the exception without message nor cause.
     */
    public IdentityExistsException() {
        super();
    }

    /**
     * Creates the exception with an error message.
     *
     * @param message the message.
     */
    public IdentityExistsException(String message) {
        super(message);
    }

    /**
     * Creates the exception with an error message and a cause.
     *
     * @param message the message.
     * @param cause   the cause.
     */
    public IdentityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates the exception with a cause.
     *
     * @param cause the cause.
     */
    public IdentityExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates the exception with an error message, a cause and specify additional parameters.
     *
     * @param message            the message.
     * @param cause              the cause.
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public IdentityExistsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
