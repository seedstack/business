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
import org.seedstack.business.pagination.dsl.SpecificationPicker;
import org.seedstack.business.specification.Specification;

class SpecificationPickerImpl<SliceT extends Slice<AggregateRootT>, AggregateRootT extends
    AggregateRoot<IdT>, IdT>
    implements
    SpecificationPicker<SliceT, AggregateRootT, IdT> {

  private final PaginatorContext<AggregateRootT, IdT> context;
  private final PaginationMode mode;

  SpecificationPickerImpl(PaginatorContext<AggregateRootT, IdT> context, PaginationMode mode) {
    this.context = context;
    this.mode = mode;
  }

  @Override
  public SliceT matching(Specification<AggregateRootT> spec) {
    return buildView(spec);
  }

  @Override
  public SliceT all() {
    return buildView(Specification.any());
  }

  @SuppressWarnings("unchecked")
  private SliceT buildView(Specification<AggregateRootT> spec) {
    switch (mode) {
      case ATTRIBUTE:
        return (SliceT) context.buildSlice(spec);
      case OFFSET:
        return (SliceT) context.buildSlice(spec);
      case PAGE:
        return (SliceT) context.buildPage(spec);
      default:
        throw new IllegalStateException("Unknown pagination mode " + mode);
    }
  }
}