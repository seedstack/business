/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.api.interfaces.assembler.dsl.Assemble;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.customer.Order;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.customer.OrderDto;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.customer.OrderFactory;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.customer.OrderRepositoryInternal;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

import static junit.framework.TestCase.fail;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
@RunWith(SeedITRunner.class)
public class AssemblerDslIT {

    public static final int PRICE = 10000;

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private OrderFactory orderFactory;

    @Inject
    private Assemble assemble;

    @Inject
    private FluentAssembler fluently;

    @Before
    public void before() {
        ((OrderRepositoryInternal)orderRepository).clear();
    }

    @Test
    public void testAssembleInjectee() {
        Object injector2 = Whitebox.getInternalState(assemble, "registry");
        Assertions.assertThat(injector2).isNotNull();
    }

    @Test
    public void testAssembleFromFactory() {
        OrderDto orderDto = new OrderDto("1", "light saber");
        orderDto.setPrice(PRICE);
        Order aggregateRoot = fluently.assemble().dto(orderDto).to(Order.class).fromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getEntityId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);
    }

    @Test
    public void testAssembleFromRepository() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        orderRepository.persist(order);

        Order aggregateRoot = null;
        try {
            aggregateRoot = fluently.assemble().dto(new OrderDto("1", "light saber")).to(Order.class).fromRepository().orFail();
        } catch (AggregateNotFoundException e) {
            fail();
        }
        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getEntityId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // other details come from the aggregate loaded from the repository
        Assertions.assertThat(aggregateRoot.getOtherDetails()).isEqualTo("some details");
    }

    @Test
    public void testAssembleFromRepositoryOrFail() {
        try {
            fluently.assemble().dto(new OrderDto("1", "light saber")).to(Order.class).fromRepository().orFail();
            fail();
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e).isNotNull();
        }
    }

    @Test
    public void testAssembleFromRepositoryOrFactory() {
        OrderDto dto = new OrderDto("1", "light saber", PRICE);

        Order aggregateRoot = fluently.assemble().dto(dto).to(Order.class).fromRepository().orFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getEntityId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, but so it should be set by the assembler
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);
    }

    @Test
    public void testAssembleFromDto() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");

        Order aggregateRoot = fluently.assemble().dto(new OrderDto("1", "light saber", PRICE)).to(order);

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getEntityId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber"); // updated info
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);

        Assertions.assertThat(aggregateRoot.getOtherDetails()).isEqualTo("some details"); // kept info
    }
}
