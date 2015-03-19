/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.identity;

import org.seedstack.business.api.domain.Entity;

/**
 * IdentityService generate a unique appropriate ID for a given entity
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
public interface IdentityService {

    /**
     * Identifies the given entity
     *
     * @param entity the entity to identify
     * @param <E> the entity type
     * @param <ID> the entity key type
     * @return the identified entity
     */
	<E extends Entity<ID>, ID> E identify(E entity);
}
