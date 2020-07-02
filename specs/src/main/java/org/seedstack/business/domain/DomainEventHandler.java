/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * Interface for event handlers, which handle events domain events fired by the {@link
 * DomainEventPublisher}.
 *
 * @param <E> the type of event which is handled.
 * @see DomainEvent
 * @see DomainEventPublisher
 */
public interface DomainEventHandler<E extends DomainEvent> {

    /**
     * Called when an event of the correct type must be handled.
     *
     * @param event the event to handle.
     */
    void onEvent(E event);

    Class<E> getEventClass();

}
