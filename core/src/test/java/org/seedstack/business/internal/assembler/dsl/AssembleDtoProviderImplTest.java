/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.javatuples.Tuple;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.Tuples;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AssembleDtoProviderImplTest {

    private AssembleDtoProviderImpl underTest;
    private AssembleDtosProviderImpl underTest2;
    private AssemblerDslContext context = new AssemblerDslContext();

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        InternalRegistry registry = Mockito.mock(InternalRegistryInternal.class);
        context.setRegistry(registry);
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        List<Class<? extends AggregateRoot<?>>> aggregateRootClasses = new ArrayList<Class<? extends AggregateRoot<?>>>();
        aggregateRootClasses.add(Order.class);
        aggregateRootClasses.add(Customer.class);
        Mockito.when(registry.tupleAssemblerOf(aggregateRootClasses, OrderDto.class)).thenReturn((Assembler) new AutoTupleAssembler());
    }

    @Test
    public void testToDto() {
        underTest = new AssembleDtoProviderImpl(context, new Order("lightsaber"));

        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto).isNotNull();
        Assertions.assertThat(orderDto.getProduct()).isEqualTo("lightsaber");
    }

    @Test
    @Ignore
    public void testToDtoWithTuple() {
        Tuple tuple = Tuples.create(new Order("lightsaber"), new Customer("luke"));

        underTest = new AssembleDtoProviderImpl(context, tuple);
        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto).isNotNull();
        Assertions.assertThat(orderDto.getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(orderDto.getCustomerName()).isEqualTo("luke");
    }

    @Test
    public void testToDtos() {
        List<Order> aggregateRoots = new ArrayList<Order>();
        aggregateRoots.add(new Order("lightsaber"));
        aggregateRoots.add(new Order("death star"));

        underTest2 = new AssembleDtosProviderImpl(context, aggregateRoots, null);
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
        Tuple tuple2 = Tuples.create(new Order("death star"), new Customer("dark vador"));

        underTest2 = new AssembleDtosProviderImpl(context, null, Lists.newArrayList(tuple1, tuple2));
        List<OrderDto> orderDtos = underTest2.to(OrderDto.class);

        Assertions.assertThat(orderDtos).isNotNull();
        Assertions.assertThat(orderDtos).isNotEmpty();
        Assertions.assertThat(orderDtos.get(0).getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(orderDtos.get(0).getCustomerName()).isEqualTo("luke");
        Assertions.assertThat(orderDtos.get(1).getProduct()).isEqualTo("death star");
        Assertions.assertThat(orderDtos.get(1).getCustomerName()).isEqualTo("dark vador");
    }

}
