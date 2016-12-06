/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.persistence.inmemory;

import org.seedstack.seed.transaction.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.seedstack.seed.transaction.Propagation.REQUIRES_NEW;


/**
 * This annotation can be used on tests to specify manually which storage in memory SEED should load.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Transactional(propagation = REQUIRES_NEW)
public @interface Store {
    /**
     * @return the name of the in-memory store.
     */
    String value();

}