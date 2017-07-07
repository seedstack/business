/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.jmh.AbstractBenchmark;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    @Setup
    public void setUp(BenchmarkParams params) {
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
    public void assemble() {
        fluentAssembler.assemble(orderList).toListOf(OrderDto.class);
    }

    @Benchmark
    public void mergeFromFactory() {
        fluentAssembler.merge(orderDtoList).into(Order.class).fromFactory().asList();
    }

    @Benchmark
    public void mergeFromRepository() {
        fluentAssembler.merge(orderDtoList).into(Order.class).fromRepository().orFail().asList();
    }

    @Benchmark
    public void mergeFromRepositoryOrFactory() {
        fluentAssembler.merge(orderDtoList).into(Order.class).fromRepository().orFromFactory().asList();
    }
}
