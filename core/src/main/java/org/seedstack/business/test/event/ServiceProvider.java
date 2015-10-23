/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.test.event;

/**
 * Provides a method to specify the method which should be tested.
 */
public interface ServiceProvider {

    /**
     * Indicates the method to test with its params.
     *
     * @param method method name
     * @param args   method arguments
     * @return itself
     */
    HandlerProvider whenCalled(String method, Object... args);
}
