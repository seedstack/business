/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.repositories;

import org.seedstack.business.domain.BaseAggregateRoot;

public class TestAggregate extends BaseAggregateRoot<String> {

    @Override
    public String getId() {
        return "test";
    }
}
