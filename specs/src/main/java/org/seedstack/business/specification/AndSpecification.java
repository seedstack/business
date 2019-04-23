/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

/**
 * A specification composing multiple specifications with a logical AND.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public class AndSpecification<T> implements Specification<T> {

    private final Specification<? super T>[] specifications;

    /**
     * Creates a specification composing the specifications passed as argument with a logical AND.
     *
     * @param specifications the specifications to compose in a logical AND.
     */
    @SafeVarargs
    public AndSpecification(Specification<? super T>... specifications) {
        this.specifications = specifications.clone();
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        for (Specification<? super T> specification : specifications) {
            if (!specification.isSatisfiedBy(candidate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < specifications.length; i++) {
            Specification<? super T> term = specifications[i];
            boolean isNegation = term instanceof NotSpecification;
            if (!isNegation) {
                sb.append("(");
            }
            sb.append(term.toString());
            if (!isNegation) {
                sb.append(")");
            }
            if (i < specifications.length - 1) {
                sb.append(" ∧ ");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the composed specifications.
     *
     * @return the array of specifications composed with the logical AND.
     */
    public Specification<? super T>[] getSpecifications() {
        return specifications.clone();
    }
}
