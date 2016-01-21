/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

/**
 * EventService provides methods to fire events. These events will be caught by EventHandlers.
 * <p>
 * The implementation for EventService is provided by Seed. You don't have to implement it.
 * </p>
 * For instance to fire an event:
 * <pre>
 * {@literal @}Inject
 * private EventService
 *
 * eventService.fire(new MyEvent());
 * </pre>
 *
 * @author pierre.thirouin@ext.mpsa.com
 * @see Event
 * @see EventHandler
 */
public interface EventService {

    /**
     * Fires an event. This method will propagate exception fired by event handlers.
     *
     * @param event the fired event
     * @param <E>   the event type
     */
    <E extends Event> void fire(E event);

}
