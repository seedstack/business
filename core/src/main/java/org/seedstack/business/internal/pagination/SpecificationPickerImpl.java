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

class SpecificationPickerImpl<S extends Slice<A>, A extends AggregateRoot<I>, I> implements
    SpecificationPicker<S, A, I> {

  private final PaginatorContext<A, I> context;
  private final PaginationMode mode;

  SpecificationPickerImpl(PaginatorContext<A, I> context, PaginationMode mode) {
    this.context = context;
    this.mode = mode;
  }

  @Override
  public S matching(Specification<A> spec) {
    return buildView(spec);
  }

  @Override
  public S all() {
    return buildView(Specification.any());
  }

  @SuppressWarnings("unchecked")
  private S buildView(Specification<A> spec) {
    switch (mode) {
      case ATTRIBUTE:
        return (S) context.buildSlice(spec);
      case OFFSET:
        return (S) context.buildSlice(spec);
      case PAGE:
        return (S) context.buildPage(spec);
      default:
        throw new IllegalStateException("Unknown pagination mode " + mode);
    }
  }
}