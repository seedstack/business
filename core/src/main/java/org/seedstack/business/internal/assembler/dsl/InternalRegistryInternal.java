/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.AssemblerErrorCodes;
import org.seedstack.business.internal.utils.BusinessReflectUtils;
import org.seedstack.seed.Logging;
import org.seedstack.seed.SeedException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class InternalRegistryInternal implements InternalRegistry {

    private final Injector injector;

    @Logging
    private Logger logger;

    /**
     * Constructor.
     *
     * @param injector the Guice injector used to retrieve the domain objects.
     */
    @Inject
    public InternalRegistryInternal(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto) {
        return findAssemblerOf(aggregateRoot, dto, null, null);
    }

    @Override
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto, @Nullable Annotation qualifier) {
        return findAssemblerOf(aggregateRoot, dto, qualifier, null);
    }

    @Override
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto, @Nullable Class<? extends Annotation> qualifier) {
        return findAssemblerOf(aggregateRoot, dto, null, qualifier);
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto) {
        return findAssemblerOf(listToTuple(aggregateRootTuple), dto, null, null);
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto, Annotation qualifier) {
        return findAssemblerOf(listToTuple(aggregateRootTuple), dto, qualifier, null);
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto, @Nullable Class<? extends Annotation> qualifier) {
        return findAssemblerOf(listToTuple(aggregateRootTuple), dto, null, qualifier);
    }

    private Type listToTuple(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple) {
        Class<? extends Tuple> tupleRawType = Tuples.classOfTuple(aggregateRootTuple); // e.g. Pair or Tiplet
        Type[] typeArguments = aggregateRootTuple.toArray(new Type[aggregateRootTuple.size()]);
        return Types.newParameterizedType(tupleRawType, typeArguments);
    }

    private Assembler<?, ?> findAssemblerOf(Type aggregateRoot, Class<?> dto, @Nullable Annotation qualifier, @Nullable Class<? extends Annotation> qualifierClass) {
        Assembler<?, ?> o;
        try {
            if (qualifier != null) {
                o = (Assembler<?, ?>) getInstance(Assembler.class, qualifier, aggregateRoot, dto);
            } else if (qualifierClass != null) {
                o = (Assembler<?, ?>) getInstance(Assembler.class, qualifierClass, aggregateRoot, dto);
            } else {
                o = (Assembler<?, ?>) getInstance(Assembler.class, aggregateRoot, dto);
            }
        } catch (ConfigurationException e) {
            SeedException seedException = SeedException.createNew(AssemblerErrorCodes.UNABLE_TO_FIND_ASSEMBLER_WITH_QUALIFIER)
                    .put("aggregateRoot", aggregateRoot)
                    .put("dto", dto);
            if (qualifier != null) {
                seedException.put("qualifier", qualifier);
                throw seedException;
            } else if (qualifierClass != null) {
                seedException.put("qualifier", qualifierClass.getSimpleName());
                throw seedException;
            } else {
                throw SeedException.createNew(AssemblerErrorCodes.UNABLE_TO_FIND_ASSEMBLER)
                        .put("aggregateRoot", aggregateRoot)
                        .put("dto", dto);
            }
        }
        return o;
    }

    @Override
    public GenericFactory<?> genericFactoryOf(Class<? extends AggregateRoot<?>> aggregateRoot) {
        GenericFactory<?> o;
        try {
            o = (GenericFactory<?>) getInstance(GenericFactory.class, aggregateRoot);
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a factory for " + aggregateRoot + ", fallback on default factory.");
            o = (GenericFactory<?>) getInstance(Factory.class, aggregateRoot);
        }
        return o;
    }

    @Override
    public Factory<?> defaultFactoryOf(Class<? extends DomainObject> domainObject) {
        return (Factory<?>) getInstance(Factory.class, domainObject);
    }

    @Override
    public Repository<?, ?> repositoryOf(Class<? extends AggregateRoot<?>> aggregateRootClass) {
        Class<?> keyClass = BusinessReflectUtils.getAggregateIdClass(aggregateRootClass);
        // contrarily to others the repository already handle the fallback on default repositories
        return (Repository<?, ?>) getInstance(Repository.class, aggregateRootClass, keyClass);
    }

    private Object getInstance(Type rawType, Class<? extends Annotation> qualifier, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments)), qualifier));
    }

    private Object getInstance(Type rawType, Annotation qualifier, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments)), qualifier));
    }

    private Object getInstance(Type rawType, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments))));
    }
}
