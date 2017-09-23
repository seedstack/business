/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

/**
 * An always false specification, NOT satisfied by any candidate object.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public class FalseSpecification<T> implements Specification<T> {

  @Override
  public boolean isSatisfiedBy(T candidate) {
    return false;
  }

  @Override
  public String toString() {
    return "⊥";
  }
}
