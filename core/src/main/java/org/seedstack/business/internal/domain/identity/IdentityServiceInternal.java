/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain.identity;

import com.google.common.base.Strings;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import net.jodah.typetools.TypeResolver;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.domain.identity.IdentityService;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.seed.SeedException;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * IdentityServiceInternal identify the handler and the configuration used to
 * generate a unique appropriate ID for the current entity
 */
class IdentityServiceInternal implements IdentityService {
    private static final String ENTITY_CLASS = "entityClass";
    private static final String HANDLER_CLASS = "handlerClass";
    private static final String IDENTITY_HANDLER_KEY = "identityHandler";

    @Inject
    private Injector injector;
    @Inject
    private Application application;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <E extends Entity<ID>, ID> E identify(E entity) {
        Field entityIdField = getEntityIdField(entity);
        Identity identity = entityIdField.getAnnotation(Identity.class);
        ClassConfiguration entityConfiguration = application.getConfiguration(entity.getClass());
        IdentityHandler identityHandler = getIdentityHandler(identity, entityConfiguration, entity.getClass());
        compareIDType(identityHandler, entity);
        entityIdField.setAccessible(true);
        try {
            Object id = entityIdField.get(entity);
            if (id == null) {
                entityIdField.set(entity, identityHandler.handle(entity, entityConfiguration));
            } else {
                throw SeedException.createNew(BusinessErrorCode.ENTITY_ALREADY_HAS_AN_IDENTITY).put(ENTITY_CLASS,
                        entity.getClass().getName());
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw SeedException.wrap(e, BusinessErrorCode.UNABLE_TO_INJECT_ENTITY_IDENTITY)
                    .put(ENTITY_CLASS, entity.getClass().getName());
        }

        return entity;
    }

    /**
     * compareIDType
     *
     * @param identityHandler IdentityHandler
     * @param entity          Entity
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void compareIDType(IdentityHandler identityHandler, Entity<?> entity) {
        Class<?> entityIdClass = getEntityIdType(entity);
        Class<?> identityHandlerIdClass = getHandlerIdType(identityHandler);
        if (!entityIdClass.isAssignableFrom(identityHandlerIdClass)) {
            throw SeedException.createNew(BusinessErrorCode.IDENTITY_TYPE_CANNOT_BE_GENERATED_BY_HANDLER)
                    .put(ENTITY_CLASS, entity.getClass().getName())
                    .put(HANDLER_CLASS, identityHandler.getClass().getName())
                    .put("entityIdClass", entityIdClass.getName())
                    .put("handlerIdClass", identityHandlerIdClass.getName());
        }
    }

    /**
     * get IdentityHandler
     *
     * @param identity            Identity
     * @param entityConfiguration Configuration
     * @return IdentityHandler
     */
    @SuppressWarnings({"rawtypes"})
    private IdentityHandler getIdentityHandler(Identity identity, ClassConfiguration<?> entityConfiguration, Class<?> entityClass) {
        IdentityHandler identityHandler;
        if (!identity.value().isInterface()) {
            identityHandler = injector.getInstance(identity.value());
        } else {
            String identityQualifier = entityConfiguration.get(IDENTITY_HANDLER_KEY);

            if (!Strings.isNullOrEmpty(identityQualifier)) {
                identityHandler = injector.getInstance(Key.get(identity.value(), Names.named(identityQualifier)));
            } else {
                throw SeedException.createNew(BusinessErrorCode.NO_IDENTITY_HANDLER_QUALIFIER_FOUND_ON_ENTITY)
                        .put(HANDLER_CLASS, identity.value())
                        .put(ENTITY_CLASS, entityClass.getName());
            }
        }
        return identityHandler;
    }

    private <E extends Entity<ID>, ID> Class<?> getHandlerIdType(IdentityHandler<E, ID> identityHandler) {
        return TypeResolver.resolveRawArguments(IdentityHandler.class, identityHandler.getClass())[1];
    }

    private <E extends Entity<ID>, ID> Class<?> getEntityIdType(E entity) {
        return TypeResolver.resolveRawArguments(Entity.class, entity.getClass())[0];
    }

    private Field getEntityIdField(Entity<?> entity) {
        Optional<Field> field = IdentityAnnotationResolver.INSTANCE.resolveField(entity.getClass());
        if (field.isPresent()) {
            return field.get();
        } else {
            throw SeedException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_ENTITY)
                    .put(ENTITY_CLASS, entity.getClass().getName());
        }
    }
}
