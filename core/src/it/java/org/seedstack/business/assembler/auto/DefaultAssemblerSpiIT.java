/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.assembler.auto.fixture.*;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@RunWith(SeedITRunner.class)
public class DefaultAssemblerSpiIT {

    @Inject
    @Named("Dummy")
    private Assembler<Order, OrderDto> defaultAssembler;

    @Inject
    private Assembler<Order, OrderDto> customAssembler;

    @Test
    public void test_using_custom_default_assembler() {
        Assertions.assertThat(defaultAssembler).isNotNull();
        Assertions.assertThat(defaultAssembler).isInstanceOf(DummyDefaultAssembler.class);
        OrderDto orderDto = defaultAssembler.assembleDtoFromAggregate(new Order(new Address("street", "city")));
        Assertions.assertThat(orderDto.getId()).isEqualTo("hodor");
    }

    @Test
    public void test_custom_assembler() {
        Assertions.assertThat(customAssembler).isNotNull();
        Assertions.assertThat(customAssembler).isInstanceOf(OrderDtoAssembler.class);
    }
}
