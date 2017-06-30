/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.BaseSelector;
import org.seedstack.business.specification.dsl.SpecificationBuilder;

class SpecificationBuilderImpl implements SpecificationBuilder {
    @Override
    @SuppressWarnings("unchecked")
    public <T, SELECTOR extends BaseSelector<T, SELECTOR>> SELECTOR of(Class<T> anyClass) {
        return (SELECTOR) new BaseSelectorImpl<T, SELECTOR>(new SpecificationBuilderContext<>(anyClass));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> SELECTOR ofAggregate(Class<A> aggregateClass) {
        return (SELECTOR) new AggregateSelectorImpl<A, ID, SELECTOR>(new SpecificationBuilderContext<>(aggregateClass));
    }
}
