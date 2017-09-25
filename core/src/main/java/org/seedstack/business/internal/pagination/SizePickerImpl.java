/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.dsl.SizePicker;
import org.seedstack.business.pagination.dsl.SpecificationPicker;

class SizePickerImpl<A extends AggregateRoot<I>, I> extends
    SpecificationPickerImpl<Page<A>, A, I> implements SizePicker<A, I> {

  private final PaginatorContext<A, I> context;

  SizePickerImpl(PaginatorContext<A, I> context) {
    super(context, PaginationMode.PAGE);
    this.context = context;
  }

  @Override
  public SpecificationPicker<Page<A>, A, I> ofSize(long size) {
    this.context.setLimit(size);
    return this;
  }
}
