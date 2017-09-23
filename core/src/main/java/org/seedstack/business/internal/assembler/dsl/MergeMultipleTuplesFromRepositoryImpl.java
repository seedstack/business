/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import static org.seedstack.business.internal.utils.BusinessUtils.getAggregateIdClasses;

import java.util.stream.Stream;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.util.Tuples;


class MergeMultipleTuplesFromRepositoryImpl<T extends Tuple, D> implements MergeFromRepository<MergeAs<T>>,
  MergeFromRepositoryOrFactory<MergeAs<T>> {

  private final Context context;
  private final Class<? extends AggregateRoot<?>>[] aggregateClasses;
  private final Class<?>[] aggregateIdClasses;
  private final Stream<D> dtoStream;

  @SafeVarargs
  MergeMultipleTuplesFromRepositoryImpl(Context context, Stream<D> dtoStream,
    Class<? extends AggregateRoot<?>>... aggregateClasses) {
    this.context = context;
    this.dtoStream = dtoStream;
    this.aggregateClasses = aggregateClasses;
    this.aggregateIdClasses = getAggregateIdClasses(aggregateClasses);
  }

  @Override
  public MergeFromRepositoryOrFactory<MergeAs<T>> fromRepository() {
    return this;
  }

  @Override
  public MergeAs<T> fromFactory() {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      AggregateRoot<?>[] aggregateRoots = new AggregateRoot<?>[aggregateClasses.length];
      for (int i = 0; i < aggregateClasses.length; i++) {
        aggregateRoots[i] = context.create(dto, aggregateClasses[i], i);
      }
      T tuple = Tuples.create((Object[]) aggregateRoots);
      context.mergeDtoIntoTuple(dto, tuple, aggregateClasses);
      return tuple;
    }));
  }

  @Override
  public MergeAs<T> orFail() throws AggregateNotFoundException {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      AggregateRoot<?>[] aggregateRoots = new AggregateRoot<?>[aggregateClasses.length];
      for (int i = 0; i < aggregateClasses.length; i++) {
        Object id = context.resolveId(dto, aggregateIdClasses[i], i);
        aggregateRoots[i] = load(id, aggregateClasses[i]);
        if (aggregateRoots[i] == null) {
          throw new AggregateNotFoundException(
            "Unable to load aggregate " + aggregateClasses[i].getName() + "[" + id + "]");
        }
      }
      T tuple = Tuples.create((Object[]) aggregateRoots);
      context.mergeDtoIntoTuple(dto, tuple, aggregateClasses);
      return tuple;
    }));
  }

  @Override
  public MergeAs<T> orFromFactory() {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      AggregateRoot<?>[] aggregateRoots = new AggregateRoot<?>[aggregateClasses.length];
      for (int i = 0; i < aggregateClasses.length; i++) {
        aggregateRoots[i] = load(context.resolveId(dto, aggregateIdClasses[i], i), aggregateClasses[i]);
        if (aggregateRoots[i] == null) {
          aggregateRoots[i] = context.create(dto, aggregateClasses[i], i);
        }
      }
      T tuple = Tuples.create((Object[]) aggregateRoots);
      context.mergeDtoIntoTuple(dto, tuple, aggregateClasses);
      return tuple;
    }));
  }

  @SuppressWarnings("unchecked")
  private <AggregateRootT extends AggregateRoot<IdT>, IdT> AggregateRoot<?> load(Object id,
    Class<? extends AggregateRoot<?>> aggregateClass) {
    return context.load((IdT) id, (Class<AggregateRootT>) aggregateClass);
  }
}