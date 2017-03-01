/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

/**
 * EventService provides methods to fire events. These events will be caught by EventHandlers.
 * <p>
 * The implementation for EventService is provided by Seed. You don't have to implement it.
 * </p>
 * For instance to fire an event:
 * <pre>
 * {@literal @}Inject
 * private DomainEventPublisher domainEventPublisher;
 *
 * domainEventPublisher.publish(new MyEvent());
 * </pre>
 *
 * @see DomainEvent
 * @see DomainEventHandler
 */
public interface DomainEventPublisher {

    /**
     * Publishes an event.
     *
     * @param event the fired event
     * @param <E>   the event type
     */
    <E extends DomainEvent> void publish(E event);
}
