/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.assembler;

import com.google.common.reflect.TypeToken;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.javatuples.Tuple;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.business.util.Tuples;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.guice.BindingStrategy;
import org.seedstack.seed.core.internal.guice.GenericBindingStrategy;

/**
 * Collects the binding strategies for default assemblers.
 */
class DefaultAssemblerCollector {
    private static final String DEFAULT_ASSEMBLER_KEY = "defaultAssembler";
    private final Collection<Class<? extends Assembler>> defaultAssemblersClasses;

    DefaultAssemblerCollector(Collection<Class<? extends Assembler>> defaultAssemblersClasses) {
        this.defaultAssemblersClasses = defaultAssemblersClasses;
    }

    /**
     * Provides the list of binding strategies for the default assemblers based on classes annotated
     * by {@literal
     *
     * @param dtoClasses the DTO classes annotated by {@literal @}DtoOf
     * @return collection of default assembler binding strategies
     * @}DtoOf.
     */
    Collection<BindingStrategy> collect(Application application, Collection<Class<?>> dtoClasses) {
        // Contains pairs of aggregateClass/dtoClass
        Map<Type[], Key<?>> autoAssemblerGenerics = new HashMap<>();
        // Contains pairs of aggregateTuple/dtoClass
        Map<Type[], Key<?>> autoTupleAssemblerGenerics = new HashMap<>();

        // Extract pair of aggregateClass/dtoClass
        for (Class<?> dtoClass : dtoClasses) {
            DtoOf dtoOf = dtoClass.getAnnotation(DtoOf.class);
            if (dtoOf != null) {
                if (dtoOf.value().length == 1) {
                    Type[] params = new Type[]{dtoOf.value()[0], dtoClass};
                    autoAssemblerGenerics.put(params, BusinessUtils.defaultQualifier(application,
                            DEFAULT_ASSEMBLER_KEY,
                            dtoClass,
                            TypeLiteral.get(Types.newParameterizedType(Assembler.class, params))));
                } else if (dtoOf.value().length > 1) {
                    Type[] params = {Tuples.typeOfTuple(dtoOf.value()), dtoClass};
                    autoTupleAssemblerGenerics.put(params, BusinessUtils.defaultQualifier(application,
                            DEFAULT_ASSEMBLER_KEY,
                            dtoClass,
                            TypeLiteral.get(Types.newParameterizedType(Assembler.class, params))));
                }
            }
        }

        Collection<BindingStrategy> bs = new ArrayList<>();
        // Each pairs of aggregateClass/dtoClass or aggregateTuple/dtoClass is bind to all the
        // default assemblers
        for (Class<? extends Assembler> defaultAssemblersClass : defaultAssemblersClasses) {
            Class<?> aggregateType = TypeToken.of(defaultAssemblersClass)
                    .resolveType(defaultAssemblersClass.getTypeParameters()[0])
                    .getRawType();

            if (aggregateType.isAssignableFrom(Tuple.class) && !autoTupleAssemblerGenerics.isEmpty()) {
                bs.add(new GenericBindingStrategy<>(Assembler.class, defaultAssemblersClass,
                        autoTupleAssemblerGenerics));

            } else if (!aggregateType.isAssignableFrom(Tuple.class) && !autoAssemblerGenerics.isEmpty()) {
                bs.add(new GenericBindingStrategy<>(Assembler.class, defaultAssemblersClass, autoAssemblerGenerics));
            }
        }
        return bs;
    }
}
