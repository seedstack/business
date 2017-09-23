/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.specification;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A {@link Specification} is a pattern that is able to tell if a candidate object matches some
 * criteria. The specification has a method {@link #isSatisfiedBy(Object)} that returns true if all
 * criteria are met by the candidate.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
@FunctionalInterface
public interface Specification<T> {

  /**
   * Special value for an always true specification.
   *
   * @param <T> the type of the candidate object the specification applies to.
   * @return a {@link TrueSpecification}.
   */
  static <T> Specification<T> any() {
    return new TrueSpecification<>();
  }

  /**
   * Special value for an always false specification.
   *
   * @param <T> the type of the candidate object the specification applies to.
   * @return a {@link FalseSpecification}.
   */
  static <T> Specification<T> none() {
    return new FalseSpecification<>();
  }

  /**
   * Compose this specification with another specification through a logical AND.
   *
   * @param other the other specification.
   * @return an {@link AndSpecification} composing this specification with the specification passed
   *   as argument.
   */
  default Specification<T> and(Specification<? super T> other) {
    Objects.requireNonNull(other);
    return new AndSpecification<>(this, other);
  }

  /**
   * Negate this specification.
   *
   * @return a {@link NotSpecification} negating this specification.
   */
  default Specification<T> negate() {
    return new NotSpecification<>(this);
  }

  /**
   * Compose this specification with another specification through a logical OR.
   *
   * @param other the other specification.
   * @return an {@link OrSpecification} composing this specification with the specification passed
   *   as argument.
   */
  default Specification<T> or(Specification<? super T> other) {
    Objects.requireNonNull(other);
    return new OrSpecification<>(this, other);
  }

  /**
   * Express this specification as a Java {@link Predicate}.
   *
   * @return the {@link Predicate} corresponding to this specification.
   */
  default Predicate<T> asPredicate() {
    return this::isSatisfiedBy;
  }

  /**
   * Evaluates if the candidate object passed as argument satisfies the specification.
   *
   * @param candidate the candidate object to check.
   * @return true if the candidate object satisfies the specification, false otherwise.
   */
  boolean isSatisfiedBy(T candidate);
}
