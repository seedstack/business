/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.fixtures.domain.multi;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Dummy aggregate for test
 */
public class Multi extends BaseAggregateRoot<String> {

  private String id;

  public Multi(String id) {
    this.id = id;
  }
}
