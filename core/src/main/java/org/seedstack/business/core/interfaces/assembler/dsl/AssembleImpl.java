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

    @Inject
    private InternalRegistry registry;

    private boolean dataSecurityEnabled = false;

    /**
     * Constructor.
     */
    public AssembleImpl() {
    }

    @Override
    public <D> AggsAssemblerProvider<D> dtos(List<D> dtos) {
        return new AggsAssemblerProviderImpl<D>(registry, dtos);
    }

    @Override
    public <D> AggAssemblerProvider<D> dto(D dto) {
        return new AggAssemblerProviderImpl<D>(registry, dto);
    }

    @Override
    public DtoAssemblerProvider aggregate(AggregateRoot<?> aggregateRoot) {
        return new DtoAssemblerProviderImpl(registry, aggregateRoot);
    }

    @Override
    public DtosAssemblerProvider aggregates(List<? extends AggregateRoot<?>> aggregateRoots) {
        return new DtosAssemblerProviderImpl(registry, aggregateRoots, null);
    }

    @Override
    public DtoAssemblerProvider tuple(Tuple aggregateRoots) {
        return new DtoAssemblerProviderImpl(registry, aggregateRoots);
    }

    @Override
    public DtosAssemblerProvider tuples(List<? extends Tuple> aggregateRoots) {
        return new DtosAssemblerProviderImpl(registry, null, aggregateRoots);
    }

    @Override
    public Assemble securely() {
        dataSecurityEnabled = true;
        return this;
    }
}
