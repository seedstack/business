/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl.fixture.auto;

import org.seedstack.business.api.interfaces.assembler.DtoOf;

import java.util.UUID;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DtoOf(BasicAggregate.class)
public class BasicDto {

    private UUID id;

    private String field1;

    private String field2;

    public BasicDto(UUID id, String field1, String field2) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
