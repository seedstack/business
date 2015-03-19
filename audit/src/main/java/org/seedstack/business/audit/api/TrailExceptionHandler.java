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
 * Allows to describe an event in case of a certain exception.
 * 
 * @param <E> exception handled by the handler
 * @author U236838
 */
public interface TrailExceptionHandler<E extends Exception> {

    /**
     * Creates a message for an exception.
     * 
     * @param e the exception to describe
     * @return the description
     */
    String describeException(E e);
}
