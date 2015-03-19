/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.annotations;

import java.lang.annotation.*;


/**
 * This annotation tells the Business Framework that the current class is candidate to be an entity from DDD approach.
 * <p>
 * To be a understood as a valid Entity, the class has also to respects the followings: 
 * <ul>
 * <li> to be non abstract  AND 
 * <li> have a field named entityId has to be annotated with {@link javax.persistence.Id} or {@link javax.persistence.EmbeddedId}
 * </ul>
 * <p>   
 *  SEED Business Support propose out of the box parent class for Entity :
 * <ul>
 *  <li> {@link BaseJpaEntity} for custom key jpa aggregate root.
 *  <li> {@link SimpleJpaEntity} for aggregate root with simple entity id.
 *  <li> {@link EmbedJpaEntity} for aggregate root with composite entity id.
 * </ul>
 * Usually projects will use {@link SimpleJpaEntity} and {@link EmbedJpaEntity}. Have a look for examples.
 * @author epo.jemba@ext.mpsa.com
 *
 */
@Documented
@DomainElement
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
public @interface DomainEntity {
}
