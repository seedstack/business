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
 * @param <A> the aggregate type this specification applies to.
 * @param <I> the aggregate identifier type.
 */
public class IdentitySpecification<A extends AggregateRoot<I>, I> implements Specification<A> {

    private final I expectedIdentifier;

    /**
     * Creates a specification satisfied only if the candidate aggregate has an identifier equal to
     * the identifier passed as argument.
     *
     * @param expectedIdentifier the expected identifier.
     */
    public IdentitySpecification(I expectedIdentifier) {
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
     * Returns the identifier that is expected.
     *
     * @return the expected identifier.
     */
    public I getExpectedIdentifier() {
        return expectedIdentifier;
    }
}
