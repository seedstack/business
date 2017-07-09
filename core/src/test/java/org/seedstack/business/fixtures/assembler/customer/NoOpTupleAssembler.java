/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.javatuples.Pair;
import org.seedstack.business.assembler.BaseTupleAssembler;

import javax.inject.Named;

@Named("noop")
public class NoOpTupleAssembler extends BaseTupleAssembler<Pair<Order, Customer>, OrderDto> {
    @Override
    protected void doAssembleDtoFromAggregate(OrderDto targetDto, Pair<Order, Customer> sourceAggregate) {

    }

    @Override
    protected void doMergeAggregateWithDto(Pair<Order, Customer> targetAggregate, OrderDto sourceDto) {

    }
}
