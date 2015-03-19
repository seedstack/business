/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.domain.annotations.DomainEntity;

/**
 * This interface is the parent type for all implementations of Entity in the Business Framework.
 * <p>
 * This interface should not be used directly as we already provide a base implementation for equals and hashcode.
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 * @param <ID> the type of the entityId 
 */
@DomainEntity
public interface Entity <ID> extends DomainObject {

    /**
     * Gets the entity id.
     *
     * @return the entity id
     */
    ID getEntityId();

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardles of other attributes.
     */
    boolean equals(Object other);

    @Override
    int hashCode();

    @Override
    String toString();

}
