/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface Specification<A extends AggregateRoot<?>> {
    default Specification<A> and(Specification<A> other) {
        Objects.requireNonNull(other);
        return new AndSpecification<>(this, other);
    }

    default Specification<A> not() {
        return new NotSpecification<>(this);
    }

    default Specification<A> or(Specification<A> other) {
        Objects.requireNonNull(other);
        return new OrSpecification<>(this, other);
    }

    default Predicate<A> asPredicate() {
        return this::isSatisfiedBy;
    }

    boolean isSatisfiedBy(A candidate);
}
