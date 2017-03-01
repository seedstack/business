/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.assembler.customer.AutoAssembler;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderFactory;
import org.seedstack.business.fixtures.assembler.customer.OrderFactoryInternal;

import java.util.Optional;

import static org.junit.Assert.fail;

public class MergeSingleAggregateFromRepositoryImplTest {

    private MergeSingleSingleAggregateFromRepositoryImpl underTest;

    private InternalRegistry registry;
    private Order order;
    private OrderFactory orderFactory;
    private Repository<Order, String> repository;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        registry = Mockito.mock(InternalRegistryInternal.class);
        AssemblerDslContext context = new AssemblerDslContext();
        context.setRegistry(registry);
        repository = Mockito.mock(Repository.class);
        orderFactory = new OrderFactoryInternal();
        order = new Order("1", "death star");

        Mockito.when(registry.repositoryOf(Order.class)).thenReturn((Repository) repository);
        Mockito.when(registry.genericFactoryOf(Order.class)).thenReturn((Factory) orderFactory);
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        underTest = new MergeSingleSingleAggregateFromRepositoryImpl<>(context, Order.class, new OrderDto("1", "lightsaber"));
    }

    @Test
    public void testFromFactory() throws Exception {
        AggregateRoot<?> aggregateRoot = underTest.fromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");
    }

    @Test
    public void testOrFailOK() {
        Mockito.when(repository.get("1")).thenReturn(Optional.of(order));

        AggregateRoot<?> aggregateRoot = null;
        try {
            aggregateRoot = underTest.fromRepository().orFail();
        } catch (AggregateNotFoundException e) {
            fail();
        }

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");
    }

    @Test
    public void testOrFailKO() {
        Mockito.when(repository.get("1")).thenReturn(Optional.empty());

        try {
            underTest.fromRepository().orFail();
            fail();
        } catch (AggregateNotFoundException e) {
            Assertions.assertThat(e.getMessage()).isNotEmpty();
        }

    }

    @Test
    public void testThenFromFactory() throws Exception {
        // Get it by the repository first
        Mockito.when(repository.get("1")).thenReturn(Optional.of(order));

        AggregateRoot<?> aggregateRoot = underTest.fromRepository().orFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");

        // Then from the factory
        Mockito.when(repository.get("1")).thenReturn(Optional.of(order));

        aggregateRoot = underTest.fromRepository().orFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");

    }

}