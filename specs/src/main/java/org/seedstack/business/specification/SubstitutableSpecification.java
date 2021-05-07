/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification;

/**
 * Specifications that can be substituted by another specification <strong>without changing the
 * satisfaction result</strong> should implement this interface.
 *
 * @param <T> the type of the candidate object the specification applies to.
 */
public interface SubstitutableSpecification<T> extends Specification<T> {

    /**
     * Returns the specification it can be be substituted with.
     *
     * @return the specification this specification can be substituted with.
     */
    Specification<T> getSubstitute();

    @Override
    default boolean isSatisfiedBy(T candidate) {
        return getSubstitute().isSatisfiedBy(candidate);
    }
}
