/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

/**
 * The domain event publisher provides the ability to publish {@link DomainEvent} instances to be
 * handled by {@link DomainEventHandler} implementations.
 *
 * @see DomainEvent
 * @see DomainEventHandler
 */
public interface DomainEventPublisher {

  /**
   * Publishes an event.
   *
   * @param event    the published event instance.
   * @param <EventT> the event type.
   */
  <EventT extends DomainEvent> void publish(EventT event);
}
