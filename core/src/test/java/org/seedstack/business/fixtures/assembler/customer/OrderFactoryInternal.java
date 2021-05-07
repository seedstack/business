/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import java.util.Date;
import org.seedstack.business.domain.BaseFactory;

public class OrderFactoryInternal extends BaseFactory<Order> implements OrderFactory {

    public Order create(String id, String product) {
        return new Order(id, product);
    }

    @Override
    public Order create(String id, String product, Date orderDate) {
        return new Order(id, product, orderDate);
    }
}
