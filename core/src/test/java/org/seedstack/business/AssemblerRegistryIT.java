/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import com.google.inject.name.Names;
import javax.inject.Inject;
import javax.inject.Named;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.fixtures.assembler.MyAggregateRoot;
import org.seedstack.business.fixtures.assembler.MyAssembler;
import org.seedstack.business.fixtures.assembler.MyUnrestrictedDto;
import org.seedstack.business.fixtures.assembler.customer.Customer;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.seed.it.SeedITRunner;

@RunWith(SeedITRunner.class)
public class AssemblerRegistryIT {

  @Inject
  private AssemblerRegistry registry;
  @Inject
  private Assembler<MyAggregateRoot, MyUnrestrictedDto> expectedAssembler;
  @Inject
  @Named("special")
  private Assembler<Order, OrderDto> expectedSpecialAssembler;

  @Test
  public void testAssemblerOfWithProvidedAssembler() {
    Assembler<?, ?> assembler = registry
      .getAssembler(MyAggregateRoot.class, MyUnrestrictedDto.class);
    Assertions.assertThat(assembler).isNotNull();
    Assertions.assertThat(assembler).isInstanceOf(BaseAssembler.class);
    Assertions.assertThat(assembler).isInstanceOf(MyAssembler.class);

    Assertions.assertThat(expectedAssembler).isNotNull();
    Assertions.assertThat(assembler.getClass()).isEqualTo(expectedAssembler.getClass());
  }

  @Test
  public void testAssemblerOfWithDefaultAssembler() {
    Assembler<?, ?> assembler = registry
      .getAssembler(Order.class, OrderDto.class, Names.named("special"));
    Assertions.assertThat(assembler).isNotNull();
    Assertions.assertThat(assembler).isInstanceOf(SpecialAssembler.class);
    Assertions.assertThat(assembler).isInstanceOf(DefaultSpecialAssembler.class);

    Assertions.assertThat(expectedSpecialAssembler).isNotNull();
    Assertions.assertThat(assembler.getClass()).isEqualTo(expectedSpecialAssembler.getClass());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testAssemblerOfTuple() {
    Class<? extends AggregateRoot<?>>[] aggregateRootTuple = new Class[]{Order.class,
      Customer.class};
    Assembler<?, ?> assembler = registry.getTupleAssembler(aggregateRootTuple, OrderDto.class);
    Assertions.assertThat(assembler).isNotNull();
  }

  @Test
  public void testAssemblerOfWithAutomaticAssembler() {
    Assembler<?, ?> assembler = registry
      .getAssembler(Order.class, OrderDto.class, Names.named("custom"));
    Assertions.assertThat(assembler).isNotNull();
    Assertions.assertThat(assembler).isInstanceOf(SpecialAssembler.class);
    Assertions.assertThat(assembler).isNotInstanceOf(DefaultSpecialAssembler.class);

    Assertions.assertThat(assembler).isInstanceOf(CustomSpecialAssembler.class);
  }

  static abstract class SpecialAssembler<A extends AggregateRoot<?>, D> extends
    BaseAssembler<A, D> {

  }

  @Named("custom")
  static class CustomSpecialAssembler extends SpecialAssembler<Order, OrderDto> {

    @Override
    public void mergeAggregateIntoDto(Order sourceAggregate, OrderDto targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Order targetAggregate) {

    }
  }

  @GenericImplementation
  @Named("special")
  public static class DefaultSpecialAssembler<A extends AggregateRoot<?>, D> extends
    SpecialAssembler<A, D> {

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {

    }
  }
}
