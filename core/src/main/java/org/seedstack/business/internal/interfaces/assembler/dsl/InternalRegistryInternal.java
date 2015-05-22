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

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.internal.utils.BusinessReflectUtils;
import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class InternalRegistryInternal implements InternalRegistry {

    @Logging
    private Logger logger;

    Injector injector;

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
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto, @Nullable Annotation qualifier) {
        return findAssemblerOf(aggregateRoot, dto, qualifier);
    }

    @Override
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto) {
        return assemblerOf(aggregateRoot, dto, null);
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto, Annotation qualifier) {
        Class<? extends Tuple> tupleRawType = Tuples.classOfTuple(aggregateRootTuple); // e.g. Pair or Tiplet
        Type[] typeArguments = aggregateRootTuple.toArray(new Type[aggregateRootTuple.size()]);
        ParameterizedType tupleType = Types.newParameterizedType(tupleRawType, typeArguments);
        return findAssemblerOf(tupleType, dto, qualifier);
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto) {
        return tupleAssemblerOf(aggregateRootTuple, dto, null);
    }

    private Assembler<?, ?> findAssemblerOf(Type aggregateRoot, Class<?> dto, @Nullable Annotation qualifier) {
        Assembler<?, ?> o;
        try {
            if (qualifier != null) {
                o = (Assembler<?, ?>) getInstance(Assembler.class, qualifier, aggregateRoot, dto);
            } else {
                o = (Assembler<?, ?>) getInstance(Assembler.class, aggregateRoot, dto);
            }
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a  base assembler for " + aggregateRoot + ", fallback on automatic assembler.", e);
            try {
                o = (Assembler<?, ?>) getInstance(Assembler.class, Names.named("ModelMapper"), aggregateRoot, dto);
            } catch (ConfigurationException e2) {
                logger.debug(e2.getMessage(), e2);
                throw new IllegalStateException("No assembler found for assembling " + aggregateRoot.toString() + " and " + dto.getSimpleName() +
                        ". Check that you have created an Assembler for them or that you have annotated " + dto.getSimpleName() + " with @DtoOf", e2);
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

    private Object getInstance(Type rawType, Annotation qualifier, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments)), qualifier));
    }

    private Object getInstance(Type rawType, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments))));
    }
}
