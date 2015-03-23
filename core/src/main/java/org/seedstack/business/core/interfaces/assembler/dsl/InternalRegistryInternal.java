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

import com.google.common.collect.Lists;
import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.javatuples.Tuple;
import org.seedstack.business.api.domain.*;
import org.seedstack.business.api.interfaces.assembler.Assembler;
import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.api.interfaces.assembler.BaseTupleAssembler;
import org.seedstack.business.core.interfaces.AutomaticAssembler;
import org.seedstack.business.core.interfaces.AutomaticTupleAssembler;
import org.seedstack.business.helpers.Tuples;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Pierre Thirouin <pierre.thirouin@ext.mpsa.com>
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
    public Assembler<?, ?, ?> assemblerOf(Class<? extends AggregateRoot<?>> aggregateRoot, Class<?> dto) {
        Assembler<?, ?, ?> o;
        try {
            o = (Assembler<?, ?, ?>) getInstance(BaseAssembler.class, aggregateRoot, dto);
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a  base assembler for " + aggregateRoot + ", fallback on automatic assembler.");
            o = (Assembler<?, ?, ?>) getInstance(AutomaticAssembler.class, aggregateRoot, dto);
        }
        return o;
    }

    @Override
    public Assembler<?, ?, ?> tupleAssemblerOf(Tuple aggregateRootTuple, Class<?> dto) {
        List<Class<? extends AggregateRoot<?>>> aggregateClasses = Lists.newArrayList();
        for (Object o : aggregateRootTuple) {
            if (!(o instanceof AggregateRoot<?>)) {
                throw new IllegalArgumentException("The aggregateRootTuple parameter should only contain aggregates. But found " + o);
            }
            aggregateClasses.add((Class<? extends AggregateRoot<?>>) o.getClass());
        }
        return tupleAssemblerOf(aggregateClasses, dto);
    }

    @Override
    public Assembler<?, ?, ?> tupleAssemblerOf(List<Class<? extends AggregateRoot<?>>> aggregateRootTuple, Class<?> dto) {
        Class<? extends Tuple> tupleRawType = Tuples.classOfTuple(aggregateRootTuple); // e.g. Pair or Tiplet
        ParameterizedType tupleType = Types.newParameterizedType(tupleRawType, aggregateRootTuple.toArray(new Type[aggregateRootTuple.size()]));
        Assembler<?, ?, ?> o;
        try {
            o = (Assembler<?, ?, ?>) getInstance(BaseTupleAssembler.class, tupleType, dto, Tuple.class);
        } catch (ConfigurationException e) {
            logger.trace("Unable to find a  base tuple assembler for " + tupleType + ", fallback on automatic tuple assembler.");
            o = (Assembler<?, ?, ?>) getInstance(AutomaticTupleAssembler.class, tupleType, dto);
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
