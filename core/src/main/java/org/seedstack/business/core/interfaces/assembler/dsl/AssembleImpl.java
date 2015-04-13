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
import org.seedstack.business.api.interfaces.assembler.dsl.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Implements {@link org.seedstack.business.api.interfaces.assembler.dsl.Assemble}
 * and {@link org.seedstack.business.api.interfaces.assembler.dsl.AssembleSecurely}.
 *
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AssembleImpl implements Assemble, AssembleSecurely {

    private final AssemblerContext assemblerContext = new AssemblerContext();

    @Inject
    private InternalRegistry registry;

    /**
     * Constructor.
     * <p>
     * Disable the data security feature by default.
     * </p>
     */
    public AssembleImpl() {
        assemblerContext.setSecured(false);
    }

    @Override
    public AggsAssemblerProvider dtos(List<Object> dtos) {
        assemblerContext.setDtos(dtos);
        return new AggAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public AggAssemblerProvider dto(Object dto) {
        assemblerContext.setDto(dto);
        return new AggAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public DtoAssemblerProvider aggregate(AggregateRoot<?> aggregateRoot) {
        assemblerContext.setAggregate(aggregateRoot);
        return new DtoAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public DtosAssemblerProvider aggregates(List<? extends AggregateRoot<?>> aggregateRoots) {
        assemblerContext.setAggregates((List<AggregateRoot<?>>) aggregateRoots);
        return new DtosAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public DtoAssemblerProvider tuple(Tuple aggregateRoots) {
        assemblerContext.setAggregateTuple(aggregateRoots);
        return new DtoAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public DtosAssemblerProvider tuples(List<? extends Tuple> aggregateRoots) {
        assemblerContext.setAggregateTuples(aggregateRoots);
        return new DtosAssemblerProviderImpl(registry, assemblerContext);
    }

    @Override
    public Assemble securely() {
        assemblerContext.setSecured(true);
        return this;
    }
}
