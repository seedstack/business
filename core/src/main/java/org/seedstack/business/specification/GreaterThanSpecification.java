/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

/**
 * A specification satisfied only when the candidate value is greater than the expected value according to {@link
 * Comparable#compareTo(Object)}.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public class GreaterThanSpecification<T extends Comparable<? super T>> extends ComparableSpecification<T> {

  /**
   * Creates a greater than specification.
   *
   * @param expectedValue the value used to do the comparison against.
   */
  public GreaterThanSpecification(T expectedValue) {
    super(expectedValue, 1);
  }

  @Override
  public String toString() {
    return "> " + String.valueOf(getExpectedValue());
  }
}
