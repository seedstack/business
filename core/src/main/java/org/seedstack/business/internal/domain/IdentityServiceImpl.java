/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static org.seedstack.business.internal.utils.BusinessUtils.resolveGenerics;
import static org.seedstack.shed.reflect.ReflectUtils.getValue;
import static org.seedstack.shed.reflect.ReflectUtils.makeAccessible;
import static org.seedstack.shed.reflect.ReflectUtils.setValue;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.inject.Inject;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.shed.exception.BaseException;

class IdentityServiceImpl implements IdentityService {
    // This unbounded cache of identity info can only grow up to the number of entity classes in the system
    private static final ConcurrentMap<Class<? extends Entity>, IdentityInfo<? extends Entity, ?>> cache =
            new ConcurrentHashMap<>();
    private static final String ENTITY_CLASS = "entityClass";
    private static final String GENERATOR_CLASS = "generatorClass";
    private static final String IDENTITY_GENERATOR_KEY = "identityGenerator";
    private static final String FIELD = "field";
    private static final int ID_TYPE_INDEX = 0;
    @Inject
    private Injector injector;
    @Inject
    private Application application;

    @Override
    public <E extends Entity<I>, I> E identify(E entity) {
        IdentityInfo<E, I> identityInfo = getIdentityInfo(entity);

        IdentityGenerator<I> identityGenerator = injector.getInstance(identityInfo.generatorKey);
        Object id = getValue(identityInfo.identityField, entity);
        if (id == null) {
            setValue(identityInfo.identityField,
                    entity,
                    identityGenerator.generate(identityInfo.entityClass)
            );
        } else {
            throw BusinessException.createNew(BusinessErrorCode.ENTITY_ALREADY_HAS_AN_IDENTITY)
                    .put(ENTITY_CLASS, identityInfo.entityClass);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    private <T extends IdentityGenerator<I>, E extends Entity<I>, I> IdentityInfo<E, I> getIdentityInfo(E entity) {
        return (IdentityInfo<E, I>) cache.computeIfAbsent(entity.getClass(), entityClass -> {
            Field identityField = getIdentityField(entityClass);
            Class<?> identityClass = identityField.getType();
            Identity identityAnnotation = getIdentityAnnotation(entityClass, identityField);
            Class<T> identityGeneratorClass = getIdentityGeneratorClass(identityAnnotation);
            Class<?> generatorIdClass = resolveGenerics(IdentityGenerator.class, identityGeneratorClass)[ID_TYPE_INDEX];

            if (IdentityGenerator.class.equals(identityGeneratorClass)) {
                throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_GENERATOR_SPECIFIED)
                        .put(ENTITY_CLASS, entityClass);
            }
            if (!identityClass.isAssignableFrom(generatorIdClass)) {
                throw BusinessException.createNew(BusinessErrorCode.IDENTITY_TYPE_CANNOT_BE_GENERATED)
                        .put(ENTITY_CLASS, entityClass)
                        .put(GENERATOR_CLASS, identityGeneratorClass)
                        .put("entityIdClass", identityClass)
                        .put("generatorIdClass", generatorIdClass);
            }

            Key<T> key;
            if (!identityAnnotation.generator().isInterface()) {
                key = Key.get(identityGeneratorClass);
            } else {
                key = BusinessUtils.getQualifier(identityField)
                        .map(qualifier -> Key.get(identityGeneratorClass, qualifier))
                        .orElseGet(() -> BusinessUtils.resolveDefaultQualifier(
                                application.getConfiguration((Class<E>) entityClass),
                                IDENTITY_GENERATOR_KEY,
                                entityClass,
                                TypeLiteral.get(identityGeneratorClass))
                                .orElse(null));
            }
            if (key == null) {
                throw BusinessException.createNew(BusinessErrorCode.UNQUALIFIED_IDENTITY_GENERATOR)
                        .put(FIELD, identityField.getName())
                        .put(ENTITY_CLASS, entityClass);
            }

            return new IdentityInfo<>((Class<E>) entityClass, identityField, key);
        });
    }

    private Field getIdentityField(Class<? extends Entity> entityClass) {
        return makeAccessible(IdentityResolver.INSTANCE.resolveField(entityClass).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                        .put(ENTITY_CLASS, entityClass)));
    }

    private Identity getIdentityAnnotation(Class<? extends Entity> entityClass, Field identityField) {
        return Optional.ofNullable(identityField.getAnnotation(Identity.class)).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                        .put(ENTITY_CLASS, entityClass));
    }

    @SuppressWarnings("unchecked")
    private <T extends IdentityGenerator<I>, I> Class<T> getIdentityGeneratorClass(Identity identity) {
        return (Class<T>) identity.generator();
    }

    private static class IdentityInfo<E extends Entity<I>, I> {
        final Class<E> entityClass;
        final Field identityField;
        final Key<? extends IdentityGenerator<I>> generatorKey;

        private IdentityInfo(Class<E> entityClass, Field identityField,
                Key<? extends IdentityGenerator<I>> generatorKey) {
            this.entityClass = entityClass;
            this.generatorKey = generatorKey;
            this.identityField = identityField;
        }
    }
}
