/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

/**
 * A specification negating another specification.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public class NotSpecification<T> implements Specification<T> {

    private final Specification<T> specification;

    /**
     * Creates a specification negating the specification passed as argument.
     *
     * @param specification the specification to negate.
     */
    public NotSpecification(Specification<T> specification) {
        this.specification = specification;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !specification.isSatisfiedBy(candidate);
    }

    @Override
    public String toString() {
        return String.format("¬(%s)", specification.toString());
    }

    /**
     * Returns the specification that negated.
     *
     * @return the negated specification.
     */
    public Specification<T> getSpecification() {
        return specification;
    }
}
