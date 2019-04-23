/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.javatuples.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.fixtures.assembler.customer.Customer;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.spi.DtoInfoResolver;
import org.seedstack.business.util.Tuples;

public class AssembleSingleProviderImplTest {

    private AssembleSingleImpl<Order, String, Tuple> underTest;
    private AssembleMultipleImpl<Order, String, Tuple> underTest2;
    private Context context;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        DtoInfoResolver dtoInfoResolver = Mockito.mock(DtoInfoResolver.class);
        AssemblerRegistry assemblerRegistry = Mockito.mock(AssemblerRegistry.class);
        Mockito.when(assemblerRegistry.getAssembler(Order.class, OrderDto.class))
                .thenReturn(new OrderDtoAssembler());
        DomainRegistry domainRegistry = Mockito.mock(DomainRegistry.class);
        context = new Context(domainRegistry, assemblerRegistry, Sets.newHashSet(dtoInfoResolver));

        Mockito.when(assemblerRegistry.getTupleAssembler(
                (Class<? extends AggregateRoot<?>>[]) new Class<?>[]{Order.class, Customer.class}, OrderDto.class))
                .thenReturn((Assembler) new OrderDtoTupleAssembler());
    }

    @Test
    public void testToDto() {
        underTest = new AssembleSingleImpl<>(context, new Order("lightsaber"), null);

        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto)
                .isNotNull();
        Assertions.assertThat(orderDto.getProduct())
                .isEqualTo("lightsaber");
    }

    @Test
    public void testToDtoWithTuple() {
        Tuple tuple = Tuples.create(new Order("lightsaber"), new Customer("luke"));

        underTest = new AssembleSingleImpl<>(context, null, tuple);
        OrderDto orderDto = underTest.to(OrderDto.class);

        Assertions.assertThat(orderDto)
                .isNotNull();
        Assertions.assertThat(orderDto.getProduct())
                .isEqualTo("lightsaber");
        Assertions.assertThat(orderDto.getCustomerName())
                .isEqualTo("luke");
    }

    @Test
    public void testToDtos() {
        underTest2 = new AssembleMultipleImpl<>(context, Stream.of(new Order("lightsaber"), new Order("death star")),
                null);

        List<OrderDto> orderDtos = underTest2.toListOf(OrderDto.class);
        Assertions.assertThat(orderDtos)
                .isNotNull();
        Assertions.assertThat(orderDtos)
                .isNotEmpty();
        Assertions.assertThat(orderDtos.get(0)
                .getProduct())
                .isEqualTo("lightsaber");
        Assertions.assertThat(orderDtos.get(1)
                .getProduct())
                .isEqualTo("death star");
    }

    @Test
    public void testToDtosWithTuple() {
        underTest2 = new AssembleMultipleImpl<>(context, null,
                Stream.of(Tuples.create(new Order("lightsaber"), new Customer("luke")),
                        Tuples.create(new Order("death star"), new Customer("dark vador"))));

        List<OrderDto> orderDtos = underTest2.toListOf(OrderDto.class);
        Assertions.assertThat(orderDtos)
                .isNotNull();
        Assertions.assertThat(orderDtos)
                .isNotEmpty();
        Assertions.assertThat(orderDtos.get(0)
                .getProduct())
                .isEqualTo("lightsaber");
        Assertions.assertThat(orderDtos.get(0)
                .getCustomerName())
                .isEqualTo("luke");
        Assertions.assertThat(orderDtos.get(1)
                .getProduct())
                .isEqualTo("death star");
        Assertions.assertThat(orderDtos.get(1)
                .getCustomerName())
                .isEqualTo("dark vador");
    }

}
