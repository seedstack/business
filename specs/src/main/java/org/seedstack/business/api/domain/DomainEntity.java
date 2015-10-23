/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import java.lang.annotation.*;


/**
 * This annotation tells the Business Framework that the current class is candidate to be an entity from DDD approach.
 * <p>
 * Usually projects will use {@link BaseEntity} as it already provides the right
 * implementation for {@code equals} and {@code hashCode()}.
 * </p>
 *
 * @author epo.jemba@ext.mpsa.com
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DomainEntity {
}
