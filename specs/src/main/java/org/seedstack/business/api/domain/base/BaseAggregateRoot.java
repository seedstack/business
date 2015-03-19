/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.base;

import org.seedstack.business.api.domain.AggregateRoot;

/**
 * This abstract class is the base class of all AggregateRoot in Seed Business Framework.
 *
 * @param <ID> the entityId Type of the aggregate root.
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseAggregateRoot<ID> extends BaseEntity<ID> implements AggregateRoot<ID> {

    protected BaseAggregateRoot() {
    }

}
