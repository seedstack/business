/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.order;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.fixtures.domain.customer.CustomerId;

import java.util.ArrayList;
import java.util.List;


public class Order extends BaseAggregateRoot<OrderId> {
    private OrderId id;
    private CustomerId customerId;
    private String description;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    Order(OrderId id) {
        this.id = id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
