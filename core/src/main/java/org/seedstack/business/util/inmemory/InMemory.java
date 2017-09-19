/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.util.inmemory;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Qualifier annotation that can be used at {@link org.seedstack.business.domain.Repository} injection points to specify
 * that the {@link DefaultInMemoryRepository} implementation should be injected.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface InMemory {

}
