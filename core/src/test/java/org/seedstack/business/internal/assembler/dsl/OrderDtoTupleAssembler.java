/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Pair;
import org.seedstack.business.assembler.BaseTupleAssembler;
import org.seedstack.business.fixtures.assembler.customer.Customer;
import org.seedstack.business.fixtures.assembler.customer.Order;
import org.seedstack.business.fixtures.assembler.customer.OrderDto;

class OrderDtoTupleAssembler extends BaseTupleAssembler<Pair<Order, Customer>, OrderDto> {

    @Override
    public void mergeAggregateIntoDto(Pair<Order, Customer> sourceAggregate, OrderDto targetDto) {
        targetDto.setOrderId(sourceAggregate.getValue0()
                .getId());
        targetDto.setOrderDate(sourceAggregate.getValue0()
                .getOrderDate());
        targetDto.setPrice(sourceAggregate.getValue0()
                .getPrice());
        targetDto.setProduct(sourceAggregate.getValue0()
                .getProduct());
        targetDto.setCustomerName(sourceAggregate.getValue1()
                .getName());
    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Pair<Order, Customer> targetAggregate) {
        targetAggregate.getValue0()
                .setProduct(sourceDto.getProduct());
        targetAggregate.getValue0()
                .setPrice(sourceDto.getPrice());
        targetAggregate.getValue0()
                .setOrderDate(sourceDto.getOrderDate());
        targetAggregate.getValue1()
                .setName(sourceDto.getCustomerName());
    }
}
