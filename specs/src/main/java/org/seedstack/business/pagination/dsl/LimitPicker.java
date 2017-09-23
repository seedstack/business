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
 * An element of the {@link Paginator} DSL allowing to specify a limit on the number of objects
 * returned, in the case of an offset-based pagination.
 *
 * @param <SliceT>         the type of the slice.
 * @param <AggregateRootT> the aggregate root type that is paginated.
 * @param <IdT>            the aggregate root identifier type.
 */
public interface LimitPicker<SliceT extends Slice<AggregateRootT>,
  AggregateRootT extends AggregateRoot<IdT>, IdT> extends
  SpecificationPicker<SliceT, AggregateRootT, IdT> {

  /**
   * Specify a limit on the number of objects returned.
   *
   * @param limit the limit.
   * @return the next operation of the paginator DSL, allowing to pick a specification for selecting
   *   objects returned from the repository.
   */
  SpecificationPicker<SliceT, AggregateRootT, IdT> limit(long limit);
}
