/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain.specification;

import org.seedstack.business.domain.AggregateRoot;

public class NotSpecification<A extends AggregateRoot<?>> implements Specification<A> {
    private final Specification<A> specification;

    public NotSpecification(Specification<A> specification) {
        this.specification = specification;
    }

    @Override
    public boolean isSatisfiedBy(A candidate) {
        return !specification.isSatisfiedBy(candidate);
    }

    public Specification<A> getSpecification() {
        return specification;
    }

    @Override
    public String toString() {
        return String.format("¬(%s)", specification.toString());
    }
}
