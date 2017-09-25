/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.specification;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.specification.IdentitySpecification;
import org.seedstack.business.specification.dsl.AggregateSelector;
import org.seedstack.business.specification.dsl.IdentityPicker;
import org.seedstack.business.specification.dsl.OperatorPicker;

class IdentityPickerImpl<AggregateRootT extends AggregateRoot<IdT>, IdT, SelectorT extends
    AggregateSelector<AggregateRootT, IdT, SelectorT>> implements
    IdentityPicker<AggregateRootT, IdT, SelectorT> {

  private final SpecificationBuilderContext<AggregateRootT, SelectorT> context;

  IdentityPickerImpl(SpecificationBuilderContext<AggregateRootT, SelectorT> context) {
    this.context = context;
  }

  @Override
  public OperatorPicker<AggregateRootT, SelectorT> is(IdT id) {
    context.addSpecification(new IdentitySpecification<>(id));
    return new OperatorPickerImpl<>(context);
  }

  @Override
  public OperatorPicker<AggregateRootT, SelectorT> isNot(IdT id) {
    context.addSpecification(new IdentitySpecification<AggregateRootT, IdT>(id).negate());
    return new OperatorPickerImpl<>(context);
  }
}
