/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.application.internal;

import org.seedstack.business.fixtures.application.GenericService;

public class GenericServiceInternal<T> implements GenericService<T> {

    @Override
    public T doSomething(Class<T> someClass) {
        try {
            return someClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
