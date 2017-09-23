/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import java.lang.reflect.Type;
import org.seedstack.business.internal.utils.BusinessUtils;

/**
 * An helper base class that can be extended to create an <strong>implementation</strong> of a repository interface
 * which, in turn, must extend {@link Repository}.
 *
 * <p> This class is mainly used as a common base for specialized technology-specific implementations. Client code will
 * often extend these more specialized classes instead of this one. </p>
 *
 * @param <AggregateRootT> Type of the aggregate root.
 * @param <IdT>            Type of the aggregate root identifier.
 * @see Repository
 * @see org.seedstack.business.util.inmemory.BaseInMemoryRepository
 */
public abstract class BaseRepository<AggregateRootT extends AggregateRoot<IdT>, IdT> implements
  Repository<AggregateRootT, IdT> {

  private static final int AGGREGATE_INDEX = 0;
  private static final int KEY_INDEX = 1;
  private final Class<AggregateRootT> aggregateRootClass;
  private final Class<IdT> idClass;

  /**
   * Creates a base domain repository. Actual classes managed by the repository are determined by reflection.
   */
  @SuppressWarnings("unchecked")
  protected BaseRepository() {
    Type[] generics = BusinessUtils.resolveGenerics(BaseRepository.class, getClass());
    this.aggregateRootClass = (Class<AggregateRootT>) generics[AGGREGATE_INDEX];
    this.idClass = (Class<IdT>) generics[KEY_INDEX];
  }

  /**
   * Creates a base domain repository. Actual classes managed by the repository are specified explicitly. This can be
   * used to create a dynamic implementation of a repository.
   *
   * @param aggregateRootClass the aggregate root class.
   * @param idClass            the aggregate root identifier class.
   */
  protected BaseRepository(Class<AggregateRootT> aggregateRootClass, Class<IdT> idClass) {
    this.aggregateRootClass = aggregateRootClass;
    this.idClass = idClass;
  }

  @Override
  public Class<AggregateRootT> getAggregateRootClass() {
    return aggregateRootClass;
  }

  @Override
  public Class<IdT> getIdentifierClass() {
    return idClass;
  }
}
