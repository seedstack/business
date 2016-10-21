/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.fixture.auto;

import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.identity.UUIDHandler;

import java.util.UUID;


public class BasicAggregate extends BaseAggregateRoot<UUID> {

    @Identity(handler = UUIDHandler.class)
    private UUID id;

    private String field1;

    private String field2;

    @Override
    public UUID getEntityId() {
        return id;
    }

    public UUID getId() {
        return id;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
