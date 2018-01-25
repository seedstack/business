/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.util.inmemory;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Qualifier annotation that can be used at injection points to specify that an in-memory implementation should be
 * injected.
 *
 * @see InMemorySequenceGenerator
 * @see DefaultInMemoryRepository
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface InMemory {

}
