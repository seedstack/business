/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import com.google.common.collect.Lists;
import com.google.inject.name.Named;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.assembler.fixtures.MyAggregateRoot;
import org.seedstack.business.assembler.fixtures.MyAssembler;
import org.seedstack.business.assembler.fixtures.MyUnrestrictedDto;
import org.seedstack.business.core.interfaces.assembler.ModelMapperAssembler;
import org.seedstack.business.internal.interfaces.assembler.DefaultModelMappedAssembler;
import org.seedstack.business.internal.interfaces.assembler.dsl.InternalRegistry;
import org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.AutoAssembler;
import org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.Customer;
import org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.Recipe;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import java.util.List;

/**
 * Tests the DSL internal registry.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@RunWith(SeedITRunner.class)
public class InternalRegistryIT {

    @Inject
    private InternalRegistry registry;

    @Inject
    private Assembler<MyAggregateRoot, MyUnrestrictedDto> expectedAssembler;

    @Inject
    @Named("ModelMapper")
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
        Assembler<?, ?> assembler = registry.assemblerOf(Order.class, OrderDto.class);
        Assertions.assertThat(assembler).isNotNull();
        Assertions.assertThat(assembler).isInstanceOf(ModelMapperAssembler.class);
        Assertions.assertThat(assembler).isInstanceOf(DefaultModelMappedAssembler.class);

        Assertions.assertThat(expectedModelMapperAssembler).isNotNull();
        Assertions.assertThat(assembler.getClass()).isEqualTo(expectedModelMapperAssembler.getClass());
    }

    @Test
    public void testAssemblerOfTuple() {
        List<?> aggregateRootTuple = Lists.newArrayList(org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.Order.class, Customer.class);
        Assembler<?, ?> assembler = registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootTuple, Recipe.class);
        Assertions.assertThat(assembler).isNotNull();
    }

    @Test
    public void testAssemblerOfWithAutomaticAssembler() {
        Assembler<?, ?> assembler = registry.assemblerOf(org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.Order.class
                , org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer.OrderDto.class);
        Assertions.assertThat(assembler).isNotNull();
        Assertions.assertThat(assembler).isInstanceOf(ModelMapperAssembler.class);
        Assertions.assertThat(assembler).isNotInstanceOf(DefaultModelMappedAssembler.class);

        Assertions.assertThat(assembler).isInstanceOf(AutoAssembler.class);
    }
}
