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
import org.seedstack.business.specification.dsl.IdentityPicker;

class AggregateSelectorImpl<AggregateRootT extends AggregateRoot<IdT>, IdT, SelectorT extends
    AggregateSelector<AggregateRootT, IdT, SelectorT>> extends
    PropertySelectorImpl<AggregateRootT, SelectorT> implements
    AggregateSelector<AggregateRootT, IdT, SelectorT> {

  AggregateSelectorImpl(SpecificationBuilderContext<AggregateRootT, SelectorT> context) {
    super(context);
  }

  @Override
  public IdentityPicker<AggregateRootT, IdT, SelectorT> identity() {
    return new IdentityPickerImpl<>(context);
  }
}
