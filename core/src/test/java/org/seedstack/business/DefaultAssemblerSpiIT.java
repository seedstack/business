/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import javax.inject.Inject;
import javax.inject.Named;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.fixtures.assembler.DummyDefaultAssembler;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;
import org.seedstack.business.fixtures.assembler.customer.OrderDtoAssembler;
import org.seedstack.seed.it.SeedITRunner;

@RunWith(SeedITRunner.class)
public class DefaultAssemblerSpiIT {

    @Inject
    @Named("Dummy")
    private Assembler<Order, OrderDto> defaultAssembler;

    @Inject
    @Named("Order")
    private Assembler<Order, OrderDto> customAssembler;

    @Test
    public void test_using_custom_default_assembler() {
        Assertions.assertThat(defaultAssembler)
                .isNotNull();
        Assertions.assertThat(defaultAssembler)
                .isInstanceOf(DummyDefaultAssembler.class);
        OrderDto orderDto = defaultAssembler.createDtoFromAggregate(new Order("id", "product"));
        Assertions.assertThat(orderDto.getOrderId())
                .isEqualTo("hodor");
    }

    @Test
    public void test_custom_assembler() {
        Assertions.assertThat(customAssembler)
                .isNotNull();
        Assertions.assertThat(customAssembler)
                .isInstanceOf(OrderDtoAssembler.class);
        customAssembler.createDtoFromAggregate(new Order("id", "product"));
    }
}
