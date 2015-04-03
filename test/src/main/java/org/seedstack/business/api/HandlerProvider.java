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

import java.util.Map;

/**
 * The {@code HandlerProvider} class provide method to assert on event handled.
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 11/06/2014
 */
public interface HandlerProvider {

    /**
     * Checks if the given event was received by the handler when the specified method is called.
     *
     * @param event   event which should be received
     * @param handler handler which should be called
     * @throws org.seedstack.seed.core.api.SeedException if the expectation is not respected
     */
    void eventWasHandledBy(Event event, Class<? extends EventHandler> handler);

    /**
     * Checks if the given event was received by the handler when the specified method is called.
     *
     * @param handlerMap map of handler which should be called with the associate event
     * @throws org.seedstack.seed.core.api.SeedException if the expectation is not respected
     */
    void eventWasHandledBy(Map<Class<? extends EventHandler>, Event> handlerMap);
}
