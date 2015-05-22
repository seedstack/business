/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.TupleAggAssemblerWithRepoProvider;
import org.seedstack.business.internal.interfaces.assembler.DefaultModelMapperTupleAssembler;
import org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AggAssemblerProviderImplTest {

    private AssemblerDslContext context = new AssemblerDslContext();

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        InternalRegistry registry = Mockito.mock(InternalRegistryInternal.class);
        Mockito.when(registry.assemblerOf(Order.class, OrderDto.class)).thenReturn((Assembler) new AutoAssembler());
        context.setRegistry(registry);

        List<?> aggregateRootTuple = Lists.newArrayList(Order.class, Customer.class);
        Mockito.when(registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootTuple, Recipe.class)).
                thenReturn((Assembler) new DefaultModelMapperTupleAssembler<Pair<Order, Customer>, Recipe>(new Object[]{null, Recipe.class}));
    }

    @Test
    public void testToAggregate() {
        AggAssemblerProviderImpl<OrderDto> underTest = new AggAssemblerProviderImpl<OrderDto>(context, new OrderDto("1", "lightsaber"));

        Order order = underTest.to(new Order());

        Assertions.assertThat(order.getProduct()).isEqualTo("lightsaber");
    }

    @Test
    public void testToAggregateTuple() {

        Recipe recipe = new Recipe("customer1", "luke", "order1", "lightsaber");
        AggAssemblerProviderImpl<Recipe> underTest = new AggAssemblerProviderImpl<Recipe>(context, recipe);
        Pair<Order, Customer> order = underTest.to(new Order(), new Customer("customer1"));

        Assertions.assertThat((Iterable<?>) order).isNotNull();
        Assertions.assertThat((Iterable<?>) order).isNotEmpty();
        Assertions.assertThat(order.getValue0().getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(order.getValue1().getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(order.getValue1().getName()).isEqualTo("luke");
    }

    @Test
    public void testToUnTypedTuple() {

        Recipe recipe = new Recipe("customer1", "luke", "order1", "lightsaber");
        AggAssemblerProviderImpl<Recipe> underTest = new AggAssemblerProviderImpl<Recipe>(context, recipe);
        Tuple order = underTest.to(Tuples.create(new Order(), new Customer("customer1")));

        Assertions.assertThat((Iterable<?>) order).isNotNull();
        Assertions.assertThat((Iterable<?>) order).isNotEmpty();
        Assertions.assertThat(((Order) order.getValue(0)).getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(((Customer) order.getValue(1)).getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(((Customer) order.getValue(1)).getName()).isEqualTo("luke");
    }

    @Test
    public void testToAggregateClass() {
        Recipe dto = new Recipe("customer1", "luke", "order1", "lightsaber");
        AggAssemblerProviderImpl<Recipe> underTest = new AggAssemblerProviderImpl<Recipe>(context, dto);

        assertToMethod(underTest.to(Order.class, Customer.class), dto, Order.class, Customer.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.to(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
    }

    @Test
    public void testToAggregateClassWithNullValue() {
        Recipe dto = new Recipe("customer1", "luke", "order1", "lightsaber");
        AggAssemblerProviderImpl<Recipe> underTest = new AggAssemblerProviderImpl<Recipe>(context, dto);

        assertToMethod(underTest.to(null, null, null, Customer.class), dto, null, null, null, Customer.class);
    }

    private void assertToMethod(TupleAggAssemblerWithRepoProvider<?> to, Object dto, Class<?>... classes) {
        List<Class<? extends AggregateRoot<?>>> aggregateClasses = Whitebox.getInternalState(to, "aggregateClasses");
        Assertions.assertThat(aggregateClasses).isEqualTo(Arrays.asList(classes));
        Object obj = Whitebox.getInternalState(to, "dto");
        Assertions.assertThat(obj).isEqualTo(dto);
    }
}
