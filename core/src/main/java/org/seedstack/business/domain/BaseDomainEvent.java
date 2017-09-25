/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;


/**
 * An helper base class that can be extended to create a domain event. If extending this base class
 * is not desirable, you can instead implement {@link org.seedstack.business.domain.DomainEvent}.
 */
public abstract class BaseDomainEvent extends BaseValueObject implements DomainEvent {

}
