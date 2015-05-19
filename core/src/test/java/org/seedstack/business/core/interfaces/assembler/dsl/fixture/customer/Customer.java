/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl.fixture.customer;

import org.seedstack.business.api.domain.base.BaseAggregateRoot;

/**
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public class Customer extends BaseAggregateRoot<String> {

    String id;

    String name;

    public Customer(String id) {
        this.id = id;
    }

    @Override
    public String getEntityId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
