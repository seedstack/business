/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.dsl.AssembleChunk;
import org.seedstack.business.assembler.dsl.AssembleChunkWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Chunk;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AssembleChunkImpl implements AssembleChunkWithQualifier {
    private final AssemblerDslContext context;
    private Chunk pagination;
    private final List<? extends AggregateRoot<?>> aggregates;

    AssembleChunkImpl(AssemblerDslContext context, Chunk pagination) {
        this.context = context;
        this.pagination = pagination;
        this.aggregates = pagination.getView();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> Chunk<D> to(Class<D> dtoClass) {
        Assembler assembler = getAssembler(dtoClass);
        List<D> dtos = new ArrayList<>();

        if (aggregates != null && !aggregates.isEmpty()) {
            for (AggregateRoot<?> aggregate : aggregates) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregate));
            }
        }
        return new Chunk<>(dtos, this.pagination.getChunkOffset(), this.pagination.getResultViewSize());
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
    public AssembleChunk with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleChunk with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }
}
