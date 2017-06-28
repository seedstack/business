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


@Named("Order") // just to test the DSL with qualifier
public class OrderAssembler extends BaseAssembler<Order, OrderDto> {

    @Override
    protected void doAssembleDtoFromAggregate(OrderDto targetDto, Order sourceAggregate) {
        targetDto.setOrderId(sourceAggregate.getOrderId());
        targetDto.setOtherDetails(sourceAggregate.getOtherDetails());
        targetDto.setPrice(sourceAggregate.getPrice());
        targetDto.setProduct(sourceAggregate.getProduct());
        targetDto.setOrderDate(sourceAggregate.getOrderDate());
    }

    @Override
    protected void doMergeAggregateWithDto(Order targetAggregate, OrderDto sourceDto) {
        targetAggregate.setProduct(sourceDto.getProduct());
        targetAggregate.setOtherDetails(sourceDto.getOtherDetails());
        targetAggregate.setOrderDate(sourceDto.getOrderDate());
        targetAggregate.setPrice(sourceDto.getPrice());
        targetAggregate.setOrderDate(sourceDto.getOrderDate());
        // identity never change
    }
}
