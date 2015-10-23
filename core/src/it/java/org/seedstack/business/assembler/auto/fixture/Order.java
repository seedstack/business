/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import org.seedstack.business.api.domain.BaseAggregateRoot;

/**
* @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
*/
public class Order extends BaseAggregateRoot<String> {

    private String id;

    private Address billingAddress;

    @Override
    public String getEntityId() {
        return id;
    }

    public Order() {
    }

    public Order(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
