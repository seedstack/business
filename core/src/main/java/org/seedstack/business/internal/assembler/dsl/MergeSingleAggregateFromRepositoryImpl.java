/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import java.util.stream.Stream;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;

class MergeSingleAggregateFromRepositoryImpl<AggregateRootT extends AggregateRoot<IdT>, IdT, DtoT> implements
  MergeFromRepository<AggregateRootT>, MergeFromRepositoryOrFactory<AggregateRootT> {

  private final MergeMultipleAggregatesFromRepositoryImpl<AggregateRootT, IdT, DtoT> multipleMerger;

  MergeSingleAggregateFromRepositoryImpl(Context context, DtoT dto, Class<AggregateRootT> aggregateRootClass) {
    multipleMerger = new MergeMultipleAggregatesFromRepositoryImpl<>(context, Stream.of(dto), aggregateRootClass);
  }

  @Override
  public MergeFromRepositoryOrFactory<AggregateRootT> fromRepository() {
    return this;
  }

  @Override
  public AggregateRootT fromFactory() {
    return multipleMerger.fromFactory().asStream().findFirst()
      .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
  }

  @Override
  public AggregateRootT orFail() throws AggregateNotFoundException {
    return multipleMerger.orFail().asStream().findFirst()
      .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
  }

  @Override
  public AggregateRootT orFromFactory() {
    return multipleMerger.orFromFactory().asStream().findFirst()
      .orElseThrow(() -> new IllegalStateException("Nothing to merge"));
  }
}
