/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl.resolver.annotated;

import org.seedstack.business.assembler.AggregateId;


public class CaseFail3Dto {

  private String firstName;
  private String lastName;

  public CaseFail3Dto(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @AggregateId(index = 0, aggregateIndex = 0)
  public String getFirstName() {
    return firstName;
  }

  @AggregateId(index = 0, aggregateIndex = 0)
  public String getLastName() {
    return lastName;
  }

}
