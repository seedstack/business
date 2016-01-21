/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.identity;

import org.seedstack.business.domain.Entity;

/**
 * Interface for handling UUID generation.
 *
 * @param <E>  the entity
 * @param <ID> the entity id
 * @author redouane.loulou@ext.mpsa.com
 */
public interface UUIDHandler<E extends Entity<ID>, ID> extends IdentityHandler<E, ID> {
}
