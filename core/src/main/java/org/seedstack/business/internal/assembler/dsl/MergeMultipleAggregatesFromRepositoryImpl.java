/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import java.util.stream.Stream;
import org.seedstack.business.assembler.dsl.MergeAs;
import org.seedstack.business.assembler.dsl.MergeFromRepository;
import org.seedstack.business.assembler.dsl.MergeFromRepositoryOrFactory;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.utils.BusinessUtils;

class MergeMultipleAggregatesFromRepositoryImpl<A extends AggregateRoot<I>, I, D> implements
        MergeFromRepository<MergeAs<A>>, MergeFromRepositoryOrFactory<MergeAs<A>> {

    private final Context context;
    private final Class<A> aggregateClass;
    private final Class<I> aggregateClassId;
    private final Stream<D> dtoStream;

    MergeMultipleAggregatesFromRepositoryImpl(Context context, Stream<D> dtoStream, Class<A> aggregateClass) {
        this.context = context;
        this.dtoStream = dtoStream;
        this.aggregateClass = aggregateClass;
        this.aggregateClassId = BusinessUtils.resolveAggregateIdClass(aggregateClass);
    }

    @Override
    public MergeFromRepositoryOrFactory<MergeAs<A>> fromRepository() {
        return this;
    }

    @Override
    public MergeAs<A> fromFactory() {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            A a = context.create(dto, aggregateClass);
            context.mergeDtoIntoAggregate(dto, a);
            return a;
        }));
    }

    @Override
    public MergeAs<A> orFail() throws AggregateNotFoundException {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            I id = context.resolveId(dto, aggregateClassId);
            A a = context.load(id, aggregateClass);
            if (a == null) {
                throw new AggregateNotFoundException(
                        "Unable to load aggregate " + aggregateClass.getName() + "[" + id + "]");
            }
            context.mergeDtoIntoAggregate(dto, a);
            return a;
        }));
    }

    @Override
    public MergeAs<A> orFromFactory() {
        return new MergeAsImpl<>(dtoStream.map(dto -> {
            A a = context.load(context.resolveId(dto, aggregateClassId), aggregateClass);
            if (a == null) {
                a = context.create(dto, aggregateClass);
            }
            context.mergeDtoIntoAggregate(dto, a);
            return a;
        }));
    }
}
