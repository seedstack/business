/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * Specialization of {@link Entity} when acting as an aggregate root.
 *
 * @param <ID> the type of the aggregate root identifier.
 * @see DomainAggregateRoot
 */
@DomainAggregateRoot
public interface AggregateRoot<ID> extends Entity<ID>, Producible {
}
