/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.assembler.fixtures.MyService;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderDtoAssembler extends BaseAssembler<Order, OrderDto> {
    @Inject
    private MyService myService;
    @Logging
    private Logger logger;

    @Override
    protected void doAssembleDtoFromAggregate(OrderDto targetDto, Order sourceAggregate) {
        assertThat(myService).isNotNull();
        assertThat(logger).isNotNull();
        targetDto.setId(sourceAggregate.getEntityId());
        targetDto.setBillingAddress(sourceAggregate.getBillingAddress());
    }

    @Override
    protected void doMergeAggregateWithDto(Order targetAggregate, OrderDto sourceDto) {
        assertThat(myService).isNotNull();
        assertThat(logger).isNotNull();
        targetAggregate.setBillingAddress(sourceDto.getBillingAddress());
    }
}
