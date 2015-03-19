/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api;

/**
 * EventService provides methods to fire events. These events will be caught by EventHandlers.
 * <p/>
 * The implementation for EventService is provided by Seed. You don't have to implement it.
 * <p/>
 * For instance to fire an event:
 * <pre>
 * {@literal @}Inject
 * private EventService
 *
 * eventService.fire(new MyEvent());
 * </pre>
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 04/06/2014
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
