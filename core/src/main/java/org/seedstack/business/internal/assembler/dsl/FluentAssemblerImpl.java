/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.assembler.dsl.AssembleMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.assembler.dsl.MergeMultipleWithQualifier;
import org.seedstack.business.assembler.dsl.MergeSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.spi.DtoInfoResolver;

/**
 * Implementation of {@link FluentAssembler}. <p> It uses a Guice provider to get the DSL entry point. Each time you
 * call the {@code assemble()} method a new DSL instance is provided. </p>
 */
public class FluentAssemblerImpl implements FluentAssembler {

  private Context context;

  @Inject
  public FluentAssemblerImpl(DomainRegistry domainRegistry, AssemblerRegistry registry,
    Set<DtoInfoResolver> dtoInfoResolvers) {
    context = new Context(domainRegistry, registry, dtoInfoResolvers);
  }

  @Override
  public <AggregateRootT extends AggregateRoot<IdT>, IdT> AssembleSingleWithQualifier assemble(
    AggregateRootT aggregateRoot) {
    return new AssembleSingleImpl<>(context, aggregateRoot, null);
  }

  @Override
  public <AggregateRootT extends AggregateRoot<IdT>, IdT> AssembleMultipleWithQualifier assemble(
    Iterable<AggregateRootT> iterable) {
    return new AssembleMultipleImpl<>(context, StreamSupport.stream(iterable.spliterator(), false), null);
  }

  @Override
  public <AggregateRootT extends AggregateRoot<IdT>, IdT> AssembleMultipleWithQualifier assemble(
    Stream<AggregateRootT> stream) {
    return new AssembleMultipleImpl<>(context, stream, null);
  }

  @Override
  public <AggregateRootT extends AggregateRoot<IdT>, IdT> AssemblePageWithQualifier assemble(
    Page<AggregateRootT> page) {
    return new AssemblePageImpl<>(context, page, null);
  }

  @Override
  public <TupleT extends Tuple> AssembleSingleWithQualifier assembleTuple(TupleT tuple) {
    return new AssembleSingleImpl<>(context, null, tuple);
  }

  @Override
  public <TupleT extends Tuple> AssembleMultipleWithQualifier assembleTuples(Stream<TupleT> stream) {
    return new AssembleMultipleImpl<>(context, null, stream);
  }

  @Override
  public <TupleT extends Tuple> AssembleMultipleWithQualifier assembleTuples(Iterable<TupleT> iterable) {
    return new AssembleMultipleImpl<>(context, null, StreamSupport.stream(iterable.spliterator(), false));
  }

  @Override
  public <TupleT extends Tuple> AssemblePageWithQualifier assembleTuples(Page<TupleT> page) {
    return new AssemblePageImpl<>(context, null, page);
  }

  @Override
  public <DtoT> MergeSingleWithQualifier merge(DtoT dto) {
    return new MergeSingleImpl<>(context, dto);
  }

  @Override
  public <DtoT> MergeMultipleWithQualifier merge(Stream<DtoT> stream) {
    return new MergeMultipleImpl<>(context, stream);
  }

  @Override
  public <DtoT> MergeMultipleWithQualifier merge(Iterable<DtoT> iterable) {
    return new MergeMultipleImpl<>(context, StreamSupport.stream(iterable.spliterator(), false));
  }
}
