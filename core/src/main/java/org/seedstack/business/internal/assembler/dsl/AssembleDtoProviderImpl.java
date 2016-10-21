/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Lists;
import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.dsl.AssembleDtoProvider;
import org.seedstack.business.assembler.dsl.AssembleDtoWithQualifierProvider;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


public class AssembleDtoProviderImpl implements AssembleDtoWithQualifierProvider {

    private final AssembleDtosProviderImpl dtosAssemblerProvider;

    public AssembleDtoProviderImpl(AssemblerDslContext context, AggregateRoot<?> aggregate) {
        List<? extends AggregateRoot<?>> aggregates = Lists.newArrayList(aggregate);
        this.dtosAssemblerProvider = new AssembleDtosProviderImpl(context, aggregates, null);
    }

    public AssembleDtoProviderImpl(AssemblerDslContext context, Tuple aggregateTuple) {
        List<Tuple> aggregateTuples = new ArrayList<>();
        aggregateTuples.add(aggregateTuple);
        this.dtosAssemblerProvider = new AssembleDtosProviderImpl(context, null, aggregateTuples);
    }

    @Override
    public <D> D to(Class<D> dtoClass) {
        List<D> ds = dtosAssemblerProvider.to(dtoClass);
        if (ds != null && !ds.isEmpty()) {
            return ds.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AssembleDtoProvider with(Annotation qualifier) {
        this.dtosAssemblerProvider.getContext().setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleDtoProvider with(Class<? extends Annotation> qualifier) {
        this.dtosAssemblerProvider.getContext().setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public AssembleDtoProvider with(AssemblerTypes qualifier) {
        this.dtosAssemblerProvider.getContext().setAssemblerQualifierClass(qualifier.get());
        return this;
    }
}
