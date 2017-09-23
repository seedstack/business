/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.FactoryArgument;

/**
 * An element of the {@link FluentAssembler DSL} allowing to specify whether the aggregates should
 * be retrieved from a repository or created from a factory.
 **/
public interface MergeFromRepository<T> {

  /**
   * Loads the aggregates from their repository. <p> It uses the {@link AggregateId} annotation on
   * the DTO to find the aggregate IDs. </p>
   *
   * @return the next element of the DSL.
   */
  MergeFromRepositoryOrFactory<T> fromRepository();

  /**
   * Create the aggregates from their factory. <p> It uses the {@link FactoryArgument} annotation on
   * the DTO to find the factory method parameters. </p>
   *
   * @return the next element of the DSL.
   */
  T fromFactory();

}
