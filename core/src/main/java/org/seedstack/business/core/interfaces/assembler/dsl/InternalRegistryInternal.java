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

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.javatuples.Tuple;
import org.seedstack.business.api.Tuples;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.core.interfaces.ModelMapperAssembler;
import org.seedstack.business.core.interfaces.ModelMapperTupleAssembler;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

import javax.inject.Inject;
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
    public Assembler<?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto) {
        Assembler<?, ?> o;
        try {
            o = (Assembler<?, ?>) getInstance(Assembler.class, aggregateRoot, dto);
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a  base assembler for " + aggregateRoot + ", fallback on automatic assembler.", e);
            try {
                o = (Assembler<?, ?>) getInstance(ModelMapperAssembler.class, aggregateRoot, dto);
            } catch (ConfigurationException e2) {
                logger.debug(e2.getMessage(), e2);
                throw new IllegalStateException("No assembler found for assembling " + aggregateRoot.getSimpleName() + " and " + dto.getSimpleName() +
                        ". Check that you have created an Assembler for them or that you have annotated " + dto.getSimpleName() + " with @DtoOf(" + aggregateRoot.getSimpleName() + ".class)");
            }
        }
        return o;
    }

    @Override
    public Assembler<?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto) {
        Class<? extends Tuple> tupleRawType = Tuples.classOfTuple(aggregateRootTuple); // e.g. Pair or Tiplet
        Type[] typeArguments = aggregateRootTuple.toArray(new Type[aggregateRootTuple.size()]);
        ParameterizedType tupleType = Types.newParameterizedType(tupleRawType, typeArguments);
        Assembler<?, ?> o;
        try {
            o = (Assembler<?, ?>) getInstance(Assembler.class, tupleType, dto);
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a  base tuple assembler for " + tupleType + ", fallback on automatic tuple assembler.");
            o = (Assembler<?, ?>) getInstance(ModelMapperTupleAssembler.class, tupleType, dto);
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
        Class<?> keyClass = BusinessUtils.getAggregateIdClass(aggregateRootClass);
        // contrarily to others the repository already handle the fallback on default repositories
        return (Repository<?, ?>) getInstance(Repository.class, aggregateRootClass, keyClass);
    }

    private Object getInstance(Type rawType, Type... typeArguments) {
        return injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(rawType, typeArguments))));
    }
}
