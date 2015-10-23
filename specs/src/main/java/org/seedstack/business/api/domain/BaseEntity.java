/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.domain.DomainErrorCodes;
import org.seedstack.business.api.domain.Entity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seedstack.seed.core.api.SeedException;


/**
 * This abstract class is the base class for all Entities in Seed Business Framework.
 *
 * It provides an {@code equals()} method based on the entity identity. This also enforce
 * the entity to valid, i.e. not null. Otherwise a SeedException will be thrown.
 *
 * @param <ID> The type of the entityId of the Entity.
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseEntity<ID> implements Entity<ID> {

    @Override
    public abstract ID getEntityId();

    @Override
    public final int hashCode() {
        return checkIdentity().hashCode();
    }

    @Override
    public final boolean equals(final Object o) {
        ID entityId = checkIdentity();

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return entityId.equals(this.getClass().cast(o).getEntityId());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false);
    }

    private ID checkIdentity() {
        ID entityId = getEntityId();
        if (entityId == null) {
            throw SeedException.createNew(DomainErrorCodes.ENTITY_WITHOUT_IDENTITY_ISSUE)
                    .put("className", getClass().getName());
        }
        return entityId;
    }
}
