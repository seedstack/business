/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.repositories;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.spi.GenericImplementation;


@GenericImplementation
@MyQualifier
public class DefaultRepoSample3<A extends AggregateRoot<K>, K> extends DummyRepository<A, K> {

  @Inject
  @SuppressWarnings("unchecked")
  public DefaultRepoSample3(@Assisted Object[] genericClasses) {
    super((Class) genericClasses[0], (Class) genericClasses[1]);
  }
}
