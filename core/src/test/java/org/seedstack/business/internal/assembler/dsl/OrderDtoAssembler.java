/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;

class OrderDtoAssembler extends BaseAssembler<Order, OrderDto> {

    @Override
    public void mergeAggregateIntoDto(Order sourceAggregate, OrderDto targetDto) {
        targetDto.setOrderId(sourceAggregate.getId());
        targetDto.setOrderDate(sourceAggregate.getOrderDate());
        targetDto.setPrice(sourceAggregate.getPrice());
        targetDto.setProduct(sourceAggregate.getProduct());
    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Order targetAggregate) {
        targetAggregate.setProduct(sourceDto.getProduct());
        targetAggregate.setPrice(sourceDto.getPrice());
        targetAggregate.setOrderDate(sourceDto.getOrderDate());
    }
}
