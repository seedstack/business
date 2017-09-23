/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
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
  public <T, SelectorT extends PropertySelector<T, SelectorT>> SelectorT of(Class<T> anyClass) {
    return (SelectorT) new PropertySelectorImpl<T, SelectorT>(new SpecificationBuilderContext<>(anyClass));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <AggregateRootT extends AggregateRoot<IdT>, IdT, SelectorT extends AggregateSelector<AggregateRootT, IdT,
    SelectorT>> SelectorT ofAggregate(
    Class<AggregateRootT> aggregateClass) {
    return (SelectorT) new AggregateSelectorImpl<AggregateRootT, IdT, SelectorT>(
      new SpecificationBuilderContext<>(aggregateClass));
  }
}
