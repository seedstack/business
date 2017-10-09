/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.AssemblerRegistry;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.util.Tuples;

class AssemblerRegistryImpl implements AssemblerRegistry {
    private final Injector injector;

    @Inject
    AssemblerRegistryImpl(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass,
            Class<D> dtoClass) {
        return findAssemblerOf(aggregateRootClass, dtoClass, null, null);
    }

    @Override
    public <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass, Class<D> dtoClass,
            @Nullable Annotation qualifier) {
        return findAssemblerOf(aggregateRootClass, dtoClass, qualifier, null);
    }

    @Override
    public <A extends AggregateRoot<?>, D> Assembler<A, D> getAssembler(Class<A> aggregateRootClass, Class<D> dtoClass,
            @Nullable Class<? extends Annotation> qualifier) {
        return findAssemblerOf(aggregateRootClass, dtoClass, null, qualifier);
    }

    @Override
    public <T extends Tuple, D> Assembler<T, D> getTupleAssembler(
            Class<? extends AggregateRoot<?>>[] aggregateRootClasses, Class<D> dtoClass) {
        return findAssemblerOf(classesToTupleType(aggregateRootClasses), dtoClass, null, null);
    }

    @Override
    public <T extends Tuple, D> Assembler<T, D> getTupleAssembler(
            Class<? extends AggregateRoot<?>>[] aggregateRootClasses, Class<D> dtoClass, Annotation qualifier) {
        return findAssemblerOf(classesToTupleType(aggregateRootClasses), dtoClass, qualifier, null);
    }

    @Override
    public <T extends Tuple, D> Assembler<T, D> getTupleAssembler(
            Class<? extends AggregateRoot<?>>[] aggregateRootClasses, Class<D> dtoClass,
            @Nullable Class<? extends Annotation> qualifier) {
        return findAssemblerOf(classesToTupleType(aggregateRootClasses), dtoClass, null, qualifier);
    }

    private Type classesToTupleType(Class<? extends AggregateRoot<?>>[] aggregateRootClasses) {
        return Types.newParameterizedType(Tuples.classOfTuple(aggregateRootClasses.length), aggregateRootClasses);
    }

    @SuppressWarnings("unchecked")
    private <T, D> Assembler<T, D> findAssemblerOf(Type aggregateRoot, Class<D> dto, @Nullable Annotation qualifier,
            @Nullable Class<? extends Annotation> qualifierClass) {
        Assembler<T, D> o;
        try {
            if (qualifier != null) {
                o = (Assembler<T, D>) getInstance(Assembler.class, qualifier, aggregateRoot, dto);
            } else if (qualifierClass != null) {
                o = (Assembler<T, D>) getInstance(Assembler.class, qualifierClass, aggregateRoot, dto);
            } else {
                o = (Assembler<T, D>) getInstance(Assembler.class, aggregateRoot, dto);
            }
        } catch (ConfigurationException e) {
            BusinessException seedException = BusinessException.createNew(
                    BusinessErrorCode.UNABLE_TO_FIND_ASSEMBLER_WITH_QUALIFIER)
                    .put("aggregateRoot", aggregateRoot)
                    .put("dto", dto);
            if (qualifier != null) {
                seedException.put("qualifier", qualifier);
                throw seedException;
            } else if (qualifierClass != null) {
                seedException.put("qualifier", qualifierClass.getSimpleName());
                throw seedException;
            } else {
                throw BusinessException.createNew(BusinessErrorCode.UNABLE_TO_FIND_ASSEMBLER)
                        .put("aggregateRoot", aggregateRoot)
                        .put("dto", dto);
            }
        }
        return o;
    }

    private Object getInstance(Type rawType, Class<? extends Annotation> qualifier, Type... typeArguments) {
        return injector.getInstance(
                Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments)), qualifier));
    }

    private Object getInstance(Type rawType, Annotation qualifier, Type... typeArguments) {
        return injector.getInstance(
                Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments)), qualifier));
    }

    private Object getInstance(Type rawType, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments))));
    }
}
