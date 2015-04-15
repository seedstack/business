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
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.dsl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
 */
public class AggAssemblerProviderImpl implements BaseAggAssemblerProvider, AggAssemblerProvider, AggsAssemblerProvider {

    private InternalRegistry registry;
    private final AssemblerContext assemblerContext;

    public AggAssemblerProviderImpl(InternalRegistry registry, AssemblerContext assemblerContext) {
        this.registry = registry;
        this.assemblerContext = assemblerContext;
    }

    @Override
    public <A extends AggregateRoot<?>> AggAssemblerWithRepoProvider<A> to(Class<A> aggregateRootClass) {
        assemblerContext.setAggregateClass(aggregateRootClass);
        return new AggAssemblerWithRepoProviderImpl<A>(registry, assemblerContext);
    }

    @Override
    public <T extends Tuple> TupleAggAssemblerWithRepoProvider<T> to(Class<? extends AggregateRoot<?>> firstAggregateClass, Class<? extends AggregateRoot<?>> secondAggregateClass, Class<? extends AggregateRoot<?>>... otherAggregateClasses) {
        List<Class<?>> aggregateRootClasses = new ArrayList<Class<?>>();
        aggregateRootClasses.add(firstAggregateClass);
        aggregateRootClasses.add(secondAggregateClass);
        aggregateRootClasses.addAll(Arrays.asList(otherAggregateClasses));
        assemblerContext.setAggregateClasses(Tuples.create((List)aggregateRootClasses));
        return new TupleAggAssemblerWithRepoProviderImpl<T>(registry, assemblerContext);
    }

    @Override
    public <A extends AggregateRoot<?>> A to(A aggregateRoot) {
        Assembler assembler = registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoot.getClass(), assemblerContext.getDto().getClass());
        assembler.mergeAggregateWithDto(aggregateRoot, assemblerContext.getDto());
        return aggregateRoot;
    }

    @Override
    public <A extends AggregateRoot<?>> List<A> to(List<A> aggregateRoots) {
        List<?> dtos = assemblerContext.getDtos();
        if (aggregateRoots != null && dtos != null) {
            if (aggregateRoots.size() != dtos.size()) {
                throw new IllegalArgumentException("The list of dto should have the same size as the list of aggregate");
            }
            Assembler assembler = registry.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.get(0).getClass(), dtos.get(0).getClass());
            for (int i = 0; i < aggregateRoots.size(); i++) {
                assembler.mergeAggregateWithDto(aggregateRoots.get(i), dtos.get(i));
            }
        }
        return aggregateRoots;
    }

}
