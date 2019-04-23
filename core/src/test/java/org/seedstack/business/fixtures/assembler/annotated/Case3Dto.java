/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.annotated;

import org.seedstack.business.assembler.AggregateId;

public class Case3Dto {

    private String customerName;
    private String orderItem;

    public Case3Dto(String customerName, String orderItem) {
        this.customerName = customerName;
        this.orderItem = orderItem;
    }

    @AggregateId(aggregateIndex = 0)
    public String getCustomerName() {
        return customerName;
    }

    @AggregateId(aggregateIndex = 1)
    public String getOrderItem() {
        return orderItem;
    }

}
