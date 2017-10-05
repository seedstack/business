/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler;

import static org.mockito.Mockito.mock;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.reference.TypeRef;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.guice.BindingStrategy;

public class DefaultAssemblerCollectorTest {
    private DefaultAssemblerCollector underTest;
    private Application application;

    @Before
    public void before() {
        List<Class<? extends Assembler>> defaultAssemblers = new ArrayList<>();
        defaultAssemblers.add(DefaultAssemblerFixture1.class);
        defaultAssemblers.add(DefaultAssemblerFixture2.class);
        underTest = new DefaultAssemblerCollector(defaultAssemblers);
        application = mock(Application.class);
    }

    @Test
    public void testCollect() {
        Collection<BindingStrategy> strategies = underTest.collect(application,
                Lists.newArrayList(Dto1.class, Dto2.class, Dto3.class));
        Assertions.assertThat(strategies)
                .hasSize(2); // 2 default assembler implementation
        Map<Type[], Key<?>> typeVariables = Reflection.field("constructorParamsMap")
                .ofType(new TypeRef<Map<Type[], Key<?>>>() {})
                .in(strategies.iterator().next())
                .get();
        Assertions.assertThat(typeVariables)
                .hasSize(3); // 3 dtos
    }

    @Test
    public void testCollectIllegalArgument() {
        Collection<BindingStrategy> strategies = underTest.collect(application, Lists.newArrayList(Dto4.class));
        Assertions.assertThat(strategies)
                .isNotNull();
        Assertions.assertThat(strategies)
                .isEmpty();
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

    private static class DefaultAssemblerFixture1<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D> {

        @Override
        public D createDtoFromAggregate(A sourceAggregate) {
            return null;
        }

        @Override
        public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        }

        @Override
        public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
        }
    }

    private static class DefaultAssemblerFixture2<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D> {

        @Override
        public D createDtoFromAggregate(A sourceAggregate) {
            return null;
        }

        @Override
        public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        }

        @Override
        public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
        }
    }
}
