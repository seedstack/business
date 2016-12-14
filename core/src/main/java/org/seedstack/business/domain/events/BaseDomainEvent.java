/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.events;

import org.seedstack.business.Event;
import org.seedstack.business.domain.BaseValueObject;
import org.seedstack.business.domain.DomainEvent;

/**
 * BaseDomainEvent is the abstract class to extend to create a domain event. It extends from {@code BaseValueObject} which provides
 * {@code equals} and {@code hashCode}. This allows to check cyclic call detection and to test events with EventFixture.
 * <p>
 * It is possible to directly implements {@code Event} instead of extends BaseEvent. But you will have to implements
 * {@code equals} and {@code hashCode}.
 * </p>
 * <p>
 * NB: Event should be immutable.
 * </p>
 *
 *
 * @see org.seedstack.business.domain.BaseValueObject
 * @see Event
 */
public abstract class BaseDomainEvent extends BaseValueObject implements DomainEvent {

	/***/
	private static final long serialVersionUID = 1L;
}
