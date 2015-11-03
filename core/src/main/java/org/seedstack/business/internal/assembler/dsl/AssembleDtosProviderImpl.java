/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.Tuples;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.dsl.AssembleDtosProvider;
import org.seedstack.business.assembler.dsl.AssembleDtosWithQualifierProvider;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AssembleDtosProviderImpl implements AssembleDtosWithQualifierProvider {

    private final AssemblerDslContext context;

    private final List<? extends AggregateRoot<?>> aggregates;
    private final List<? extends Tuple> aggregateTuples;

    public AssembleDtosProviderImpl(AssemblerDslContext context, List<? extends AggregateRoot<?>> aggregates, List<? extends Tuple> aggregateTuples) {
        this.context = context;
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
            assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregates.get(0).getClass(), dtoClass);
        } else if (aggregateTuples != null && !aggregateTuples.isEmpty()) {
            Tuple firstTuple = aggregateTuples.get(0);
            List<?> aggregateRootClasses = Tuples.toListOfClasses(firstTuple);
            assembler = context.tupleAssemblerOf((List<Class<? extends AggregateRoot<?>>>) aggregateRootClasses, dtoClass);
        }
        return assembler;
    }

    AssemblerDslContext getContext() {
        return context;
    }

    @Override
    public AssembleDtosProvider with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleDtosProvider with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public AssembleDtosProvider with(AssemblerTypes qualifier) {
        context.setAssemblerQualifierClass(qualifier.get());
        return this;
    }
}
