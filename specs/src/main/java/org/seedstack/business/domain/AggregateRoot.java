/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Producible;

/**
 * This interface is the parent type for all implementations of AggregateRoot in the Business Framework.
 *
 * @param <ID> the type of the entityId
 * @author epo.jemba@ext.mpsa.com
 */
@DomainAggregateRoot
public interface AggregateRoot<ID> extends Entity<ID>, DomainObject, Producible {
}
