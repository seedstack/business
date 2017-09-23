/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.pagination.dsl.KeyValuePicker;
import org.seedstack.business.pagination.dsl.LimitPicker;
import org.seedstack.business.pagination.dsl.PaginationTypePicker;
import org.seedstack.business.pagination.dsl.SizePicker;

class PaginationTypePickerImpl<AggregateRootT extends AggregateRoot<IdT>, IdT> implements
  PaginationTypePicker<AggregateRootT, IdT> {

  private final Repository<AggregateRootT, IdT> repository;
  protected Repository.Option[] options = new Repository.Option[0];

  PaginationTypePickerImpl(Repository<AggregateRootT, IdT> repository) {
    checkNotNull(repository, "Repository cannot be null");
    this.repository = repository;
  }

  @Override
  public SizePicker<AggregateRootT, IdT> byPage(long pageIndex) {
    PaginatorContext<AggregateRootT, IdT> builder = new PaginatorContext<>(repository, options);
    builder.setPageIndex(pageIndex);
    return new SizePickerImpl<>(builder);
  }

  @Override
  public LimitPicker<Slice<AggregateRootT>, AggregateRootT, IdT> byOffset(long startingOffset) {
    PaginatorContext<AggregateRootT, IdT> builder = new PaginatorContext<>(repository, options);
    builder.setOffset(startingOffset);
    return new LimitPickerImpl<>(builder, PaginationMode.OFFSET);
  }

  @Override
  public KeyValuePicker<AggregateRootT, IdT> byAttribute(String attributeName) {
    PaginatorContext<AggregateRootT, IdT> builder = new PaginatorContext<>(repository, options);
    builder.setAttribute(attributeName);
    return new AfterKeyPickerImpl<>(builder, PaginationMode.ATTRIBUTE);
  }
}