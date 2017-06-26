/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.AssemblePage;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


public class AssemblePageImpl implements AssemblePageWithQualifier {
    private final AssemblerDslContext context;
    private Page pagination;
    private final List<? extends AggregateRoot<?>> aggregates;

    AssemblePageImpl(AssemblerDslContext context, Page pagination) {
        this.context = context;
        this.pagination = pagination;
        this.aggregates = pagination.getView();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> Page<D> to(Class<D> dtoClass) {
        Assembler assembler = getAssembler(dtoClass);
        List<D> dtos = new ArrayList<>();

        if (aggregates != null && !aggregates.isEmpty()) {
            for (AggregateRoot<?> aggregate : aggregates) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregate));
            }
        }
        return new Page<>(dtos, this.pagination.getResultSize(), this.pagination.getIndex(), this.pagination.getCapacity(), this.pagination.getResultViewSize());
    }

    @SuppressWarnings("unchecked")
    private Assembler getAssembler(Class<?> dtoClass) {
        Assembler assembler = null;

        if (aggregates != null && !aggregates.isEmpty()) {
            assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregates.get(0).getClass(), dtoClass);
        }
        return assembler;
    }

    AssemblerDslContext getContext() {
        return context;
    }

    @Override
    public AssemblePage with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssemblePage with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }
}
