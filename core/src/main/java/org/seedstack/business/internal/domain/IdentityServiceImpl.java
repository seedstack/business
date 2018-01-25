/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.seedstack.shed.reflect.ReflectUtils.getValue;
import static org.seedstack.shed.reflect.ReflectUtils.setValue;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
import javax.inject.Inject;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.IdentityExistsException;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.shed.cache.Cache;
import org.seedstack.shed.cache.CacheParameters;
import org.seedstack.shed.exception.BaseException;
import org.seedstack.shed.reflect.ReflectUtils;

class IdentityServiceImpl implements IdentityService {
    private static final String ENTITY_CLASS = "entityClass";
    private static final String GENERATOR_CLASS = "generatorClass";
    private static final String IDENTITY_GENERATOR_KEY = "defaultGenerator";
    private static final String FIELD = "field";
    private static final String EXPECTED_CLASS = "expectedIdClass";
    private static final String ACTUAL_CLASS = "actualIdClass";
    private static final Cache<ClassInfo<?>, IdentityInfo<?, ?>> cache = Cache.create(
            new CacheParameters<ClassInfo<?>, IdentityInfo<?, ?>>()
                    .setInitialSize(256)
                    .setMaxSize(1024)
                    .setLoadingFunction(IdentityServiceImpl::resolveIdentityInfo)
    );

    @Inject
    private Injector injector;
    @Inject
    private Application application;

    @Override
    public <E extends Entity<I>, I> E identify(E entity) throws IdentityExistsException {
        IdentityInfo<E, I> identityInfo = getIdentityInfo(checkNotNull(entity, "Entity must not be null"));

        if (identityInfo.hasIdentity()) {
            IdentityGenerator<I> identityGenerator = injector.getInstance(identityInfo.generatorKey);
            Object id = getValue(identityInfo.identityField, entity);
            if (id == null) {
                I generatedValue = identityGenerator.generate(identityInfo.entityClass);
                try {
                    setValue(identityInfo.identityField,
                            entity,
                            generatedValue
                    );
                } catch (IllegalArgumentException e) {
                    throw BusinessException.wrap(e, BusinessErrorCode.INCOMPATIBLE_IDENTITY_TYPES)
                            .put(ENTITY_CLASS, identityInfo.entityClass)
                            .put(GENERATOR_CLASS, identityGenerator.getClass())
                            .put(EXPECTED_CLASS, identityInfo.identityField.getType())
                            .put(ACTUAL_CLASS, generatedValue.getClass());
                }
            } else {
                throw new IdentityExistsException("Entity " + String.valueOf(entity) + " already has an identity");
            }
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    private <E extends Entity<I>, I> IdentityInfo<E, I> getIdentityInfo(E entity) {
        return (IdentityInfo<E, I>) cache.get(
                new ClassInfo<>(
                        (Class<E>) entity.getClass(),
                        () -> application.getConfiguration((Class<E>) entity.getClass())
                )
        );
    }

    @SuppressWarnings("unchecked")
    private static <T extends IdentityGenerator<I>, E extends Entity<I>, I> IdentityInfo<E, I> resolveIdentityInfo(
            ClassInfo<?> classInfo) {
        return getIdentityField(classInfo.entityClass).map(identityField -> {
            Identity identityAnnotation = getIdentityAnnotation(classInfo.entityClass, identityField);
            Class<T> identityGeneratorClass = getIdentityGeneratorClass(identityAnnotation);

            if (IdentityGenerator.class.equals(identityGeneratorClass)) {
                throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_GENERATOR_SPECIFIED)
                        .put(ENTITY_CLASS, classInfo.entityClass);
            }

            Key<T> key;
            if (!identityAnnotation.generator().isInterface()) {
                key = Key.get(identityGeneratorClass);
            } else {
                key = BusinessUtils.getQualifier(identityField)
                        .map(qualifier -> Key.get(identityGeneratorClass, qualifier))
                        .orElseGet(() -> BusinessUtils.resolveDefaultQualifier(
                                new HashMap<>(),
                                classInfo.classConfigurationSupplier.get(),
                                IDENTITY_GENERATOR_KEY,
                                classInfo.entityClass,
                                TypeLiteral.get(identityGeneratorClass))
                                .orElse(null));
            }
            if (key == null) {
                throw BusinessException.createNew(BusinessErrorCode.UNQUALIFIED_IDENTITY_GENERATOR)
                        .put(FIELD, identityField.getName())
                        .put(ENTITY_CLASS, classInfo.entityClass);
            }
            return new IdentityInfo<>((Class<E>) classInfo.entityClass, identityField, key);
        }).orElse(new IdentityInfo<>((Class<E>) classInfo.entityClass));
    }

    @SuppressWarnings("unchecked")
    private static <T extends IdentityGenerator<?>> Class<T> getIdentityGeneratorClass(Identity identity) {
        return (Class<T>) identity.generator();
    }

    private static Optional<Field> getIdentityField(Class<? extends Entity> entityClass) {
        return IdentityResolver.INSTANCE.resolveField(entityClass)
                .map(ReflectUtils::makeAccessible);
    }

    private static Identity getIdentityAnnotation(Class<? extends Entity> entityClass, Field identityField) {
        return Optional.ofNullable(identityField.getAnnotation(Identity.class)).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                        .put(ENTITY_CLASS, entityClass));
    }

    private static class ClassInfo<E extends Entity<?>> {
        final Class<E> entityClass;
        final Supplier<ClassConfiguration<E>> classConfigurationSupplier;

        private ClassInfo(Class<E> entityClass,
                Supplier<ClassConfiguration<E>> classConfigurationSupplier) {
            this.entityClass = entityClass;
            this.classConfigurationSupplier = classConfigurationSupplier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ClassInfo<?> classInfo = (ClassInfo<?>) o;
            return entityClass.equals(classInfo.entityClass);
        }

        @Override
        public int hashCode() {
            return entityClass.hashCode();
        }
    }

    private static class IdentityInfo<E extends Entity<I>, I> {
        final Class<E> entityClass;
        final Field identityField;
        final Key<? extends IdentityGenerator<I>> generatorKey;

        private IdentityInfo(Class<E> entityClass) {
            this(entityClass, null, null);
        }

        private IdentityInfo(Class<E> entityClass, Field identityField,
                Key<? extends IdentityGenerator<I>> generatorKey) {
            this.entityClass = entityClass;
            this.generatorKey = generatorKey;
            this.identityField = identityField;
        }

        boolean hasIdentity() {
            return entityClass != null && identityField != null && generatorKey != null;
        }
    }
}
