/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.domain.embeddedid;

import org.seedstack.business.jpa.domain.BaseJpaAggregateRoot;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;


/**
 * This AggregateRoot Base class is a building block to create an {@link org.seedstack.business.api.domain.AggregateRoot}.
 * It supplies the method getEntityId() of the right type.
 * <p/>
 * No setter is supplied and no should be supplied by descendant class or in package scope.
 * So it is accessed only by Factories and Repositories.
 *
 * @param <ID>
 * @author epo.jemba@ext.mpsa.com
 * @Deprecated Aggregate roots should not extend classes which depend on the infrastructure. This class will be removed
 * in the next version of the business framework.
 */
@Deprecated
@MappedSuperclass
public abstract class EmbedJpaAggregateRoot<ID> extends BaseJpaAggregateRoot<ID> {

    @EmbeddedId
    protected ID entityId;

    public EmbedJpaAggregateRoot() {
    }

    @Override
    public ID getEntityId() {
        return entityId;
    }

}
