/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.assembler.BaseAssembler;

import javax.inject.Named;

@Named("noop")
public class NoOpAssembler extends BaseAssembler<Order, OrderDto> {
    @Override
    public void mergeAggregateIntoDto(Order sourceAggregate, OrderDto targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Order targetAggregate) {

    }
}
