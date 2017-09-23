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

class SizePickerImpl<AggregateRootT extends AggregateRoot<IdT>, IdT> extends
  SpecificationPickerImpl<Page<AggregateRootT>, AggregateRootT, IdT> implements SizePicker<AggregateRootT, IdT> {

  private final PaginatorContext<AggregateRootT, IdT> context;

  SizePickerImpl(PaginatorContext<AggregateRootT, IdT> context) {
    super(context, PaginationMode.PAGE);
    this.context = context;
  }

  @Override
  public SpecificationPicker<Page<AggregateRootT>, AggregateRootT, IdT> ofSize(long size) {
    this.context.setLimit(size);
    return this;
  }
}
