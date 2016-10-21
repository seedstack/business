/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.business.assembler.fixtures.MyAggregateRoot;
import org.seedstack.business.assembler.fixtures.MyAssembler;
import org.seedstack.business.assembler.fixtures.MyUnrestrictedDto;
import org.seedstack.business.assembler.modelmapper.ModelMapperAssembler;
import org.seedstack.business.internal.assembler.DefaultModelMapperAssembler;
import org.seedstack.business.internal.assembler.dsl.InternalRegistry;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.AutoAssembler;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.Customer;
import org.seedstack.business.internal.assembler.dsl.fixture.customer.Recipe;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.List;

/**
 * Tests the DSL internal registry.
 */
@RunWith(SeedITRunner.class)
public class InternalRegistryIT {

    @Inject
    private InternalRegistry registry;

    @Inject
    private Assembler<MyAggregateRoot, MyUnrestrictedDto> expectedAssembler;

    @Inject
    @ModelMapper
    private Assembler<Order, OrderDto> expectedModelMapperAssembler;

    @Test
    public void testAssemblerOfWithProvidedAssembler() {
        Assembler<?, ?> assembler = registry.assemblerOf(MyAggregateRoot.class, MyUnrestrictedDto.class);
        Assertions.assertThat(assembler).isNotNull();
        Assertions.assertThat(assembler).isInstanceOf(BaseAssembler.class);
        Assertions.assertThat(assembler).isInstanceOf(MyAssembler.class);

        Assertions.assertThat(expectedAssembler).isNotNull();
        Assertions.assertThat(assembler.getClass()).isEqualTo(expectedAssembler.getClass());
    }

    static class Order implements AggregateRoot<String> {
        @Override
        public String getEntityId() { return null; }
    }

    @DtoOf(Order.class)
    static class OrderDto  { }

    @Test
    public void testAssemblerOfWithDefaultAssembler() {
        Assembler<?, ?> assembler = registry.assemblerOf(Order.class, OrderDto.class, ModelMapper.class);
        Assertions.assertThat(assembler).isNotNull();
        Assertions.assertThat(assembler).isInstanceOf(ModelMapperAssembler.class);
        Assertions.assertThat(assembler).isInstanceOf(DefaultModelMapperAssembler.class);

        Assertions.assertThat(expectedModelMapperAssembler).isNotNull();
        Assertions.assertThat(assembler.getClass()).isEqualTo(expectedModelMapperAssembler.getClass());
    }

    @Test
    public void testAssemblerOfTuple() {
        List<?> aggregateRootTuple = Lists.newArrayList(org.seedstack.business.internal.assembler.dsl.fixture.customer.Order.class, Customer.class);
        Assembler<?, ?> assembler = registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootTuple, Recipe.class, ModelMapper.class);
        Assertions.assertThat(assembler).isNotNull();
    }

    @Test
    public void testAssemblerOfWithAutomaticAssembler() {
        Assembler<?, ?> assembler = registry.assemblerOf(org.seedstack.business.internal.assembler.dsl.fixture.customer.Order.class
                , org.seedstack.business.internal.assembler.dsl.fixture.customer.OrderDto.class);
        Assertions.assertThat(assembler).isNotNull();
        Assertions.assertThat(assembler).isInstanceOf(ModelMapperAssembler.class);
        Assertions.assertThat(assembler).isNotInstanceOf(DefaultModelMapperAssembler.class);

        Assertions.assertThat(assembler).isInstanceOf(AutoAssembler.class);
    }
}
