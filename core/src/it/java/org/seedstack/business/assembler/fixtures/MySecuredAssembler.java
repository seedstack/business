/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.fixtures;


import org.seedstack.business.api.interfaces.annotations.Secured;
import org.seedstack.business.api.interfaces.assembler.BaseAssembler;

/**
 * @author epo.jemba@ext.mpsa.com
 */
public class MySecuredAssembler extends BaseAssembler<MyAggregateRoot, MyDto> {

    @Override
    protected void doAssembleDtoFromAggregate(@Secured MyDto targetDto, MyAggregateRoot sourceEntity) {
        targetDto.setNom(sourceEntity.getLastName() + ", " + sourceEntity.getFirstName());
        targetDto.setAge(sourceEntity.getAge());
        targetDto.setAddresse(sourceEntity.getAddress());
    }

    @Override
    public void doMergeAggregateWithDto(MyAggregateRoot targetEntity, @Secured MyDto sourceDto) {
        String[] split = sourceDto.getNom().split(",");
        targetEntity.setFirstName(split[1].trim());
        targetEntity.setLastName(split[0].trim());
        targetEntity.setAge(sourceDto.getAge());
        targetEntity.setAddress(sourceDto.getAddresse());
    }
}
