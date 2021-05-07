/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.dsl.AssembleSingle;
import org.seedstack.business.assembler.dsl.AssembleSingleWithQualifier;
import org.seedstack.business.domain.AggregateRoot;

class AssembleSingleImpl<A extends AggregateRoot<I>, I, T extends Tuple> implements AssembleSingleWithQualifier {

    private final Context context;
    private final AssembleMultipleImpl<A, I, T> multipleAssembler;

    AssembleSingleImpl(Context context, A aggregateRoot, T tuple) {
        this.context = context;
        this.multipleAssembler = new AssembleMultipleImpl<>(context,
                aggregateRoot == null ? null : Stream.of(aggregateRoot), tuple == null ? null : Stream.of(tuple));
    }

    @Override
    public <D> D to(Class<D> dtoClass) {
        return multipleAssembler.toStreamOf(dtoClass)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nothing to assemble"));
    }

    @Override
    public AssembleSingle with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public AssembleSingle with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public AssembleSingle with(String qualifier) {
        context.setAssemblerQualifier(Names.named(qualifier));
        return this;
    }
}
