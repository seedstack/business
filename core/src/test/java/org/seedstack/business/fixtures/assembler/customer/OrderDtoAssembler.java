/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import javax.inject.Named;
import org.seedstack.business.assembler.BaseAssembler;

@Named("Order") // just to test the DSL with qualifier
public class OrderDtoAssembler extends BaseAssembler<Order, OrderDto> {

    @Override
    public void mergeAggregateIntoDto(Order sourceAggregate, OrderDto targetDto) {
        targetDto.setOrderId(sourceAggregate.getId());
        targetDto.setPrice(sourceAggregate.getPrice());
        targetDto.setProduct(sourceAggregate.getProduct());
        targetDto.setOrderDate(sourceAggregate.getOrderDate());
    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Order targetAggregate) {
        targetAggregate.setProduct(sourceDto.getProduct());
        targetAggregate.setOrderDate(sourceDto.getOrderDate());
        targetAggregate.setPrice(sourceDto.getPrice());
        targetAggregate.setOrderDate(sourceDto.getOrderDate());
        // identity never change
    }
}
