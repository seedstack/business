/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.migrate;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.util.Types;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.seedstack.business.assembler.Assembler;
import org.seedstack.business.assembler.LegacyAssembler;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.GenericFactory;
import org.seedstack.business.domain.LegacyBaseAggregateRoot;
import org.seedstack.business.domain.LegacyRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.assembler.AssemblerPlugin;
import org.seedstack.business.internal.domain.DomainPlugin;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;

public class MigratePlugin extends AbstractSeedPlugin {
    private final Map<Key<?>, Key<?>> repositoryBindings = new HashMap<>();
    private final Map<Key<?>, Key<?>> factoryBindings = new HashMap<>();
    private final Map<Key<?>, Key<?>> assemblerBindings = new HashMap<>();

    @Override
    public String name() {
        return "business-migrate";
    }

    @Override
    protected Collection<Class<?>> dependencies() {
        return Lists.newArrayList(DomainPlugin.class, AssemblerPlugin.class);
    }

    @Override
    protected InitState initialize(InitContext initContext) {
        if (round.isFirst()) {
            for (Element element : Elements.getElements((AbstractModule) initContext.dependency(AssemblerPlugin.class)
                    .nativeUnitModule())) {
                if (element instanceof Binding) {
                    Key key = ((Binding) element).getKey();
                    if (key.getTypeLiteral().getType() instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType) key.getTypeLiteral().getType();
                        if (Assembler.class.equals(type.getRawType())) {
                            // Assembler
                            registerBinding(assemblerBindings, Types.newParameterizedType(LegacyAssembler.class,
                                    type.getActualTypeArguments()), type, key.getAnnotation());
                        }
                    }
                }
            }
            return InitState.NON_INITIALIZED;
        } else {
            for (Element element : Elements.getElements((AbstractModule) initContext.dependency(DomainPlugin.class)
                    .nativeUnitModule())) {
                if (element instanceof Binding) {
                    Key key = ((Binding) element).getKey();
                    if (key.getTypeLiteral().getType() instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType) key.getTypeLiteral().getType();
                        if (Repository.class.equals(type.getRawType())) {
                            // Repository
                            if (!LegacyBaseAggregateRoot.class.equals(type.getActualTypeArguments()[0])) {
                                registerBinding(repositoryBindings,
                                        Types.newParameterizedType(LegacyRepository.class,
                                                type.getActualTypeArguments()), type, key.getAnnotation());
                            }
                        } else if (Factory.class.equals(type.getRawType())) {
                            // Factory
                            if (!LegacyBaseAggregateRoot.class.equals(type.getActualTypeArguments()[0])) {
                                registerBinding(factoryBindings,
                                        Types.newParameterizedType(GenericFactory.class,
                                                type.getActualTypeArguments()), type, key.getAnnotation());
                            }
                        }
                    }
                }
            }
            return InitState.INITIALIZED;
        }
    }

    private void registerBinding(Map<Key<?>, Key<?>> bindings, ParameterizedType from, ParameterizedType to,
            Annotation annotation) {
        if (annotation != null) {
            bindings.put(Key.get(from, annotation), Key.get(to, annotation));
        } else {
            bindings.put(Key.get(from), Key.get(to));
        }
    }

    @Override
    public Object nativeUnitModule() {
        return new MigrateModule(repositoryBindings, factoryBindings, assemblerBindings);
    }
}
