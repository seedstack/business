/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.pagination.dsl;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Slice;

/**
 * An element of the {@link Paginator} DSL allowing to choose the pagination type. Three pagination
 * types are supported: <ul> <li>Offset-based which allows to skip a specified amount of
 * objects,</li> <li>Page-based which is similar to offset-based but allows to specify the amount of
 * skipped objects as a number of pages,</li> <li>Key-based which allows to select objects that come
 * after a specific value of a specific attribute.</li> </ul>
 *
 * @param <AggregateRootT> the aggregate root type that is paginated.
 * @param <IdT>            the aggregate root identifier type.
 */
public interface PaginationTypePicker<AggregateRootT extends AggregateRoot<IdT>, IdT> {

  /**
   * Choose a page-based pagination type. Objects that come before the beginning of specified page
   * will be skipped.
   *
   * @param pageIndex the index of the page containing objects that will be returned.
   * @return the next operation of the paginator DSL, allowing to specify page size.
   */
  SizePicker<AggregateRootT, IdT> byPage(long pageIndex);

  /**
   * Choose an offset-based pagination type. Objects that come before the specified index will be
   * skipped.
   *
   * @param startingOffset the index of first object that will be returned.
   * @return the next operation of the paginator DSL, allowing to specify a limit to the number of
   *   objects returned.
   */
  LimitPicker<Slice<AggregateRootT>, AggregateRootT, IdT> byOffset(long startingOffset);

  /**
   * Choose a key-based pagination type.
   *
   * @param attributeName the attribute on which the lessThan/greaterThan comparison will be made.
   * @return the next operation of the paginator DSL, allowing to specify the value used as
   *   boundary.
   */
  KeyValuePicker<AggregateRootT, IdT> byAttribute(String attributeName);
}
