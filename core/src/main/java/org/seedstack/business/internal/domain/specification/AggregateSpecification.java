/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.specification.Specification;

public class AggregateSpecification<A extends AggregateRoot<?>> implements Specification<A> {
    private final Class<A> aggregateClass;
    private final Specification<A> specification;

    public AggregateSpecification(Class<A> aggregateClass, Specification<A> specification) {
        this.aggregateClass = aggregateClass;
        this.specification = specification;
    }

    @Override
    public boolean isSatisfiedBy(A candidate) {
        return specification.isSatisfiedBy(candidate);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", aggregateClass.getSimpleName(), specification.toString());
    }

    public Class<A> getAggregateClass() {
        return aggregateClass;
    }

    public Specification<A> getSpecification() {
        return specification;
    }
}
