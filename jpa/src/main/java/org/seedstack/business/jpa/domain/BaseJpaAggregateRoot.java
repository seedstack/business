/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.domain;

import org.seedstack.business.api.domain.base.BaseAggregateRoot;

import javax.persistence.MappedSuperclass;


/**
 * This abstract class is the base class of all the Jpa aggregate roots in Seed Business Framework.
 *
 * @param <ID> the entityId Type of the aggregate root.
 * @author epo.jemba@ext.mpsa.com
 * @Deprecated Aggregate roots should not extend classes which depend on the infrastructure. This class will be removed
 * in the next version of the business framework.
 */
@Deprecated
@MappedSuperclass
public abstract class BaseJpaAggregateRoot<ID> extends BaseAggregateRoot<ID> {

    protected BaseJpaAggregateRoot() {
    }

}
