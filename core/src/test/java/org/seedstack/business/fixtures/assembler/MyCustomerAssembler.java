/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.fixtures.domain.customer.Customer;

public class MyCustomerAssembler extends BaseAssembler<Customer, MyCustomerRepresentation> {

    @Override
    public void mergeAggregateIntoDto(Customer sourceEntity, MyCustomerRepresentation targetDto) {
        targetDto.setName(sourceEntity.getFullName());
        targetDto.setId(sourceEntity.getId()
                .getCustomerId());
    }

    @Override
    public void mergeDtoIntoAggregate(MyCustomerRepresentation sourceDto, Customer targetAggregate) {
        String name = sourceDto.getName();
        if (name != null) {
            String[] split = name.split(" ");
            targetAggregate.setFirstName(split[1].trim());
            targetAggregate.setLastName(split[0].trim());
        }
    }
}