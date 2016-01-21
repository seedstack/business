/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.common.reflect.TypeToken;
import org.javatuples.Tuple;
import org.seedstack.business.Tuples;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.internal.strategy.GenericBindingStrategy;
import org.seedstack.business.internal.strategy.api.BindingStrategy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Collects the binding strategies for default assemblers.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
class DefaultAssemblerCollector {

    private final Collection<Class<?>> defaultAssemblersClasses;

    /**
     * Constructor.
     *
     * @param defaultAssemblersClasses the default assembler classes to bind
     */
    public DefaultAssemblerCollector(Collection<Class<?>> defaultAssemblersClasses) {
        this.defaultAssemblersClasses = defaultAssemblersClasses;
    }

    /**
     * Provides the list of binding strategies for the default assemblers based
     * on classes annotated by {@literal @}DtoOf.
     *
     * @param dtoClasses the DTO classes annotated by {@literal @}DtoOf
     * @return collection of default assembler binding strategies
     */
    public Collection<BindingStrategy> collect(Collection<Class<?>> dtoClasses) {
        // Contains pairs of aggregateClass/dtoClass
        Set<Type[]> autoAssemblerGenerics = new HashSet<Type[]>();
        // Contains pairs of aggregateTuple/dtoClass
        Set<Type[]> autoTupleAssemblerGenerics = new HashSet<Type[]>();

        // Extract pair of aggregateClass/dtoClass
        for (Class<?> dtoClass : dtoClasses) {
            DtoOf dtoOf = dtoClass.getAnnotation(DtoOf.class);
            // Silently ignore bad arguments
            if (dtoOf == null) {
                continue;
            }
            if (dtoOf.value().length == 1) {
                Class<? extends AggregateRoot<?>> aggregateClass = dtoOf.value()[0];
                autoAssemblerGenerics.add(new Type[]{aggregateClass, dtoClass});
            } else if (dtoOf.value().length > 1) {
                autoTupleAssemblerGenerics.add(new Type[]{Tuples.typeOfTuple(dtoOf.value()), dtoClass});
            }
        }

        Collection<BindingStrategy> bs = new ArrayList<BindingStrategy>();
        // Each pairs of aggregateClass/dtoClass or aggregateTuple/dtoClass is bind to all the default assemblers
        for (Class<?> defaultAssemblersClass : defaultAssemblersClasses) {
            Class<?> aggregateType = TypeToken.of(defaultAssemblersClass)
                    .resolveType(defaultAssemblersClass.getTypeParameters()[0]).getRawType();

            if (aggregateType.isAssignableFrom(Tuple.class) && !autoTupleAssemblerGenerics.isEmpty()) {
                bs.add(new GenericBindingStrategy<Assembler>(Assembler.class, defaultAssemblersClass, autoTupleAssemblerGenerics));

            } else if (!aggregateType.isAssignableFrom(Tuple.class) && !autoAssemblerGenerics.isEmpty()){
                bs.add(new GenericBindingStrategy<Assembler>(Assembler.class, defaultAssemblersClass, autoAssemblerGenerics));
            }
        }
        return bs;
    }
}
