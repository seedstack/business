/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public class Customer extends BaseAggregateRoot<String> {
    String id;

    Name name;

    @Override
    public String getEntityId() {
        return id;
    }

    public Customer() {
    }

    public Customer(Name name) {

        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
