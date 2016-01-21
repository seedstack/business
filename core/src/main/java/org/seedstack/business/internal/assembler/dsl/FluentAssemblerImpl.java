/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import org.javatuples.Tuple;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.dsl.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link org.seedstack.business.assembler.FluentAssembler}.
 * <p>
 * It uses a Guice provider to get the DSL entry point. Each time you call the {@code assemble()}
 * method a new DSL instance is provided.
 * </p>
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class FluentAssemblerImpl implements FluentAssembler {

    private AssemblerDslContext context;

    @Inject
    public FluentAssemblerImpl(InternalRegistry registry) {
        context = new AssemblerDslContext();
        context.setRegistry(registry);
    }

    @Override
    public AssembleDtoWithQualifierProvider assemble(AggregateRoot<?> aggregateRoot) {
        return new AssembleDtoProviderImpl(context, aggregateRoot);
    }

    @Override
    public AssembleDtosWithQualifierProvider assemble(List<? extends AggregateRoot<?>> aggregateRoots) {
        return new AssembleDtosProviderImpl(context, aggregateRoots, null);
    }

    @Override
    public AssembleDtoWithQualifierProvider assembleTuple(Tuple aggregateRoots) {
        return new AssembleDtoProviderImpl(context, aggregateRoots);
    }

    @Override
    public AssembleDtosWithQualifierProvider assembleTuple(List<? extends Tuple> aggregateRoots) {
        return new AssembleDtosProviderImpl(context, null, aggregateRoots);
    }

    @Override
    public <D> MergeAggregateOrTupleWithQualifierProvider<D> merge(D dto) {
        return new MergeAggregateOrTupleProviderImpl<D>(context, dto);
    }

    @Override
    public <D> MergeAggregatesOrTuplesWithQualifierProvider<D> merge(List<D> dtos) {
        return new MergeAggregatesOrTuplesProviderImpl<D>(context, dtos);
    }
}
