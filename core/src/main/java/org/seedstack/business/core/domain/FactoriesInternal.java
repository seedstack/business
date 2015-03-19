/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.domain;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import org.seedstack.business.api.Producible;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.GenericFactory;
import org.seedstack.business.helpers.Factories;
import org.seedstack.seed.core.api.SeedException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Set;


/**
 * This class is the main containers of all {@linkplain org.seedstack.business.api.domain.GenericFactory}s.
 * <p/>
 * it can be injected via
 * <p/>
 * <pre>
 * {@literal @}Inject
 * Factories factories;
 * </pre>
 * <p/>
 * and uses like the following.
 * <p/>
 * <pre>
 * ...
 * ProductFactory productFactory = factories.get(ProductFactory.class);
 * ...
 *
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 */
@SuppressWarnings("rawtypes")
public class FactoriesInternal implements Factories {

    @Inject
    @Named("factoriesTypes")
    private Set<Key<?>> factoriesClasses;

    private Multimap<Class, GenericFactory> factories;

    @Inject
    private Injector injector;

    /**
     * Constructor.
     */
    public FactoriesInternal() {
    }

    /**
     * Return the right factory instance in function of the class given as argument.
     *
     * @param producedClass the produced class
     * @return the factory
     */
    public <DO extends DomainObject & Producible> GenericFactory<DO> get(Class<DO> producedClass) {
        GenericFactory<DO> factory;
        initFactories();
        factory = getFactory(producedClass);
        return factory;
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject & Producible> GenericFactory<T> getFactory(Class<T> aggregateClass) {
        Collection<GenericFactory> factories = this.factories.get(aggregateClass);

        if (factories != null && factories.size() > 1) {
            throw SeedException.createNew(FactoriesErrorCodes.MULTIPLE_FACTORIES_FOUND_FOR_AGGREGATE)
                    .put("aggregate", aggregateClass.getName()).put("factories", factories);
        }

        GenericFactory<T> genericFactory;
        if (factories == null || factories.isEmpty()) {
            genericFactory = (GenericFactory<T>) injector.getInstance(Key.get(TypeLiteral.get(Types.newParameterizedType(Factory.class, aggregateClass))));
        } else {
            genericFactory = factories.iterator().next();
        }

        return genericFactory;
    }

    //TODO PERF : see if we can create an instance if needed (get method maybe!)
    private void initFactories() {
        if (factories == null) {
            factories = ArrayListMultimap.create();
            for (Key<?> key : factoriesClasses) {
                GenericFactory<?> factory = (GenericFactory<?>) injector.getInstance(key);
                factories.put(factory.getProducedClass(), factory);
            }
        }
    }
}
