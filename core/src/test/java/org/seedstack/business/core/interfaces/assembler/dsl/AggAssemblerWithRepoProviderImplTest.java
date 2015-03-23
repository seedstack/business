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

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.AggregateNotFoundException;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.*;

import static org.junit.Assert.fail;

public class AggAssemblerWithRepoProviderImplTest {

    private AggAssemblerWithRepoProviderImpl underTest;

    private InternalRegistry registry;
    private Order order;
    private OrderFactory orderFactory;
    private Repository<Order, String> repository;

    @Before
    public void before() {
        registry = Mockito.mock(InternalRegistryInternal.class);
        repository = Mockito.mock(Repository.class);
        orderFactory = new OrderFactoryInternal();
        order = new Order("1", "death star");

        AssemblerContext context = new AssemblerContext();
        context.setDto(new OrderDto("1", "lightsaber"));
        context.setAggregateClass(Order.class);

        Mockito.when(registry.repositoryOf(Order.class)).thenReturn((Repository) repository);
        Mockito.when(registry.genericFactoryOf(Order.class)).thenReturn((GenericFactory)orderFactory);
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        //Mockito.when(injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(Factory.class, aggregateIdClass))))).thenReturn((Assembler)new AutoAssembler());
        underTest = new AggAssemblerWithRepoProviderImpl(registry, context);
    }

    @Test
    public void testFromFactory() throws Exception {
        AggregateRoot<?> aggregateRoot = underTest.fromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");
    }

    @Test
    public void testOrFailOK(){
        Mockito.when(repository.load("1")).thenReturn(order);

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
    public void testOrFailKO(){
        Mockito.when(repository.load("1")).thenReturn(null);

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
        Mockito.when(repository.load("1")).thenReturn(order);

        AggregateRoot<?> aggregateRoot = underTest.fromRepository().thenFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");

        // Then from the factory
        Mockito.when(repository.load("1")).thenReturn(order);

        aggregateRoot = underTest.fromRepository().thenFromFactory();

        Assertions.assertThat(aggregateRoot).isNotNull();
        Assertions.assertThat(aggregateRoot).isEqualTo(order);

        Assertions.assertThat(((Order) aggregateRoot).getProduct()).isEqualTo("lightsaber");

    }

}