/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.util.inmemory;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.spi.GenericImplementation;

/**
 * Default repository implementation for in-memory persistence. <p> When no custom repository exists for the aggregate,
 * this in-memory implementation can be injected for {@link org.seedstack.business.domain.Repository} with the qualifier
 * {@literal @}InMemory. </p>
 */
@GenericImplementation
@InMemory
public class DefaultInMemoryRepository<AggregateRootT extends AggregateRoot<IdT>, IdT> extends
  BaseInMemoryRepository<AggregateRootT, IdT> {

  /**
   * Creates a default in-memory repository.
   *
   * @param genericClasses the resolved generics for the aggregate root class and the key class
   */
  @SuppressWarnings("unchecked")
  @Inject
  public DefaultInMemoryRepository(@Assisted Object[] genericClasses) {
    super((Class<AggregateRootT>) genericClasses[0], (Class<IdT>) genericClasses[1]);
  }
}
