/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.AssembleSlice;
import org.seedstack.business.assembler.dsl.AssembleSliceWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.SimpleSlice;
import org.seedstack.business.pagination.Slice;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AssembleSliceImpl implements AssembleSliceWithQualifier {
    private final AssemblerDslContext context;
    private Slice<? extends AggregateRoot<?>> slice;

    AssembleSliceImpl(AssemblerDslContext context, Slice<? extends AggregateRoot<?>> slice) {
        this.context = context;
        this.slice = slice;
    }

    AssemblerDslContext getContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> Slice<D> to(Class<D> dtoClass) {
        List<D> dtos = new ArrayList<>();
        if (!slice.getItems().isEmpty()) {
            Assembler assembler = getAssembler(dtoClass);
            for (AggregateRoot<?> aggregate : slice.getItems()) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregate));
            }
        }
        return new SimpleSlice<>(dtos);
    }

    @Override
    public AssembleSlice with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleSlice with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @SuppressWarnings("unchecked")
    private Assembler getAssembler(Class<?> dtoClass) {
        return context.assemblerOf((Class<? extends AggregateRoot<?>>) slice.getItems().get(0).getClass(), dtoClass);
    }
}
