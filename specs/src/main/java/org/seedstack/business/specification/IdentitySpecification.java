/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import static java.util.Objects.requireNonNull;

import org.seedstack.business.domain.AggregateRoot;

/**
 * A specification that can only be applied to {@link AggregateRoot}s and that is satisfied only if
 * the candidate aggregate has an identifier equal to the expected one.
 *
 * @param <AggregateRootT> the aggregate type this specification applies to.
 * @param <IdT>            the aggregate identifier type.
 */
public class IdentitySpecification<AggregateRootT extends AggregateRoot<IdT>, IdT> implements
  Specification<AggregateRootT> {

  private final IdT expectedIdentifier;

  /**
   * Creates a specification satisfied only if the candidate aggregate has an identifier equal to
   * the identifier passed as argument.
   *
   * @param expectedIdentifier the expected identifier.
   */
  public IdentitySpecification(IdT expectedIdentifier) {
    requireNonNull(expectedIdentifier, "Expected identifier cannot be null");
    this.expectedIdentifier = expectedIdentifier;
  }

  @Override
  public boolean isSatisfiedBy(AggregateRootT candidate) {
    return expectedIdentifier.equals(candidate.getId());
  }

  @Override
  public String toString() {
    return String.valueOf(expectedIdentifier);
  }

  /**
   * Returns the identifier that is expected.
   *
   * @return the expected identifier.
   */
  public IdT getExpectedIdentifier() {
    return expectedIdentifier;
  }
}
