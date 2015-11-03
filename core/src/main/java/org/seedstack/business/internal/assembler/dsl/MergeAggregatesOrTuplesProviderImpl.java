/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.common.collect.Lists;
import org.javatuples.*;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerTypes;
import org.seedstack.business.assembler.dsl.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class MergeAggregatesOrTuplesProviderImpl<D> implements MergeAggregatesOrTuplesWithQualifierProvider<D> {

    private final AssemblerDslContext context;
    private final List<D> dtos;

    public MergeAggregatesOrTuplesProviderImpl(AssemblerDslContext context, List<D> dtos) {
        this.context = context;
        this.dtos = dtos;
    }

    @Override
    public <A extends AggregateRoot<?>> MergeAggregatesWithRepoProvider<A> into(Class<A> aggregateRootClass) {
        return new MergeAggregatesWithRepoProviderImpl<A>(context, aggregateRootClass, dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Pair<A1, A2>> into(Class<A1> first, Class<A2> second) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Pair<A1, A2>>(context, Lists.newArrayList(first, second), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Triplet<A1, A2, A3>> into(Class<A1> first, Class<A2> second, Class<A3> third) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Triplet<A1, A2, A3>>(context, Lists.newArrayList(first, second, third), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Quartet<A1, A2, A3, A4>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Quartet<A1, A2, A3, A4>>(context, Lists.newArrayList(first, second, third, fourth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Quintet<A1, A2, A3, A4, A5>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Quintet<A1, A2, A3, A4, A5>>(context, Lists.newArrayList(first, second, third, fourth, fifth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Sextet<A1, A2, A3, A4, A5, A6>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Sextet<A1, A2, A3, A4, A5, A6>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Septet<A1, A2, A3, A4, A5, A6, A7>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Septet<A1, A2, A3, A4, A5, A6, A7>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Octet<A1, A2, A3, A4, A5, A6, A7, A8>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Octet<A1, A2, A3, A4, A5, A6, A7, A8>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Ennead<A1, A2, A3, A4, A5, A6, A7, A8, A9>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth), dtos);
    }

    @Override
    public <A1 extends AggregateRoot<?>, A2 extends AggregateRoot<?>, A3 extends AggregateRoot<?>, A4 extends AggregateRoot<?>, A5 extends AggregateRoot<?>, A6 extends AggregateRoot<?>, A7 extends AggregateRoot<?>, A8 extends AggregateRoot<?>, A9 extends AggregateRoot<?>, A10 extends AggregateRoot<?>>
    MergeTuplesWithRepositoryProvider<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> into(Class<A1> first, Class<A2> second, Class<A3> third, Class<A4> fourth, Class<A5> fifth, Class<A6> sixth, Class<A7> seventh, Class<A8> eighth, Class<A9> ninth, Class<A10> tenth) {
        return new MergeMergeTuplesWithRepositoryProviderImpl<Decade<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>>(context, Lists.newArrayList(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth), dtos);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public MergeTuplesWithRepositoryProvider<Tuple> into(Class<? extends AggregateRoot<?>>[] aggregateRootClasses) {
        // Still used by our legacy Assemblers but not part of the API
        return new MergeMergeTuplesWithRepositoryProviderImpl<Tuple>(context, Arrays.asList(aggregateRootClasses), dtos);
    }

    @Override
    public <A extends AggregateRoot<?>> void into(List<A> aggregateRoots) {
        if (aggregateRoots != null && dtos != null) {
            if (aggregateRoots.size() != dtos.size()) {
                throw new IllegalArgumentException("The list of dto should have the same size as the list of aggregate");
            }
            Assembler assembler = context.assemblerOf((Class<? extends AggregateRoot<?>>) aggregateRoots.get(0).getClass(), dtos.get(0).getClass());
            for (int i = 0; i < aggregateRoots.size(); i++) {
                assembler.mergeAggregateWithDto(aggregateRoots.get(i), dtos.get(i));
            }
        }
    }

    @Override
    public MergeAggregatesOrTuplesProvider<D> with(Annotation qualifier) {
        context.setAssemblerQualifier(qualifier);
        return this;
    }

    @Override
    public MergeAggregatesOrTuplesProvider<D> with(Class<? extends Annotation> qualifier) {
        context.setAssemblerQualifierClass(qualifier);
        return this;
    }

    @Override
    public MergeAggregatesOrTuplesProvider<D> with(AssemblerTypes qualifier) {
        context.setAssemblerQualifierClass(qualifier.get());
        return this;
    }
}
