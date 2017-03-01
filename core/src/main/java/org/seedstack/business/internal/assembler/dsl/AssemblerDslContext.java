/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainObject;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Context used by the DSL to carry the internal registry and the qualifier it uses.
 */
class AssemblerDslContext {

    private InternalRegistry registry;

    private Annotation assemblerQualifier;

    private Class<? extends Annotation> assemblerQualifierClass;

    // ----- Assembler qualifiers

    public Annotation getAssemblerQualifier() {
        return assemblerQualifier;
    }

    public void setAssemblerQualifier(Annotation assemblerQualifier) {
        this.assemblerQualifier = assemblerQualifier;
    }

    public Class<? extends Annotation> getAssemblerQualifierClass() {
        return assemblerQualifierClass;
    }

    public void setAssemblerQualifierClass(Class<? extends Annotation> assemblerQualifierClass) {
        this.assemblerQualifierClass = assemblerQualifierClass;
    }

    public void setRegistry(InternalRegistry registry) {
        this.registry = registry;
    }

    // ----- Internal registry methods

    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto) {
        if (assemblerQualifierClass != null) {
            return registry.assemblerOf(aggregateRoot, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return registry.assemblerOf(aggregateRoot, dto, assemblerQualifier);
        }
        return registry.assemblerOf(aggregateRoot, dto);
    }

    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto) {
        if (assemblerQualifierClass != null) {
            return registry.tupleAssemblerOf(aggregateRootTuple, dto, assemblerQualifierClass);
        } else if (assemblerQualifier != null) {
            return registry.tupleAssemblerOf(aggregateRootTuple, dto, assemblerQualifier);
        }
        return registry.tupleAssemblerOf(aggregateRootTuple, dto);
    }

    public Factory<?> genericFactoryOf(Class<? extends AggregateRoot<?>> aggregateRoot) {
        return registry.genericFactoryOf(aggregateRoot);
    }

    public Factory<?> defaultFactoryOf(Class<? extends DomainObject> domainObject) {
        return registry.defaultFactoryOf(domainObject);
    }

    public Repository<?, ?> repositoryOf(Class<? extends AggregateRoot<?>> aggregateRoot) {
        return registry.repositoryOf(aggregateRoot);
    }
}
