/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.application.internal;

import javax.inject.Singleton;
import org.seedstack.business.fixtures.application.SingletonService;

@Singleton
public class SingletonServiceInternal implements SingletonService {

    private Object singletonObject = new Object();

    @Override
    public Object getStuff() {
        return singletonObject;
    }
}
