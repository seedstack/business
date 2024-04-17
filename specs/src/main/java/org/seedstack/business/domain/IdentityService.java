/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * IdentityService provides the ability to generate an identity using an {@link IdentityGenerator}
 * and inject it in the specified entity.
 *
 * @see IdentityGenerator
 */
public interface IdentityService {

    /**
     * Identifies the given entity.
     *
     * @param entity the entity to identify.
     * @param <E>    the entity type.
     * @param <I>    the entity key type.
     * @return the identified entity.
     * @throws IdentityExistsException if the entity already has an identity.
     */
    <E extends Entity<I>, I> E identify(E entity) throws IdentityExistsException;
}
