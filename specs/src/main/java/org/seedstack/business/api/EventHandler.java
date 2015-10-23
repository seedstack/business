/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api;

/**
 * Interface for event handlers. It handles events fired by the {@code EventService}.
 *
 * @param <E> the type of event to handle
 * @author pierre.thirouin@ext.mpsa.com
 * @see org.seedstack.business.api.Event
 * @see org.seedstack.business.api.EventService
 */
public interface EventHandler<E extends Event> {

    /**
     * Handles an event. If this method throws an exception, this may prevent other handlers to receive the event.
     *
     * @param event the handled event
     */
    void handle(E event);
}
