/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.PropertySelector;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

class SpecificationBuilderImpl implements SpecificationBuilder {

    @Override
    @SuppressWarnings("unchecked")
    public <T, S extends PropertySelector<T, S>> S of(Class<T> anyClass) {
        return (S) new PropertySelectorImpl<T, S>(new SpecificationBuilderContext<>(anyClass));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends AggregateRoot<I>, I, S extends AggregateSelector<A, I, S>> S ofAggregate(
            Class<A> aggregateClass) {
        return (S) new AggregateSelectorImpl<A, I, S>(new SpecificationBuilderContext<>(aggregateClass));
    }
}
