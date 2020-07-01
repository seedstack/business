/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.order;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.fixtures.domain.customer.CustomerId;

public class OrderFactoryDefault extends BaseFactory<Order> implements OrderFactory {

    public OrderFactoryDefault() {
    }

    @Override
    public Order createOrder(String orderId) {
        Order order = new Order(new OrderId(orderId));
        return order;
    }

    @Override
    public Order createOrder(String orderId, String customerId) {
        Order order = createOrder(orderId);
        order.setCustomerId(new CustomerId(customerId));
        return order;
    }

}
