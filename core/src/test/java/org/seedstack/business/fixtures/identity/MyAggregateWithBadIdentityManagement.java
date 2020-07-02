/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.fixtures.identity;

import java.util.Set;
import java.util.UUID;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;

public class MyAggregateWithBadIdentityManagement extends BaseAggregateRoot<UUID> {

    @Identity(generator = RandomIdentityGenerator.class)
    private UUID id;

    private String name;

    private MyEntity mySubAggregate;

    private Set<MyEntity> mySubAggregates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyEntity getMySubAggregate() {
        return mySubAggregate;
    }

    public void setMySubAggregate(MyEntity mySubAggregate) {
        this.mySubAggregate = mySubAggregate;
    }

    public Set<MyEntity> getMySubAggregates() {
        return mySubAggregates;
    }

    public void setMySubAggregates(Set<MyEntity> mySubAggregates) {
        this.mySubAggregates = mySubAggregates;
    }
}
