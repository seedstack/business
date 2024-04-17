/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.spi.DtoInfoResolver;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Context used by the DSL to carry the internal registry and the qualifier it uses.
 */
class Context {
    private final DomainRegistry domainRegistry;
    private final AssemblerRegistry assemblerRegistry;
    private final Set<DtoInfoResolver> dtoInfoResolvers;
    private Annotation repoQualifier;
    private Class<? extends Annotation> repoQualifierClass;
    private Annotation factoryQualifier;
    private Class<? extends Annotation> factoryQualifierClass;
    private Annotation assemblerQualifier;
    private Class<? extends Annotation> assemblerQualifierClass;

    public Context(DomainRegistry domainRegistry, AssemblerRegistry assemblerRegistry,
                   Set<DtoInfoResolver> dtoInfoResolvers) {
        this.domainRegistry = domainRegistry;
        this.assemblerRegistry = assemblerRegistry;
        this.dtoInfoResolvers = dtoInfoResolvers;
    }

    public void setRepoQualifier(Annotation repoQualifier) {
        this.repoQualifier = repoQualifier;
    }

    public void setRepoQualifierClass(Class<? extends Annotation> repoQualifierClass) {
        this.repoQualifierClass = repoQualifierClass;
    }

    public void setFactoryQualifier(Annotation factoryQualifier) {
        this.factoryQualifier = factoryQualifier;
    }

    public void setFactoryQualifierClass(Class<? extends Annotation> factoryQualifierClass) {
        this.factoryQualifierClass = factoryQualifierClass;
    }

    void setAssemblerQualifier(Annotation assemblerQualifier) {
        this.assemblerQualifier = assemblerQualifier;
    }

    void setAssemblerQualifierClass(Class<? extends Annotation> assemblerQualifierClass) {
        this.assemblerQualifierClass = assemblerQualifierClass;
    }

    <A extends AggregateRoot<I>, I, D> Assembler<A, D> assemblerOf(Class<A> aggregateRoot, Class<D> dto) {
        if (assemblerQualifierClass != null) {
            return assemblerRegistry.getAssembler(aggregateRoot, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return assemblerRegistry.getAssembler(aggregateRoot, dto, assemblerQualifier);
        } else {
            return assemblerRegistry.getAssembler(aggregateRoot, dto);
        }
    }

    <T extends Tuple, D> Assembler<T, D> tupleAssemblerOf(Class<? extends AggregateRoot<?>>[] aggregateRootTuple,
                                                          Class<D> dto) {
        if (assemblerQualifierClass != null) {
            return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto, assemblerQualifier);
        } else {
            return assemblerRegistry.getTupleAssembler(aggregateRootTuple, dto);
        }
    }

    <D, A extends AggregateRoot<I>, I> A create(D dto, Class<A> aggregateClass) {
        // TODO: implement qualifier in resolvers
        if (factoryQualifierClass != null) {
            throw new UnsupportedOperationException("Factory qualifiers are not supported from FluentAssembler DSL yet");
        } else if (factoryQualifier != null) {
            throw new UnsupportedOperationException("Factory qualifiers are not supported from FluentAssembler DSL yet");
        } else {
            return findResolverFor(dto).resolveAggregate(dto, aggregateClass);
        }
    }

    <D, A extends AggregateRoot<?>> A create(D dto, Class<A> aggregateClass, int indexInTuple) {
        // TODO: implement qualifier in resolvers
        if (factoryQualifierClass != null) {
            throw new UnsupportedOperationException("Factory qualifiers are not supported from FluentAssembler DSL yet");
        } else if (factoryQualifier != null) {
            throw new UnsupportedOperationException("Factory qualifiers are not supported from FluentAssembler DSL yet");
        } else {
            return findResolverFor(dto).resolveAggregate(dto, aggregateClass, indexInTuple);
        }
    }

    <A extends AggregateRoot<I>, I> A load(I id, Class<A> aggregateClass) {
        Repository<A, I> repo;
        if (repoQualifierClass != null) {
            repo = domainRegistry.getRepository(aggregateClass, BusinessUtils.resolveAggregateIdClass(aggregateClass), repoQualifierClass);
        } else if (repoQualifier != null) {
            repo = domainRegistry.getRepository(aggregateClass, BusinessUtils.resolveAggregateIdClass(aggregateClass), repoQualifier);
        } else {
            repo = domainRegistry.getRepository(aggregateClass, BusinessUtils.resolveAggregateIdClass(aggregateClass));
        }
        return repo.get(id).orElse(null);
    }

    <D, I> I resolveId(D dto, Class<I> aggregateIdClass) {
        return findResolverFor(dto).resolveId(dto, aggregateIdClass);
    }

    <D, I> I resolveId(D dto, Class<I> aggregateIdClass, int position) {
        return findResolverFor(dto).resolveId(dto, aggregateIdClass, position);
    }

    @SuppressWarnings("unchecked")
    <A extends AggregateRoot<I>, I, D> void mergeDtoIntoAggregate(D dto, A aggregateRoot) {
        checkNotNull(dto);
        checkNotNull(aggregateRoot);
        Assembler<A, D> assembler = assemblerOf((Class<A>) aggregateRoot.getClass(), (Class<D>) dto.getClass());
        assembler.mergeDtoIntoAggregate(dto, aggregateRoot);
    }

    @SuppressWarnings("unchecked")
    <D, T extends Tuple> void mergeDtoIntoTuple(D dto, T tuple, Class<? extends AggregateRoot<?>>[] aggregateClasses) {
        Assembler<Tuple, D> tupleAssembler = tupleAssemblerOf(aggregateClasses, (Class<D>) dto.getClass());
        tupleAssembler.mergeDtoIntoAggregate(dto, tuple);
    }

    private <D> DtoInfoResolver findResolverFor(D dto) {
        for (DtoInfoResolver dtoInfoResolver : dtoInfoResolvers) {
            if (dtoInfoResolver.supports(dto)) {
                return dtoInfoResolver;
            }
        }
        throw BusinessException.createNew(BusinessErrorCode.UNABLE_TO_FIND_SUITABLE_DTO_INFO_RESOLVER)
                .put("dtoClass", dto.getClass()
                        .getName());
    }
}
