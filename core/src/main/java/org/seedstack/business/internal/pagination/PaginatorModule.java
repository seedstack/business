/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.pagination;

import com.google.inject.AbstractModule;
import org.seedstack.business.pagination.dsl.Paginator;

class PaginatorModule extends AbstractModule {

  PaginatorModule() {
  }

  @Override
  protected void configure() {
    bind(Paginator.class).to(PaginatorImpl.class);
  }
}
