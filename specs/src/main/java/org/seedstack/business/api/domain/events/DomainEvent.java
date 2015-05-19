/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain.events;

import org.seedstack.business.api.Event;
import org.seedstack.business.api.domain.base.BaseValueObject;

/**
 * BaseEvent is the abstract class to extend to create an event. It extends from {@code BaseValueObject} which provides
 * {@code equals} and {@code hashCode}. This allows to check cyclic call detection and to test events with EventFixture.
 * <p>
 * It is possible to directly implements {@code Event} instead of extends BaseEvent. But you will have to implements
 * {@code equals} and {@code hashCode}.
 * </p>
 * <p>
 * NB: Event should be immutable.
 * </p>
 *
 * @author pierre.thirouin@ext.mpsa.com

 * @see org.seedstack.business.api.domain.base.BaseValueObject
 * @see org.seedstack.business.api.Event
 */
public abstract class DomainEvent extends BaseValueObject implements Event {

	/***/
	private static final long serialVersionUID = 4411433793175516909L;
}
