/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * An helper base class that can be extended to create a domain aggregate root. If extending this
 * base class is not desirable, you can instead do one of the following: <ul> <li>Implement {@link
 * AggregateRoot},</li> <li>Annotate your class with {@link DomainAggregateRoot} (but this limits
 * the ability to use framework features).</li> </ul>
 *
 * @param <I> The type of the aggregate root identifier.
 */
public abstract class BaseAggregateRoot<I> extends BaseEntity<I> implements AggregateRoot<I> {

}
