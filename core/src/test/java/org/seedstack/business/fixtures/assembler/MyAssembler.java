/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler;


import org.seedstack.business.assembler.BaseAssembler;

public class MyAssembler extends BaseAssembler<MyAggregateRoot, MyUnrestrictedDto> {

  @Override
  public void mergeAggregateIntoDto(MyAggregateRoot sourceEntity, MyUnrestrictedDto targetDto) {
    targetDto.setName(sourceEntity.getLastName() + ", " + sourceEntity.getFirstName());
    targetDto.setAge(sourceEntity.getAge());
    targetDto.setAddress(sourceEntity.getAddress());
  }

  @Override
  public void mergeDtoIntoAggregate(MyUnrestrictedDto sourceDto, MyAggregateRoot targetEntity) {
    String[] split = sourceDto.getName().split(",");
    targetEntity.setFirstName(split[1].trim());
    targetEntity.setLastName(split[0].trim());
    targetEntity.setAge(sourceDto.getAge());
    targetEntity.setAddress(sourceDto.getAddress());
  }
}