/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.SpecificationPicker;

class LimitPickerImpl<S extends Slice<A>, A extends AggregateRoot<I>, I> extends
    SpecificationPickerImpl<S, A, I> implements
    LimitPicker<S, A, I> {

  private final PaginatorContext<A, I> context;

  LimitPickerImpl(PaginatorContext<A, I> context, PaginationMode mode) {
    super(context, mode);
    this.context = context;
  }

  @Override
  public SpecificationPicker<S, A, I> limit(long limit) {
    this.context.setLimit(limit);
    return this;
  }
}
