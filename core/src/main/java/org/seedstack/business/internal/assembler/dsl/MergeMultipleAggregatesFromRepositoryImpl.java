/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import java.util.stream.Stream;
import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.utils.BusinessUtils;


class MergeMultipleAggregatesFromRepositoryImpl<AggregateRootT extends AggregateRoot<IdT>, IdT,
    DtoT> implements
    MergeFromRepository<MergeAs<AggregateRootT>>,
    MergeFromRepositoryOrFactory<MergeAs<AggregateRootT>> {

  private final Context context;
  private final Class<AggregateRootT> aggregateClass;
  private final Class<IdT> aggregateClassId;
  private final Stream<DtoT> dtoStream;

  MergeMultipleAggregatesFromRepositoryImpl(Context context, Stream<DtoT> dtoStream,
      Class<AggregateRootT> aggregateClass) {
    this.context = context;
    this.dtoStream = dtoStream;
    this.aggregateClass = aggregateClass;
    this.aggregateClassId = BusinessUtils.getAggregateIdClass(aggregateClass);
  }

  @Override
  public MergeFromRepositoryOrFactory<MergeAs<AggregateRootT>> fromRepository() {
    return this;
  }

  @Override
  public MergeAs<AggregateRootT> fromFactory() {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      AggregateRootT a = context.create(dto, aggregateClass);
      context.mergeDtoIntoAggregate(dto, a);
      return a;
    }));
  }

  @Override
  public MergeAs<AggregateRootT> orFail() throws AggregateNotFoundException {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      IdT id = context.resolveId(dto, aggregateClassId);
      AggregateRootT a = context.load(id, aggregateClass);
      if (a == null) {
        throw new AggregateNotFoundException(
            "Unable to load aggregate " + aggregateClass.getName() + "[" + id + "]");
      }
      context.mergeDtoIntoAggregate(dto, a);
      return a;
    }));
  }

  @Override
  public MergeAs<AggregateRootT> orFromFactory() {
    return new MergeAsImpl<>(dtoStream.map(dto -> {
      AggregateRootT a = context.load(context.resolveId(dto, aggregateClassId), aggregateClass);
      if (a == null) {
        a = context.create(dto, aggregateClass);
      }
      context.mergeDtoIntoAggregate(dto, a);
      return a;
    }));
  }
}
