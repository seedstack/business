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
import org.assertj.core.api.Assertions;
import org.javatuples.Tuple;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.*;
import org.seedstack.business.api.Tuples;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class DtoAssemblerProviderImplTest {

    private DtoAssemblerProviderImpl underTest;
    private DtosAssemblerProviderImpl underTest2;
    private InternalRegistry registry;

    @Before
    public void before() {
        registry = Mockito.mock(InternalRegistryInternal.class);
    }

    @Test
    public void testToDto() {
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        AssemblerContext context = new AssemblerContext();
        context.setAggregate(new Order("lightsaber"));

        underTest = new DtoAssemblerProviderImpl(registry, context);
        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto).isNotNull();
        Assertions.assertThat(orderDto.getProduct()).isEqualTo("lightsaber");
    }

    @Test
    @Ignore
    public void testToDtoWithTuple() {
        Tuple tuple = Tuples.create(new Order("lightsaber"), new Customer("luke"));

        Mockito.when(registry.tupleAssemblerOf(tuple, OrderDto.class)).thenReturn((Assembler)new AutoTupleAssembler());

        AssemblerContext context = new AssemblerContext();
        context.setAggregateTuple(tuple);

        underTest = new DtoAssemblerProviderImpl(registry, context);
        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto).isNotNull();
        Assertions.assertThat(orderDto.getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(orderDto.getCustomerName()).isEqualTo("luke");
    }

    @Test
    public void testToDtos() {
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler)new AutoAssembler());

        AssemblerContext context = new AssemblerContext();
        // TODO <pith> : try to add the possibility to pass a List<? extends AggregateRoot<?>>, e.g. List<Order>
        List<AggregateRoot<?>> aggregateRoots = new ArrayList<AggregateRoot<?>>();
        aggregateRoots.add(new Order("lightsaber"));
        aggregateRoots.add(new Order("death star"));
        context.setAggregates(aggregateRoots);

        underTest2 = new DtosAssemblerProviderImpl(registry, context);
        List<OrderDto> orderDtos = underTest2.to(OrderDto.class);

        Assertions.assertThat(orderDtos).isNotNull();
        Assertions.assertThat(orderDtos).isNotEmpty();
        Assertions.assertThat(orderDtos.get(0).getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(orderDtos.get(1).getProduct()).isEqualTo("death star");
    }

    @Test
    @Ignore
    public void testToDtosWithTuple() {
        Tuple tuple1 = Tuples.create(new Order("lightsaber"), new Customer("luke")); // used to get the class of the tuple
        Mockito.when(registry.tupleAssemblerOf(tuple1, OrderDto.class)).thenReturn((Assembler)new AutoTupleAssembler());

        AssemblerContext context = new AssemblerContext();
        Tuple tuple2 = Tuples.create(new Order("death star"), new Customer("dark vador"));
        context.setAggregateTuples(Lists.newArrayList(tuple1, tuple2));

        underTest2 = new DtosAssemblerProviderImpl(registry, context);
        List<OrderDto> orderDtos = underTest2.to(OrderDto.class);

        Assertions.assertThat(orderDtos).isNotNull();
        Assertions.assertThat(orderDtos).isNotEmpty();
        Assertions.assertThat(orderDtos.get(0).getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(orderDtos.get(0).getCustomerName()).isEqualTo("luke");
        Assertions.assertThat(orderDtos.get(1).getProduct()).isEqualTo("death star");
        Assertions.assertThat(orderDtos.get(1).getCustomerName()).isEqualTo("dark vador");
    }

}
