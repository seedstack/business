/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.dsl.Paginator;
import org.seedstack.business.pagination.dsl.RepositoryOptionsPicker;

class PaginatorImpl implements Paginator {

  @Override
  public <AggregateRootT extends AggregateRoot<IdT>, IdT> RepositoryOptionsPicker<AggregateRootT, IdT> paginate(
    Repository<AggregateRootT, IdT> repository) {
    return new RepositoryOptionsTypePickerImpl<>(repository);
  }
}