/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.AssembleMultiple;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;
import org.seedstack.business.util.Tuples;


class AssembleMultipleImpl<AggregateRootT extends AggregateRoot<IdT>, IdT, TupleT extends Tuple> implements
  AssembleMultipleWithQualifier {

  private final Context context;
  private final Stream<AggregateRootT> aggregates;
  private final Stream<TupleT> aggregateTuples;

  AssembleMultipleImpl(Context context, Stream<AggregateRootT> aggregates, Stream<TupleT> aggregateTuples) {
    this.context = checkNotNull(context, "Context must not be null");
    checkArgument(aggregates != null || aggregateTuples != null,
      "Cannot assemble null");
    checkArgument(aggregates == null || aggregateTuples == null,
      "Cannot specify both aggregates and tuples to assemble");
    this.aggregates = aggregates;
    this.aggregateTuples = aggregateTuples;
  }

  @Override
  public <DtoT> Stream<DtoT> toStreamOf(Class<DtoT> dtoClass) {
    if (aggregates != null) {
      return aggregates.map(
        aggregate -> context.assemblerOf(getAggregateClass(aggregate), dtoClass).createDtoFromAggregate(aggregate));
    } else if (aggregateTuples != null) {
      return aggregateTuples
        .map(tuple -> context.tupleAssemblerOf(Tuples.itemClasses(tuple), dtoClass).createDtoFromAggregate(tuple));
    }
    throw new IllegalStateException("Nothing to assemble");
  }

  @Override
  public <DtoT, CollectionT extends Collection<DtoT>> CollectionT toCollectionOf(Class<DtoT> dtoClass,
    Supplier<CollectionT> collectionSupplier) {
    CollectionT collection = collectionSupplier.get();
    toStreamOf(dtoClass).forEach(collection::add);
    return collection;
  }

  @Override
  public <DtoT> List<DtoT> toListOf(Class<DtoT> dtoClass) {
    return toCollectionOf(dtoClass, ArrayList::new);
  }

  @Override
  public <DtoT> Set<DtoT> toSetOf(Class<DtoT> dtoClass) {
    return toCollectionOf(dtoClass, HashSet::new);
  }

  @Override
  public <DtoT> Slice<DtoT> toSliceOf(Class<DtoT> dtoClass) {
    return new SimpleSlice<>(toListOf(dtoClass));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <DtoT> DtoT[] toArrayOf(Class<DtoT> dtoClass) {
    return toStreamOf(dtoClass).toArray(size -> (DtoT[]) new Object[size]);
  }

  @Override
  public AssembleMultiple with(Annotation qualifier) {
    context.setAssemblerQualifier(qualifier);
    return this;
  }

  @Override
  public AssembleMultiple with(Class<? extends Annotation> qualifier) {
    context.setAssemblerQualifierClass(qualifier);
    return this;
  }

  Context getContext() {
    return context;
  }

  @SuppressWarnings("unchecked")
  private Class<AggregateRootT> getAggregateClass(AggregateRootT aggregate) {
    return (Class<AggregateRootT>) aggregate.getClass();
  }
}
