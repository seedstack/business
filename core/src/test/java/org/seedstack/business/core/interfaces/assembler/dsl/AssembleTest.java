/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl;

import com.google.common.collect.Lists;
import org.javatuples.Pair;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;

import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.api.Tuples;

import java.util.List;


/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AssembleTest {

    private OrderDto orderDto = new OrderDto();
    private Order myOrder = new Order();
    private Order myOrder2 = new Order();
    private Customer customer = new Customer();
    private Customer customer2 = new Customer();
    private Order order = new Order();
    private Order order2 = new Order();
    private List<Object> dtos = Lists.newArrayList(orderDto, myOrder);
    private List<Order> orders = Lists.newArrayList(myOrder, myOrder);

    private FluentAssembler fluently;

    public void testDsl() {

        // dto to aggregate
        order = fluently.assemble().dto(orderDto).to(myOrder);

        // from factory
        order = fluently.assemble().dto(orderDto).to(Order.class).fromFactory();

        // data security
        order = fluently.assemble().securely().dto(orderDto).to(Order.class).fromFactory();

        // list of dto to tuple of aggregates
        Pair<Order, Customer> aggregateClass = Tuples.create(Order.class, Customer.class);
        Pair<Order, Customer> orderCustomerPair = fluently.assemble().dto(orderDto).to(aggregateClass).fromFactory();

        // list of dtos to list of aggregates
        orders = fluently.assemble().dtos(dtos).to(orders);

        // from repo or fail
        try {
            order = fluently.assemble().dto(orderDto).to(Order.class).fromRepository().orFail();
        } catch (AggregateNotFoundException e) {
            e.printStackTrace();
        }

        // from repo or fact
        order = fluently.assemble().dto(orderDto).to(Order.class).fromRepository().thenFromFactory();

        // aggregate to dto
        OrderDto orderDto1 = fluently.assemble().aggregate(myOrder).to(OrderDto.class);

        // tuple of aggregate to dto
        orderDto1 = fluently.assemble().tuple(Tuples.create(Order.class, Customer.class)).to(OrderDto.class);

        List<OrderDto> orderDtos = fluently.assemble().tuples(
                Lists.newArrayList(Tuples.create(order, customer), Tuples.create(order2, customer2))
        ).to(OrderDto.class);
    }

    static class OrderDto {
    }

    static class Order extends BaseAggregateRoot<String> {
        String entityId;

        @Override
        public String getEntityId() {
            return entityId;
        }
    }

    static class Customer extends BaseAggregateRoot<String> {
        String entityId;

        @Override
        public String getEntityId() {
            return entityId;
        }
    }
}
