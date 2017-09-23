/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.util.inmemory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;
import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.LimitOption;
import org.seedstack.business.domain.OffsetOption;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.domain.SortOption;
import org.seedstack.business.specification.Specification;

/**
 * An helper base class that can be extended to create an in-memory implementation of a {@link Repository}. It is backed
 * by a {@link ConcurrentHashMap} per aggregate root class.
 */
public abstract class BaseInMemoryRepository<AggregateRootT extends AggregateRoot<IdT>, IdT> extends
  BaseRepository<AggregateRootT, IdT> {

  private static final ConcurrentMap<Class<?>, Map<?, ?>> buckets = new ConcurrentHashMap<>();
  @SuppressWarnings("unchecked")
  private final Map<IdT, AggregateRootT> bucket = (Map<IdT, AggregateRootT>) buckets
    .computeIfAbsent(getAggregateRootClass(), key -> new ConcurrentHashMap<IdT, AggregateRootT>());

  /**
   * Creates a base in-memory repository. Actual classes managed by the repository are determined by reflection.
   */
  protected BaseInMemoryRepository() {
  }

  /**
   * Creates a base in-memory repository. Actual classes managed by the repository are specified explicitly.
   *
   * @param aggregateRootClass the actual aggregate root class.
   * @param idClass            the actual aggregate identifier class.
   */
  protected BaseInMemoryRepository(Class<AggregateRootT> aggregateRootClass, Class<IdT> idClass) {
    super(aggregateRootClass, idClass);
  }

  @Override
  public void add(AggregateRootT a) throws AggregateExistsException {
    bucket.put(a.getId(), a);
  }

  @Override
  public Stream<AggregateRootT> get(Specification<AggregateRootT> specification, Option... options) {
    Stream<AggregateRootT> stream = bucket.values().stream().filter(specification.asPredicate());
    for (Option option : options) {
      if (option instanceof OffsetOption) {
        stream = stream.skip(((OffsetOption) option).getOffset());
      } else if (option instanceof LimitOption) {
        stream = stream.limit(((LimitOption) option).getLimit());
      } else if (option instanceof SortOption) {
        // TODO
      }
    }
    return stream;
  }

  @Override
  public long remove(Specification<AggregateRootT> specification) {
    Iterator<Map.Entry<IdT, AggregateRootT>> iterator = bucket.entrySet().iterator();
    int count = 0;
    while (iterator.hasNext()) {
      Map.Entry<IdT, AggregateRootT> next = iterator.next();
      if (specification.isSatisfiedBy(next.getValue())) {
        iterator.remove();
        count++;
      }
    }
    return count;
  }
}
