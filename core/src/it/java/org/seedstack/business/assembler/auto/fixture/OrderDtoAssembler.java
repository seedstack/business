/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.auto.fixture;

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class OrderDtoAssembler extends BaseAssembler<Order, OrderDto> {

    @Override
    protected void doAssembleDtoFromAggregate(OrderDto targetDto, Order sourceAggregate) {
        targetDto.setId(sourceAggregate.getEntityId());
        targetDto.setBillingAddress(sourceAggregate.getBillingAddress());
    }

    @Override
    protected void doMergeAggregateWithDto(Order targetAggregate, OrderDto sourceDto) {
        targetAggregate.setBillingAddress(sourceDto.getBillingAddress());
    }
}
