/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.benchmarks;

import com.google.inject.name.Names;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.jmh.AbstractBenchmark;

@State(Scope.Benchmark)
public class FluentAssemblerBenchmark extends AbstractBenchmark {

    @Inject
    private FluentAssembler fluentAssembler;
    @Inject
    private Repository<Order, String> orderRepository;
    @Inject
    private OrderFactory orderFactory;
    private List<OrderDto> orderDtoList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private Order order;
    private OrderDto orderDto;

    @Setup
    public void setUp() {
        orderDto = new OrderDto(String.valueOf(1), "light saber", 1);
        order = orderFactory.create("1", "death star");
        for (int i = 0; i < 1000; i++) {
            orderDtoList.add(new OrderDto(String.valueOf(i), "light saber", i));
        }
        for (int i = 0; i < 1000; i++) {
            Order order = orderFactory.create(String.valueOf(i), "death star");
            orderRepository.add(order);
            orderList.add(order);
        }
    }

    @Benchmark
    public void assembleAggregate() {
        fluentAssembler.assemble(order)
                .with(Names.named("noop"))
                .to(OrderDto.class);
    }

    @Benchmark
    public void assembleList() {
        fluentAssembler.assemble(orderList)
                .with(Names.named("noop"))
                .toStreamOf(OrderDto.class)
                .parallel()
                .collect(Collectors.toList());
    }

    @Benchmark
    public void mergeAggregateFromFactory() {
        fluentAssembler.merge(orderDto)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromFactory();
    }

    @Benchmark
    public void mergeListFromFactory() {
        fluentAssembler.merge(orderDtoList)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromFactory()
                .asStream()
                .parallel()
                .collect(Collectors.toList());
    }

    @Benchmark
    public void mergeAggregateFromRepository() {
        fluentAssembler.merge(orderDto)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromRepository()
                .orFail();
    }

    @Benchmark
    public void mergeListFromRepository() {
        fluentAssembler.merge(orderDtoList)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromRepository()
                .orFail()
                .asStream()
                .parallel()
                .collect(Collectors.toList());
    }

    @Benchmark
    public void mergeAggregateFromRepositoryOrFactory() {
        fluentAssembler.merge(orderDto)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromRepository()
                .orFromFactory();
    }

    @Benchmark
    public void mergeListFromRepositoryOrFactory() {
        fluentAssembler.merge(orderDtoList)
                .with(Names.named("noop"))
                .into(Order.class)
                .fromRepository()
                .orFromFactory()
                .asStream()
                .parallel()
                .collect(Collectors.toList());
    }
}
