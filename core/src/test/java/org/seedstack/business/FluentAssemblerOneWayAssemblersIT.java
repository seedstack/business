/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static junit.framework.TestCase.fail;

import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class FluentAssemblerOneWayAssemblersIT {
    private static final int PRICE = 10000;
    @Inject
    private Repository<Order, String> orderRepository;
    @Inject
    private OrderFactory orderFactory;
    @Inject
    private FluentAssembler fluently;

    private OrderDto orderDto;
    private Order order;

    @Before
    public void before() {
        orderRepository.clear();

        orderDto = new OrderDto("1", "light saber");
        orderDto.setPrice(PRICE);

        order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        orderRepository.add(order);

    }

    @After
    public void tearDown() {
        orderRepository.clear();
    }

    @Test
    public void mergeOneWayDtoAssemble() {
        fluently.assemble(order).with("DtoAssemble").to(OrderDto.class);

        try {
            fluently.merge(orderDto).with("DtoAssemble").into(Order.class).fromFactory();
            fail();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("[BUSINESS] Merge not implemented");
        }
    }

    @Test
    public void mergeOneWayAggregateAssemble() {
        fluently.merge(orderDto).with("AggregateAssemble").into(Order.class).fromFactory();

        try {
            fluently.assemble(order).with("AggregateAssemble").to(OrderDto.class);
            fail();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("[BUSINESS] Assemble not implemented");
        }
    }

}
