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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.AutoAssembler;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.Order;
import org.seedstack.business.core.interfaces.assembler.dsl.fixture.OrderDto;

import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggAssemblerProviderImplTest {

    private AggAssemblerProviderImpl underTest;

    private InternalRegistry registry;

    @Before
    public void before() {
        registry = Mockito.mock(InternalRegistryInternal.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testToAggregate() {
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        AssemblerContext context = new AssemblerContext();
        context.setDto(new OrderDto("1", "lightsaber"));

        underTest = new AggAssemblerProviderImpl(registry, context);
        Order order = underTest.to(new Order());

        Assertions.assertThat(order.getProduct()).isEqualTo("lightsaber");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testToAggregateTuple() {
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());

        AssemblerContext context = new AssemblerContext();
        context.setDtos(Lists.newArrayList(new OrderDto("1", "lightsaber"), new OrderDto("1", "death star")));

        underTest = new AggAssemblerProviderImpl(registry, context);
        List<Order> order = underTest.to(Lists.newArrayList(new Order(), new Order()));

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order).isNotEmpty();
        Assertions.assertThat(order.get(0).getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(order.get(1).getProduct()).isEqualTo("death star");
    }
}
