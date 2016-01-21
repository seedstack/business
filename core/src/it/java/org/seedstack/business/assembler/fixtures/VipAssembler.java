/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.fixtures;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.fixtures.domain.customer.Customer;

public class VipAssembler extends BaseAssembler<Customer, VipRepresentation> {

    @Override
    protected void doAssembleDtoFromAggregate(VipRepresentation targetDto, Customer sourceEntity) {
        targetDto.setName(sourceEntity.getFullName());
        targetDto.setId(sourceEntity.getEntityId().getCustomerId());
        targetDto.setVipName("vip");
    }

    @Override
    public void doMergeAggregateWithDto(Customer targetEntity, VipRepresentation sourceDto) {
        String[] split = sourceDto.getName().split(" ");
        targetEntity.setFirstName(split[1].trim());
        targetEntity.setLastName(split[0].trim());
    }
}