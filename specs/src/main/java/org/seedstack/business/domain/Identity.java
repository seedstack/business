/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.domain;

import org.seedstack.business.domain.identity.IdentityHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks the field of an entity as a candidate for identity generation. The annotation value specifies
 * the identity generation strategy class.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Identity {
    /**
     * @return the identity handler to use to generate identity.
     */
    Class<? extends IdentityHandler> value();
}
