/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.identity;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.api.domain.Entity;

import java.util.Map;

/**
 * Interface for handling identity generation.
 *
 * @param <E>  the entity
 * @param <ID> the entity id
 * @author redouane.loulou@ext.mpsa.com
 */
public interface IdentityHandler<E extends Entity<ID>, ID> {

    /**
     * Generate new id for entity.
     *
     * @param entity              Generate by the factory
     * @param entityConfiguration property coming from props configuration for entity
     * @return the entity id
     */
    ID handle(final Entity entity, Configuration entityConfiguration);

}
