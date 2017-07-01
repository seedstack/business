/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.reference.TypeRef;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.assembler.AbstractBaseAssembler;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.seed.core.internal.guice.BindingStrategy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DefaultAssemblerCollectorTest {

    private DefaultAssemblerCollector underTest;

    @Before
    public void before() {
        List<Class<? extends Assembler>> defaultAssemblers = new ArrayList<>();
        defaultAssemblers.add(DefaultAssemblerFixture1.class);
        defaultAssemblers.add(DefaultAssemblerFixture2.class);
        underTest = new DefaultAssemblerCollector(defaultAssemblers);
    }

    @Test
    public void testCollect() {
        Collection<BindingStrategy> strategies = underTest.collect(Lists.newArrayList(Dto1.class, Dto2.class, Dto3.class));
        Assertions.assertThat(strategies).hasSize(2); // 2 default assembler implementation
        Collection<Type[]> typeVariables = Reflection.field("constructorParams").ofType(new TypeRef<Collection<Type[]>>() {
        }).in(strategies.iterator().next()).get();
        Assertions.assertThat(typeVariables).hasSize(3); // 3 dtos
    }

    @Test
    public void testCollectIllegalArgument() {
        Collection<BindingStrategy> strategies = underTest.collect(Lists.newArrayList(Dto4.class));
        Assertions.assertThat(strategies).isNotNull();
        Assertions.assertThat(strategies).isEmpty();
    }

    private static class Agg1 extends BaseAggregateRoot<Integer> {
    }

    private static class Agg2 extends BaseAggregateRoot<Integer> {
    }

    @DtoOf(Agg1.class)
    private static class Dto1 {
    }

    @DtoOf(Agg1.class)
    private static class Dto2 {
    }

    @DtoOf(Agg2.class)
    private static class Dto3 {
    }

    private static class Dto4 {
    }

    private static class DefaultAssemblerFixture1<A extends AggregateRoot<?>, D> extends AbstractBaseAssembler<A, D> {
        @Override
        public D assembleDtoFromAggregate(A sourceAggregate) {
            return null;
        }

        @Override
        public void assembleDtoFromAggregate(D targetDto, A sourceAggregate) {
        }

        @Override
        public void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        }
    }

    private static class DefaultAssemblerFixture2<A extends AggregateRoot<?>, D> extends AbstractBaseAssembler<A, D> {
        @Override
        public D assembleDtoFromAggregate(A sourceAggregate) {
            return null;
        }

        @Override
        public void assembleDtoFromAggregate(D targetDto, A sourceAggregate) {
        }

        @Override
        public void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        }
    }
}
