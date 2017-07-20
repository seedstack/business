/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.seed.ClassConfiguration;

/**
 * A generator of identity for entities.
 *
 * @param <ID> the identifier type.
 */
public interface IdentityGenerator<ID> {
    /**
     * Generate a new identifier for an entity of the specified class.
     *
     * @param <E>                 the entity type.
     * @param entityClass         the entity class to generate an identity for.
     * @param entityConfiguration configuration properties for the entity class.
     * @return the generated identifier.
     */
    <E extends Entity<ID>> ID generate(Class<E> entityClass, ClassConfiguration<E> entityConfiguration);
}
