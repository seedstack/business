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


import com.google.inject.assistedinject.Assisted;
import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.interfaces.assembler.dsl.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Implements {@link org.seedstack.business.api.interfaces.assembler.dsl.AssemblerProvider}.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class AssemblerProviderImpl implements AssemblerProvider {

    private final AssemblerDslContext context;

    /**
     * Assisted constructor.
     */
    @Inject
    public AssemblerProviderImpl(InternalRegistry registry, @Assisted AssemblerDslContext context) {
        this.context = context;
        context.setRegistry(registry);
    }

    @Override
    public <D> AggsAssemblerProvider<D> dtos(List<D> dtos) {
        return new AggsAssemblerProviderImpl<D>(context, dtos);
    }

    @Override
    public <D> AggAssemblerProvider<D> dto(D dto) {
        return new AggAssemblerProviderImpl<D>(context, dto);
    }

    @Override
    public DtoAssemblerProvider aggregate(AggregateRoot<?> aggregateRoot) {
        return new DtoAssemblerProviderImpl(context, aggregateRoot);
    }

    @Override
    public DtosAssemblerProvider aggregates(List<? extends AggregateRoot<?>> aggregateRoots) {
        return new DtosAssemblerProviderImpl(context, aggregateRoots, null);
    }

    @Override
    public DtoAssemblerProvider tuple(Tuple aggregateRoots) {
        return new DtoAssemblerProviderImpl(context, aggregateRoots);
    }

    @Override
    public DtosAssemblerProvider tuples(List<? extends Tuple> aggregateRoots) {
        return new DtosAssemblerProviderImpl(context, null, aggregateRoots);
    }

}
