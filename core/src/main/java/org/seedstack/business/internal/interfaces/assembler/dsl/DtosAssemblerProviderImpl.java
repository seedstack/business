/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.DtosAssemblerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class DtosAssemblerProviderImpl implements DtosAssemblerProvider {

    private final InternalRegistry registry;
    private final AssemblerDslContext context;

    private final List<? extends AggregateRoot<?>> aggregates;
    private final List<? extends Tuple> aggregateTuples;

    public DtosAssemblerProviderImpl(AssemblerDslContext context, List<? extends AggregateRoot<?>> aggregates, List<? extends Tuple> aggregateTuples) {
        this.context = context;
        this.registry = context.getRegistry();
        this.aggregates = aggregates;
        this.aggregateTuples = aggregateTuples;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D> List<D> to(Class<D> dtoClass) {
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

        Assembler assembler = null;

        if (aggregates != null && !aggregates.isEmpty()) {
            assembler = registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregates.get(0).getClass(), dtoClass);
        } else if (aggregateTuples != null && !aggregateTuples.isEmpty()) {
            Tuple firstTuple = aggregateTuples.get(0);
            List<?> aggregateRootClasses = Tuples.toListOfClasses(firstTuple);
            assembler = registry.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dtoClass);
        }
        return assembler;
    }
}
