/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.auto.BasicAggregate;
import org.seedstack.business.fixtures.assembler.auto.BasicDto;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.fail;


@RunWith(SeedITRunner.class)
public class AssemblerDslIT {
    public static final int PRICE = 10000;

    @Inject
    private Repository<Order, String> orderRepository;

    @Inject
    private OrderFactory orderFactory;

    @Inject
    private FluentAssembler fluently;

    @Before
    public void before() {
        orderRepository.clear();
    }

    @Test
    public void testAssembleFromFactory() {
        OrderDto orderDto = new OrderDto("1", "light saber");
        orderDto.setPrice(PRICE);
        Order aggregateRoot = fluently.merge(orderDto).into(Order.class).fromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, so it is set by the assembler
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);
    }

    @Test
    public void testAssembleFromRepository() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        orderRepository.add(order);

        Order aggregateRoot = null;
        try {
            aggregateRoot = fluently.merge(new OrderDto("1", "light saber")).into(Order.class).fromRepository().orFail();
        } catch (AggregateNotFoundException e) {
            fail();
        }
        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // other details come from the aggregate loaded from the repository
        Assertions.assertThat(aggregateRoot.getOtherDetails()).isEqualTo("some details");
    }

    @Test
    public void testAssembleFromRepositoryOrFail() {
        try {
            fluently.merge(new OrderDto("1", "light saber")).into(Order.class).fromRepository().orFail();
            fail();
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e).isNotNull();
        }
    }

    @Test
    public void testAssembleFromRepositoryOrFactory() {
        OrderDto dto = new OrderDto("1", "light saber", PRICE);

        Order aggregateRoot = fluently.merge(dto).into(Order.class).fromRepository().orFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot.getId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoot.getProduct()).isEqualTo("light saber");
        // the customer name is not part of the factory parameters, but so it should be set by the assembler
        Assertions.assertThat(aggregateRoot.getPrice()).isEqualTo(PRICE);
    }

    @Test
    public void testAssembleFromRepositoryOrFactoryMixed() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");
        orderRepository.add(order);

        OrderDto dto1 = new OrderDto("1", "death star", PRICE);
        OrderDto dto2 = new OrderDto("2", "light saber", PRICE);

        List<Order> aggregateRoots = fluently.merge(Lists.newArrayList(dto1, dto2)).into(Order.class).fromRepository().orFromFactory();

        Assertions.assertThat(aggregateRoots).hasSize(2);
        Assertions.assertThat(aggregateRoots.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(aggregateRoots.get(0).getProduct()).isEqualTo("death star");
        Assertions.assertThat(aggregateRoots.get(0).getOtherDetails()).isEqualTo("some details");
        Assertions.assertThat(aggregateRoots.get(1).getId()).isEqualTo("2");
        Assertions.assertThat(aggregateRoots.get(1).getProduct()).isEqualTo("light saber");
        Assertions.assertThat(aggregateRoots.get(1).getOtherDetails()).isNull();
    }

    @Test
    public void testAssembleFromDto() {
        Order order = orderFactory.create("1", "death star");
        order.setOtherDetails("some details");

        fluently.merge(new OrderDto("1", "light saber", PRICE)).into(order);

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getId()).isEqualTo("1");
        Assertions.assertThat(order.getProduct()).isEqualTo("light saber"); // updated info
        Assertions.assertThat(order.getPrice()).isEqualTo(PRICE);

        Assertions.assertThat(order.getOtherDetails()).isEqualTo("some details"); // kept info
    }

    @Test
    public void testWithFullDefault() {
        UUID id = UUID.randomUUID();
        // Uses:
        // - identity creation strategy
        // - default factory without params
        // - default assembler
        BasicAggregate aggregate = fluently.merge(new BasicDto(id, "aaa", "bbb")).with(ModelMapper.class)
                .into(BasicAggregate.class).fromFactory();
        Assertions.assertThat(aggregate.getId()).isNotNull();
        Assertions.assertThat(aggregate.getId()).isNotEqualTo(id);
        Assertions.assertThat(aggregate.getField1()).isEqualTo("aaa");
        Assertions.assertThat(aggregate.getField2()).isEqualTo("bbb");
    }
}
