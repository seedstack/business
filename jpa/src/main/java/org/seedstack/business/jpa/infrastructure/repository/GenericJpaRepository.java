/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.infrastructure.repository;

import org.seedstack.business.api.domain.AggregateRoot;


/**
 * This class serves as inheritance base for the JPA repositories.
 *
 * @param <A> JPA Entity Type (DDD: Aggregate)
 * @param <K> key type
 *            
 * @author epo.jemba@ext.mpsa.com
 * @deprecated This class is now deprecated and will be removed in the next version. Please use {@link BaseJpaRepository} instead.
 */
@Deprecated
public abstract class GenericJpaRepository<A extends AggregateRoot<K>, K> extends BaseJpaRepository<A, K> {

    
    
}
