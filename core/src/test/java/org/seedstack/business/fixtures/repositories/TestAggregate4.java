/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.repositories;

import org.seedstack.business.domain.BaseAggregateRoot;

public class TestAggregate4 extends BaseAggregateRoot<String> {
    private String id;

    public TestAggregate4(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
