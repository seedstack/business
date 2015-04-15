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
import org.javatuples.Pair;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.Customer;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.Order;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.OrderDto;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.OrderFactory;
import org.seedstack.business.api.Tuples;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;

import static junit.framework.TestCase.fail;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
@RunWith(SeedITRunner.class)
public class AssemblerDslWithTupleIT {

    public static final int PRICE = 10000;

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private OrderFactory orderFactory;

    @Inject
    private Factory<Customer> customerFactory;

    @Inject
    private FluentAssembler fluently;

    @Test
    @Ignore
    public void testAssembleFromFactory() {
        OrderDto orderDto = new OrderDto("1", "light saber");
        orderDto.setCustomerName("luke");

        Pair<Order, Customer> orderCustomerClasses = Tuples.create(Order.class, Customer.class);
        Pair<Order, Customer> orderCustomerPair = fluently.assemble().dto(orderDto).to(orderCustomerClasses).fromFactory();

        Assertions.assertThat(orderCustomerPair.getValue0()).isNotNull();
        Assertions.assertThat(orderCustomerPair.getValue0().getEntityId()).isEqualTo("1");
        Assertions.assertThat(orderCustomerPair.getValue0().getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(orderCustomerPair.getValue1().getEntityId()).isEqualTo("luke");
    }

    @Test
    @Ignore
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
    @Ignore
    public void testAssembleFromRepositoryOrFail() {
        try {
            fluently.assemble().dto(new OrderDto("1", "light saber")).to(Order.class).fromRepository().orFail();
            fail();
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e).isNotNull();
        }
    }

    @Test
    @Ignore
    public void testAssembleFromRepositoryOrFactory() {
        OrderDto dto = new OrderDto("1", "light saber", PRICE);

        Order aggregateRoot = fluently.assemble().dto(dto).to(Order.class).fromRepository().thenFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getEntityId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, but so it should be set by the assembler
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @Ignore
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
