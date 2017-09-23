/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler.dsl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.DtoInfoResolver;

/**
 * Context used by the DSL to carry the internal registry and the qualifier it uses.
 */
class Context {

  private final DomainRegistry domainRegistry;
  private final AssemblerRegistry assemblerRegistry;
  private final Set<DtoInfoResolver> dtoInfoResolvers;
  private Annotation assemblerQualifier;
  private Class<? extends Annotation> assemblerQualifierClass;

  public Context(DomainRegistry domainRegistry, AssemblerRegistry assemblerRegistry,
    Set<DtoInfoResolver> dtoInfoResolvers) {
    this.domainRegistry = domainRegistry;
    this.assemblerRegistry = assemblerRegistry;
    this.dtoInfoResolvers = dtoInfoResolvers;
  }

  void setAssemblerQualifier(Annotation assemblerQualifier) {
    this.assemblerQualifier = assemblerQualifier;
  }

  void setAssemblerQualifierClass(Class<? extends Annotation> assemblerQualifierClass) {
    this.assemblerQualifierClass = assemblerQualifierClass;
  }

  <AggregateRootT extends AggregateRoot<IdT>, IdT, DtoT> Assembler<AggregateRootT, DtoT> assemblerOf(
    Class<AggregateRootT> aggregateRoot, Class<DtoT> dto) {
    if (assemblerQualifierClass != null) {
      return assemblerRegistry.getAssembler(aggregateRoot, dto, assemblerQualifierClass);
    } else if (assemblerQualifier != null) {
      return assemblerRegistry.getAssembler(aggregateRoot, dto, assemblerQualifier);
    }
    return assemblerRegistry.getAssembler(aggregateRoot, dto);
  }

  <TupleT extends Tuple, DtoT> Assembler<TupleT, DtoT> tupleAssemblerOf(
    Class<? extends AggregateRoot<?>>[] aggregateRootTuple, Class<DtoT> dto) {
    if (assemblerQualifierClass != null) {
      return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto, assemblerQualifierClass);
    } else if (assemblerQualifier != null) {
      return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto, assemblerQualifier);
    }
    return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto);
  }

  <DtoT, AggregateRootT extends AggregateRoot<IdT>, IdT> AggregateRootT create(DtoT dto,
    Class<AggregateRootT> aggregateClass) {
    return findResolverFor(dto).resolveAggregate(dto, aggregateClass);
  }

  <DtoT, AggregateRootT extends AggregateRoot<?>> AggregateRootT create(DtoT dto, Class<AggregateRootT> aggregateClass,
    int indexInTuple) {
    return findResolverFor(dto).resolveAggregate(dto, aggregateClass, indexInTuple);
  }

  <AggregateRootT extends AggregateRoot<IdT>, IdT> AggregateRootT load(IdT id, Class<AggregateRootT> aggregateClass) {
    return domainRegistry.getRepository(aggregateClass, BusinessUtils.getAggregateIdClass(aggregateClass)).get(id)
      .orElse(null);
  }

  <DtoT, IdT> IdT resolveId(DtoT dto, Class<IdT> aggregateIdClass) {
    return findResolverFor(dto).resolveId(dto, aggregateIdClass);
  }

  <DtoT, IdT> IdT resolveId(DtoT dto, Class<IdT> aggregateIdClass, int position) {
    return findResolverFor(dto).resolveId(dto, aggregateIdClass, position);
  }

  @SuppressWarnings("unchecked")
  <AggregateRootT extends AggregateRoot<IdT>, IdT, D> void mergeDtoIntoAggregate(D dto, AggregateRootT aggregateRoot) {
    checkNotNull(dto);
    checkNotNull(aggregateRoot);
    Assembler<AggregateRootT, D> assembler = assemblerOf((Class<AggregateRootT>) aggregateRoot.getClass(),
      (Class<D>) dto.getClass());
    assembler.mergeDtoIntoAggregate(dto, aggregateRoot);
  }

  @SuppressWarnings("unchecked")
  <DtoT, TupleT extends Tuple> void mergeDtoIntoTuple(DtoT dto, TupleT tuple,
    Class<? extends AggregateRoot<?>>[] aggregateClasses) {
    Assembler<Tuple, DtoT> tupleAssembler = tupleAssemblerOf(aggregateClasses, (Class<DtoT>) dto.getClass());
    tupleAssembler.mergeDtoIntoAggregate(dto, tuple);
  }

  private <DtoT> DtoInfoResolver findResolverFor(DtoT dto) {
    for (DtoInfoResolver dtoInfoResolver : dtoInfoResolvers) {
      if (dtoInfoResolver.supports(dto)) {
        return dtoInfoResolver;
      }
    }
    throw BusinessException.createNew(BusinessErrorCode.UNABLE_TO_FIND_SUITABLE_DTO_INFO_RESOLVER)
      .put("dtoClass", dto.getClass().getName());
  }
}
