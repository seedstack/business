/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/***
 * This annotations allows to set a {@link DomainEventInterceptor} to intercept
 * {@link DomainEvent} and alter the {@link DomainEventHandler} properties (Skip / Add one / Order
 * are examples of what can be achieved with DomainEventHandlerInterceptors)
 *
 * @see DomainEvent
 * @see DomainEventHandler
 * @see DomainEventInterceptor
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface PriorizedEvent {

    Class<? extends DomainEventInterceptor> priorizator();
}
