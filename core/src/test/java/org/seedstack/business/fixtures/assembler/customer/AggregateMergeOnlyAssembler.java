/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import javax.inject.Named;

import org.seedstack.business.assembler.BaseAssembler;

@Named("AggregateAssemble")
public class AggregateMergeOnlyAssembler extends BaseAssembler<Order, OrderDto> {

    @Override
    public void mergeDtoIntoAggregate(OrderDto sourceDto, Order targetAggregate) {
        // NOOP
    }

}
