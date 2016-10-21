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
import org.fest.reflect.core.Reflection;
import org.fest.reflect.reference.TypeRef;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.MergeTupleWithRepositoryProvider;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.assembler.DefaultModelMapperTupleAssembler;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.AutoAssembler;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.Customer;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.Order;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.OrderDto;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.Recipe;

import java.util.Arrays;
import java.util.List;


public class MergeAggregateOrTupleProviderImplTest {

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
        MergeAggregateOrTupleProviderImpl<OrderDto> underTest = new MergeAggregateOrTupleProviderImpl<>(context, new OrderDto("1", "lightsaber"));

        Order order = new Order();
        underTest.into(order);

        Assertions.assertThat(order.getProduct()).isEqualTo("lightsaber");
    }

    @Test
    public void testToAggregateTuple() {

        Recipe recipe = new Recipe("customer1", "luke", "order1", "lightsaber");
        MergeAggregateOrTupleProviderImpl<Recipe> underTest = new MergeAggregateOrTupleProviderImpl<>(context, recipe);
        Order order = new Order();
        Customer customer = new Customer("customer1");
        underTest.into(order, customer);

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getProduct()).isEqualTo("lightsaber");
        Assertions.assertThat(customer.getEntityId()).isEqualTo("customer1");
        Assertions.assertThat(customer.getName()).isEqualTo("luke");
    }

    @Test
    public void testToAggregateClass() {
        Recipe dto = new Recipe("customer1", "luke", "order1", "lightsaber");
        MergeAggregateOrTupleProviderImpl<Recipe> underTest = new MergeAggregateOrTupleProviderImpl<>(context, dto);

        assertToMethod(underTest.into(Order.class, Customer.class), dto, Order.class, Customer.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class);
        assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
    }

    @Test
    public void testToAggregateClassWithNullValue() {
        Recipe dto = new Recipe("customer1", "luke", "order1", "lightsaber");
        MergeAggregateOrTupleProviderImpl<Recipe> underTest = new MergeAggregateOrTupleProviderImpl<>(context, dto);

        assertToMethod(underTest.into(null, null, null, Customer.class), dto, null, null, null, Customer.class);
    }

    private void assertToMethod(MergeTupleWithRepositoryProvider<?> to, Object dto, Class<?>... classes) {
        List<Class<? extends AggregateRoot<?>>> aggregateClasses = Reflection.field("aggregateClasses").ofType(new TypeRef<List<Class<? extends AggregateRoot<?>>>>() {}).in(to).get();
        Assertions.assertThat(aggregateClasses).isEqualTo(Arrays.asList(classes));
        Object obj = Reflection.field("dto").ofType(Object.class).in(to).get();
        Assertions.assertThat(obj).isEqualTo(dto);
    }
}
