/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.domain.specification;

import org.seedstack.business.domain.BaseValueObject;

public class Address extends BaseValueObject {

  private final int number;
  private final String street;
  private final String city;

  public Address(int number, String street, String city) {
    this.number = number;
    this.street = street;
    this.city = city;
  }
}
