/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.Producible;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainObject;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.utils.BusinessUtils;

import java.lang.annotation.Annotation;

/**
 * Context used by the DSL to carry the internal registry and the qualifier it uses.
 */
class Context {
    private final DomainRegistry domainRegistry;
    private final AssemblerRegistry assemblerRegistry;
    private Annotation assemblerQualifier;
    private Class<? extends Annotation> assemblerQualifierClass;

    public Context(DomainRegistry domainRegistry, AssemblerRegistry assemblerRegistry) {
        this.domainRegistry = domainRegistry;
        this.assemblerRegistry = assemblerRegistry;
    }

    void setAssemblerQualifier(Annotation assemblerQualifier) {
        this.assemblerQualifier = assemblerQualifier;
    }

    void setAssemblerQualifierClass(Class<? extends Annotation> assemblerQualifierClass) {
        this.assemblerQualifierClass = assemblerQualifierClass;
    }

    <A extends AggregateRoot<ID>, ID, D> Assembler<A, D> assemblerOf(Class<A> aggregateRoot, Class<D> dto) {
        // TODO: cache
        if (assemblerQualifierClass != null) {
            return assemblerRegistry.assemblerOf(aggregateRoot, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return assemblerRegistry.assemblerOf(aggregateRoot, dto, assemblerQualifier);
        }
        return assemblerRegistry.assemblerOf(aggregateRoot, dto);
    }

    <T extends Tuple, D> Assembler<T, D> tupleAssemblerOf(Class<? extends AggregateRoot<?>>[] aggregateRootTuple, Class<D> dto) {
        // TODO: cache
        if (assemblerQualifierClass != null) {
            return assemblerRegistry.tupleAssemblerOf(aggregateRootTuple, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return assemblerRegistry.tupleAssemblerOf(aggregateRootTuple, dto, assemblerQualifier);
        }
        return assemblerRegistry.tupleAssemblerOf(aggregateRootTuple, dto);
    }

    <DO extends DomainObject & Producible> Factory<DO> factoryOf(Class<DO> producibleClass) {
        // TODO: cache
        return domainRegistry.getFactory(producibleClass);
    }

    <A extends AggregateRoot<ID>, ID> Repository<A, ID> repositoryOf(Class<A> aggregateRootClass) {
        // TODO: cache
        return domainRegistry.getRepository(aggregateRootClass, BusinessUtils.getAggregateIdClass(aggregateRootClass));
    }
}
