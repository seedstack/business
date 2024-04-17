/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.javatuples.Pair;
import org.seedstack.business.assembler.BaseTupleAssembler;

public class RecipeAssembler extends BaseTupleAssembler<Pair<Order, Customer>, Recipe> {

    @Override
    public void mergeAggregateIntoDto(Pair<Order, Customer> sourceAggregate, Recipe targetDto) {
        targetDto.setOrderId(sourceAggregate.getValue0()
                .getId());
        targetDto.setCustomerId(sourceAggregate.getValue1()
                .getId());
        targetDto.setProduct(sourceAggregate.getValue0()
                .getProduct());
        targetDto.setCustomerName(sourceAggregate.getValue1()
                .getName());
    }

    @Override
    public void mergeDtoIntoAggregate(Recipe sourceDto, Pair<Order, Customer> targetAggregate) {
        targetAggregate.getValue0()
                .setProduct(sourceDto.getProduct());
        targetAggregate.getValue1()
                .setName(sourceDto.getCustomerName());
    }
}
