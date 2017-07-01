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
import org.seedstack.business.pagination.SimplePage;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AssemblePageImpl implements AssemblePageWithQualifier {
    private final AssemblerDslContext context;
    private final Page<? extends AggregateRoot<?>> page;

    AssemblePageImpl(AssemblerDslContext context, Page<? extends AggregateRoot<?>> page) {
        this.context = context;
        this.page = page;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> Page<D> to(Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>();
        if (!page.getItems().isEmpty()) {
            Assembler assembler = getAssembler(dtoClass);
            for (AggregateRoot<?> aggregate : page.getItems()) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregate));
            }
        }
        return new SimplePage<>(dtos, page.getIndex(), page.getCapacity(), page.getTotalSize());
    }

    @SuppressWarnings("unchecked")
    private Assembler getAssembler(Class<?> dtoClass) {
        return context.assemblerOf((Class<? extends AggregateRoot<?>>) page.getItems().get(0).getClass(), dtoClass);
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
