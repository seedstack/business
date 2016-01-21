/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.lang.annotation.*;


/**
 * This annotation marks its annotated interface as a domain repository for the framework.
 *
 * Verification will be done by the framework on either or not this service is in the right place.
 *
 * @author epo.jemba@ext.mpsa.com
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
@Inherited
public @interface DomainRepository {

}
