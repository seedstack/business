/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Sets;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.fest.reflect.core.Reflection;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.fixtures.assembler.customer.Customer;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.Recipe;
import org.seedstack.business.fixtures.assembler.customer.RecipeAssembler;
import org.seedstack.business.spi.DtoInfoResolver;

public class MergeSingleImplTest {

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
      (Class<? extends AggregateRoot<?>>[]) new Class<?>[]{Order.class, Customer.class},
      Recipe.class)
    ).thenReturn((Assembler) new RecipeAssembler());
  }

  @Test
  public void testToAggregate() {
    MergeSingleImpl<OrderDto> underTest = new MergeSingleImpl<>(context,
      new OrderDto("1", "lightsaber"));

    Order order = new Order();
    underTest.into(order);

    Assertions.assertThat(order.getProduct()).isEqualTo("lightsaber");
  }

  @Test
  public void testToAggregateTuple() {
    Recipe recipe = new Recipe("customer1", "luke", "order1", "lightsaber");
    MergeSingleImpl<Recipe> underTest = new MergeSingleImpl<>(context, recipe);
    Pair<Order, Customer> pair = Pair.with(new Order(), new Customer("customer1"));
    underTest.into(pair);

    Assertions.assertThat(pair.getValue0()).isNotNull();
    Assertions.assertThat(pair.getValue0().getProduct()).isEqualTo("lightsaber");
    Assertions.assertThat(pair.getValue1().getId()).isEqualTo("customer1");
    Assertions.assertThat(pair.getValue1().getName()).isEqualTo("luke");
  }

  @Test
  public void testToAggregateClass() {
    Recipe dto = new Recipe("customer1", "luke", "order1", "lightsaber");
    MergeSingleImpl<Recipe> underTest = new MergeSingleImpl<>(context, dto);

    assertToMethod(underTest.into(Order.class, Customer.class), dto, Order.class, Customer.class);
    assertToMethod(underTest.into(Order.class, Customer.class, Order.class), dto, Order.class,
      Customer.class, Order.class);
    assertToMethod(underTest.into(Order.class, Customer.class, Order.class, Customer.class), dto,
      Order.class, Customer.class, Order.class, Customer.class);
    assertToMethod(
      underTest.into(Order.class, Customer.class, Order.class, Customer.class, Order.class), dto,
      Order.class, Customer.class, Order.class, Customer.class, Order.class);
    assertToMethod(underTest
        .into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer
          .class),
      dto, Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class);
    assertToMethod(underTest
        .into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class,
          Order.class), dto, Order.class, Customer.class, Order.class, Customer.class, Order.class,
      Customer.class, Order.class);
    assertToMethod(underTest
        .into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class,
          Order.class, Customer.class), dto, Order.class, Customer.class, Order.class, Customer
        .class,
      Order.class, Customer.class, Order.class, Customer.class);
    assertToMethod(underTest
        .into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class,
          Order.class, Customer.class, Order.class), dto, Order.class, Customer.class, Order.class,
      Customer.class, Order.class, Customer.class, Order.class, Customer.class, Order.class);
    assertToMethod(underTest
        .into(Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class,
          Order.class, Customer.class, Order.class, Customer.class), dto, Order.class, Customer
        .class,
      Order.class, Customer.class, Order.class, Customer.class, Order.class, Customer.class,
      Order.class, Customer.class);
  }

  @Test(expected = NullPointerException.class)
  public void testToAggregateClassWithNullValue() {
    new MergeSingleImpl<>(context, new Recipe("customer1", "luke", "order1", "lightsaber"))
      .into(Customer.class, null);
  }

  private void assertToMethod(MergeFromRepository<?> to, Object dto, Class<?>... classes) {
    if (to instanceof MergeMultipleTuplesFromRepositoryImpl) {
      assertMultiple(to, dto, classes);
    } else if (to instanceof MergeSingleTupleFromRepositoryImpl) {
      assertMultiple(
        Reflection.field("multipleMerger").ofType(MergeMultipleTuplesFromRepositoryImpl.class)
          .in(to).get(), dto, classes);
    }
  }

  @SuppressWarnings("unchecked")
  private void assertMultiple(MergeFromRepository<?> to, Object dto, Class<?>[] classes) {
    Class<? extends AggregateRoot<?>>[] aggregateClasses = Reflection.field("aggregateClasses")
      .ofType(Class[].class).in(to).get();
    Assertions.assertThat(aggregateClasses).isEqualTo(classes);
    Stream<Object> stream = Reflection.field("dtoStream").ofType(Stream.class).in(to).get();
    Assertions.assertThat(stream.findFirst()).contains(dto);
  }
}
