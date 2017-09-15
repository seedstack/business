/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

import org.seedstack.business.domain.AggregateRoot;

import static java.util.Objects.requireNonNull;

/**
 * A specification that can only be applied to {@link AggregateRoot}s and that is satisfied only if the candidate
 * aggregate has an identifier equal to the expected one.
 *
 * @param <A>  the aggregate type this specification applies to.
 * @param <ID> the aggregate identifier type.
 */
public class IdentitySpecification<A extends AggregateRoot<ID>, ID> implements Specification<A> {
    private final ID expectedIdentifier;

    /**
     * Creates a specification satisfied only if the candidate aggregate has an identifier equal to the identifier passed
     * as argument.
     *
     * @param expectedIdentifier the expected identifier.
     */
    public IdentitySpecification(ID expectedIdentifier) {
        requireNonNull(expectedIdentifier, "Expected identifier cannot be null");
        this.expectedIdentifier = expectedIdentifier;
    }

    @Override
    public boolean isSatisfiedBy(A candidate) {
        return expectedIdentifier.equals(candidate.getId());
    }

    @Override
    public String toString() {
        return String.valueOf(expectedIdentifier);
    }

    /**
     * @return the expected identifier.
     */
    public ID getExpectedIdentifier() {
        return expectedIdentifier;
    }
}
