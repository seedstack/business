/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface Specification<T> {
    static <T> Specification<T> any() {
        return new TrueSpecification<>();
    }

    static <T> Specification<T> none() {
        return new FalseSpecification<>();
    }

    default Specification<T> and(Specification<? super T> other) {
        Objects.requireNonNull(other);
        return new AndSpecification<>(this, other);
    }

    default Specification<T> negate() {
        return new NotSpecification<>(this);
    }

    default Specification<T> or(Specification<? super T> other) {
        Objects.requireNonNull(other);
        return new OrSpecification<>(this, other);
    }

    default Predicate<T> asPredicate() {
        return this::isSatisfiedBy;
    }

    boolean isSatisfiedBy(T candidate);
}
