/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.util.Collection;
import java.util.List;

/***
 * Interface for Interceptors, which handles the execution order of the EventHandlers
 * that are fired. 
 *
 * @see DomainEvent
 * @see DomainEventHandler
 * @see PriorizedEvent
 */
@SuppressWarnings("rawtypes")
public interface DomainEventInterceptor {

    List<DomainEventHandler> interceptDomainHandler(Collection<DomainEventHandler> handlers);
}
