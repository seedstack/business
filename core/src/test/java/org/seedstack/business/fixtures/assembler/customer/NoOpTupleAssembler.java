/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import javax.inject.Named;
import org.javatuples.Pair;
import org.seedstack.business.assembler.BaseTupleAssembler;

@Named("noop")
public class NoOpTupleAssembler extends BaseTupleAssembler<Pair<Order, Customer>, OrderDto> {

    @Override
    public void mergeAggregateIntoDto(Pair<Order, Customer> sourceAggregate, OrderDto targetDto) {

    }

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Pair<Order, Customer> targetAggregate) {

    }
}
