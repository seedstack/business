/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

public class AndSpecification<T> implements Specification<T> {
    private final Specification<T> lhs;
    private final Specification<T> rhs;

    public AndSpecification(Specification<T> lhs, Specification<T> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Specification<T> getLhs() {
        return lhs;
    }

    public Specification<T> getRhs() {
        return rhs;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return lhs.isSatisfiedBy(candidate) && rhs.isSatisfiedBy(candidate);
    }

    @Override
    public String toString() {
        return String.format("(%s) ∧ (%s)", lhs.toString(), rhs.toString());
    }
}
