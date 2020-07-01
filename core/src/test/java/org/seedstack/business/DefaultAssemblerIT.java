/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.fixtures.assembler.DummyDefaultAssembler;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderDtoAssembler;
import org.seedstack.business.spi.GenericImplementation;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class DefaultAssemblerIT {
    @Inject
    @Named("Dummy")
    private Assembler<Order, OrderDto> defaultAssembler;

    @Inject
    @Named("Order")
    private Assembler<Order, OrderDto> customAssembler;

    @Inject
    @Named("configured")
    private Assembler<SomeAggregate, ConfiguredDto> qualifiedAssembler;

    @Inject
    private Assembler<SomeAggregate, ConfiguredDto> configuredAssembler;

    @Inject
    private Assembler<SomeAggregate, ExplicitDto> explicitAssembler;

    @Test
    public void testUsingCustomDefaultAssembler() {
        assertThat(defaultAssembler)
                .isInstanceOf(DummyDefaultAssembler.class);
        OrderDto orderDto = defaultAssembler.createDtoFromAggregate(new Order("id", "product"));
        assertThat(orderDto.getOrderId())
                .isEqualTo("hodor");
    }

    @Test
    public void testCustomAssembler() {
        assertThat(customAssembler)
                .isInstanceOf(OrderDtoAssembler.class);
        customAssembler.createDtoFromAggregate(new Order("id", "product"));
    }

    @Test
    public void testConfigQualifier() {
        assertThat(qualifiedAssembler).isInstanceOf(SomeDefaultAssembler.class);
        assertThat(configuredAssembler).isInstanceOf(SomeDefaultAssembler.class);
        assertThat(explicitAssembler).isInstanceOf(SomeExplicitAssembler.class);
    }

    @GenericImplementation
    @Named("configured")
    public static class SomeDefaultAssembler<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D> {
        @Override
        public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {

        }

        @Override
        public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {

        }
    }

    public static class SomeExplicitAssembler extends BaseAssembler<SomeAggregate, ExplicitDto> {
        @Override
        public void mergeAggregateIntoDto(SomeAggregate sourceAggregate, ExplicitDto targetDto) {

        }

        @Override
        public void mergeDtoIntoAggregate(ExplicitDto sourceDto, SomeAggregate targetAggregate) {

        }
    }

    public static class ExplicitDto {
    }

    @DtoOf(SomeAggregate.class)
    public static class ConfiguredDto {
    }

    public static class SomeAggregate extends BaseAggregateRoot<String> {
    }
}
