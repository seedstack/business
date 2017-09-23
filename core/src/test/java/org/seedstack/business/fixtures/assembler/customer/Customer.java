/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.domain.BaseAggregateRoot;


public class Customer extends BaseAggregateRoot<String> {

  private String id;
  private String name;

  public Customer(String id) {
    this.id = id;
    this.name = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
