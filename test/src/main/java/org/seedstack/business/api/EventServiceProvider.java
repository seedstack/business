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
 * The{@code EventServiceProvider} provides method to assert on handler called.
 */
public interface EventServiceProvider {

    /**
     * Checks if the given handlers was called.
     *
     * @param handlers handlers to test
     * @throws org.seedstack.seed.core.api.SeedException if the expectation is not respected
     */
    void wasHandledBy(Class<? extends EventHandler>... handlers);

    /**
     * Checks if the given list of handler exactly correspond to the called handlers.
     *
     * @param handlers list to test
     * @throws org.seedstack.seed.core.api.SeedException if the expectation is not respected
     */
    void wasHandledExactlyBy(Class<? extends EventHandler>... handlers);

    /**
     * Checks if the given handlers was not called.
     *
     * @param handlers list to test
     * @throws org.seedstack.seed.core.api.SeedException if the expectation is not respected
     */
    void wasNotHandledBy(Class<? extends EventHandler>... handlers);
}
