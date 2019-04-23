/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import java.lang.annotation.Annotation;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.AssemblePage;
import org.seedstack.business.assembler.dsl.AssemblePageWithQualifier;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.SimplePage;

class AssemblePageImpl<A extends AggregateRoot<I>, I, T extends Tuple> extends AssembleMultipleImpl<A, I, T>
        implements AssemblePageWithQualifier {

    private final Page<A> pageOfAggregates;
    private final Page<T> pageOfTuples;

    AssemblePageImpl(Context context, Page<A> pageOfAggregates, Page<T> pageOfTuples) {
        super(context, pageOfAggregates == null ? null : pageOfAggregates.getItems()
                .stream(), pageOfTuples == null ? null : pageOfTuples.getItems()
                .stream());
        this.pageOfAggregates = pageOfAggregates;
        this.pageOfTuples = pageOfTuples;
    }

    @Override
    public <D> Page<D> toPageOf(Class<D> dtoClass) {
        if (pageOfAggregates != null) {
            return new SimplePage<>(super.toListOf(dtoClass), pageOfAggregates.getIndex(),
                    pageOfAggregates.getMaxSize(), pageOfAggregates.getTotalSize());
        } else if (pageOfTuples != null) {
            return new SimplePage<>(super.toListOf(dtoClass), pageOfTuples.getIndex(), pageOfTuples.getMaxSize(),
                    pageOfTuples.getTotalSize());
        }
        throw new IllegalStateException("Nothing to assemble");
    }

    @Override
    public AssemblePage with(Annotation qualifier) {
        getContext().setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssemblePage with(Class<? extends Annotation> qualifier) {
        getContext().setAssemblerQualifierClass(qualifier);
        return this;
    }
}
