/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import static org.seedstack.shed.reflect.ReflectUtils.getValue;
import static org.seedstack.shed.reflect.ReflectUtils.makeAccessible;
import static org.seedstack.shed.reflect.ReflectUtils.setValue;

import com.google.common.base.Strings;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.lang.reflect.Field;
import javax.inject.Inject;
import net.jodah.typetools.TypeResolver;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.shed.exception.BaseException;

class IdentityServiceImpl implements IdentityService {

    private static final String ENTITY_CLASS = "entityClass";
    private static final String GENERATOR_CLASS = "generatorClass";
    private static final String IDENTITY_GENERATOR_KEY = "identityGenerator";
    private static final int IDENTITY_TYPE_INDEX = 0;
    @Inject
    private Injector injector;
    @Inject
    private Application application;

    @Override
    public <E extends Entity<I>, I> E identify(E entity) {
        Class<E> entityClass = getEntityClass(entity);
        Field entityIdField = getIdentityField(entityClass);
        Identity identity = getIdentityAnnotation(entityClass);
        ClassConfiguration<E> entityConfiguration = application.getConfiguration(entityClass);
        IdentityGenerator<I> identityGenerator = getIdentityGenerator(identity, entityClass, entityConfiguration);

        compareIdType(entityClass, identityGenerator);
        makeAccessible(entityIdField);

        Object id = getValue(entityIdField, entity);
        if (id == null) {
            setValue(entityIdField, entity, identityGenerator.generate(entityClass, entityConfiguration.asMap()));
        } else {
            throw BusinessException.createNew(BusinessErrorCode.ENTITY_ALREADY_HAS_AN_IDENTITY)
                    .put(ENTITY_CLASS, entityClass);
        }

        return entity;
    }

    private <E extends Entity<I>, I> void compareIdType(Class<E> entityClass, IdentityGenerator<I> identityGenerator) {
        Class<?> entityIdClass = getEntityIdClass(entityClass);
        Class<?> identityGeneratorIdClass = getGeneratorIdClass(identityGenerator);
        if (!entityIdClass.isAssignableFrom(identityGeneratorIdClass)) {
            throw BusinessException.createNew(BusinessErrorCode.IDENTITY_TYPE_CANNOT_BE_GENERATED)
                    .put(ENTITY_CLASS, entityClass)
                    .put(GENERATOR_CLASS, identityGenerator.getClass())
                    .put("entityIdClass", entityIdClass)
                    .put("generatorIdClass", identityGeneratorIdClass);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Entity<I>, I> Class<E> getEntityClass(E entity) {
        return (Class<E>) entity.getClass();
    }

    private <E extends Entity<I>, I> Class<?> getEntityIdClass(Class<E> entityClass) {
        return TypeResolver.resolveRawArguments(Entity.class, entityClass)[0];
    }

    private <I> Class<?> getGeneratorIdClass(IdentityGenerator<I> identityGenerator) {
        return (Class<?>) BusinessUtils.resolveGenerics(IdentityGenerator.class,
                identityGenerator.getClass())[IDENTITY_TYPE_INDEX];
    }

    private Field getIdentityField(Class<? extends Entity> entityClass) {
        return IdentityResolver.INSTANCE.resolveField(entityClass).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                        .put(ENTITY_CLASS, entityClass));
    }

    private Identity getIdentityAnnotation(Class<? extends Entity> entityClass) {
        return IdentityResolver.INSTANCE.apply(entityClass).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                        .put(ENTITY_CLASS, entityClass));
    }

    @SuppressWarnings("unchecked")
    private <E extends Entity<I>, I> IdentityGenerator<I> getIdentityGenerator(Identity identity, Class<E> entityClass,
            ClassConfiguration<E> entityConfiguration) {
        IdentityGenerator<I> identityGenerator;
        if (!identity.generator()
                .isInterface()) {
            identityGenerator = (IdentityGenerator<I>) injector.getInstance(identity.generator());
        } else if (IdentityGenerator.class.equals(identity.generator())) {
            throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_GENERATOR_SPECIFIED)
                    .put(ENTITY_CLASS, entityClass);
        } else {
            String identityQualifier = entityConfiguration.get(IDENTITY_GENERATOR_KEY);

            if (!Strings.isNullOrEmpty(identityQualifier)) {
                identityGenerator = (IdentityGenerator<I>) injector.getInstance(
                        Key.get(identity.generator(), Names.named(identityQualifier)));
            } else {
                throw BusinessException.createNew(BusinessErrorCode.UNQUALIFIED_IDENTITY_GENERATOR)
                        .put(GENERATOR_CLASS, identity.generator())
                        .put(ENTITY_CLASS, entityClass);
            }
        }
        return identityGenerator;
    }
}
