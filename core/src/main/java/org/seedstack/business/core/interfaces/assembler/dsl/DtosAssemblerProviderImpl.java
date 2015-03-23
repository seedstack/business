/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.DtosAssemblerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class DtosAssemblerProviderImpl implements DtosAssemblerProvider {

    private InternalRegistry registry;

    private final AssemblerContext context;

    public DtosAssemblerProviderImpl(InternalRegistry registry, AssemblerContext context) {
        this.registry = registry;
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> List<D> to(Class<D> dtoClass) {
        List<? extends AggregateRoot<?>> aggregates = context.getAggregates();
        List<? extends Tuple> aggregateTuples = context.getAggregateTuples();

        Assembler assembler = getAssembler(dtoClass);
        List<D> dtos = new ArrayList<D>();

        if (aggregates != null && !aggregates.isEmpty()) {
            for (AggregateRoot<?> aggregate : aggregates) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregate));
            }
        } else if (aggregateTuples != null && !aggregateTuples.isEmpty()) {
            for (Tuple aggregateTuple : aggregateTuples) {
                dtos.add((D) assembler.assembleDtoFromAggregate(aggregateTuple));
            }
        }
        return dtos;
    }

    @SuppressWarnings("unchecked")
    private Assembler getAssembler(Class<?> dtoClass) {
        AggregateRoot<?> aggregate = context.getAggregate();
        Tuple aggregateTuple = context.getAggregateTuple();

        Assembler assembler = null;

        if (aggregate != null) {
            assembler = registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregate.getClass(), dtoClass);
        } else if (aggregateTuple != null) {
            assembler = registry.tupleAssemblerOf(aggregateTuple, dtoClass);
        }
        return assembler;
    }
}
