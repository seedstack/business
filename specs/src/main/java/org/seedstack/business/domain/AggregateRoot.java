/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * Specialization of {@link Entity} when acting as an aggregate root.
 *
 * <p> The {@code BaseAggregateRoot} class can be used as a base class for aggregate roots. It
 * provides an implementation of the {@link #getId()}, {@link #equals(Object)} and {@link
 * #hashCode()} methods. </p>
 *
 * @param <I> the type of the aggregate root identifier.
 * @see DomainAggregateRoot
 * @see Entity
 */
@DomainAggregateRoot
public interface AggregateRoot<I> extends Entity<I>, Producible {

}
