/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.audit.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to automatically audit a method. A message before the execution of the method can be defined. A message at the end of the
 * execution must be defined. If an exception occurs in the method, a message can also be defined. Each message can be an EL (Expression language)
 * which will be evaluated with the context of the method (arguments, return, exception...)
 * 
 * @author U236838
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Audited {

    /**
     * The message (EL) to trail before the execution
     * 
     * @return The message (EL) to trail before the execution
     */
    String messageBefore() default "";

    /**
     * The message (EL) to trail after the execution
     * 
     * @return The message (EL) to trail after the execution
     */
    String messageAfter();

    /**
     * The message (EL) to trail on an exception
     * 
     * @return The message (EL) to trail on an exception
     */
    String messageOnException() default "";
}
