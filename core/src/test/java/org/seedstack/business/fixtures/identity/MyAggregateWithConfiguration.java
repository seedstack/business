/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
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
import org.seedstack.business.util.UuidGenerator;

public class MyAggregateWithConfiguration extends BaseAggregateRoot<UUID> {

    @Identity(generator = UuidGenerator.class)
    private UUID id;
    private String name;
    private MyEntity mySubEntity;
    private Set<MyEntity> mySubEntities;

    public MyAggregateWithConfiguration() {
    }

    public MyAggregateWithConfiguration(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyEntity getMySubEntity() {
        return mySubEntity;
    }

    public void setMySubEntity(MyEntity mySubEntity) {
        this.mySubEntity = mySubEntity;
    }

    public Set<MyEntity> getMySubEntities() {
        return mySubEntities;
    }

    public void setMySubAggregates(Set<MyEntity> mySubEntities) {
        this.mySubEntities = mySubEntities;
    }
}