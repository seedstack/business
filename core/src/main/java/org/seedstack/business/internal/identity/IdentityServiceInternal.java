/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.internal.identity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import net.jodah.typetools.TypeResolver;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.identity.IdentityErrorCodes;
import org.seedstack.business.domain.identity.IdentityHandler;
import org.seedstack.business.domain.identity.IdentityService;
import org.seedstack.seed.Application;
import org.seedstack.seed.SeedException;

import java.lang.reflect.Field;

/**
 * IdentityServiceInternal identify the handler and the configuration used to
 * generate a unique appropriate ID for the current entity
 *
 * @author redouane.loulou@ext.mpsa.com
 */
class IdentityServiceInternal implements IdentityService {

    private static final String ENTITY_CLASS = "entityClass";
    private static final String HANDLER_CLASS = "handlerClass";

    @Inject
    private Injector injector;

    @Inject
    private Application application;

    private static final String IDENTITY_HANDLER_QUALIFIER_PROPS = "identity.handler-qualifier";

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <E extends Entity<ID>, ID> E identify(E entity) {
        Field entityIdField = getEntityIdField(entity);
        Identity identity = entityIdField.getAnnotation(Identity.class);
        Configuration entityConfiguration = application.getConfiguration(entity.getClass());
        IdentityHandler identityHandler = getIdentityHandler(identity, entityConfiguration, entity.getClass());
        compareIDType(identityHandler, entity);
        entityIdField.setAccessible(true);
        try {
            Object id = entityIdField.get(entity);
            if (id == null) {
                entityIdField.set(entity, identityHandler.handle(entity, entityConfiguration));
            } else {
                throw SeedException.createNew(IdentityErrorCodes.ID_MUST_BE_NULL).put(ENTITY_CLASS,
                        entity.getClass().getName());
            }
        } catch (IllegalArgumentException e) {
            throw SeedException.wrap(e, IdentityErrorCodes.ID_INJECTION_ERROR)
                    .put(ENTITY_CLASS, entity.getClass().getName());
        } catch (IllegalAccessException e) {
            throw SeedException.wrap(e, IdentityErrorCodes.ID_INJECTION_ERROR)
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
            throw SeedException.createNew(IdentityErrorCodes.BAD_IDENTITY_HANDLER_DEFINE_FOR_ENTITY_ID)
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
    private IdentityHandler getIdentityHandler(Identity identity,
                                               Configuration entityConfiguration, Class<?> entityClass) {
        IdentityHandler identityHandler = null;
        if (!identity.handler().isInterface()) {
            identityHandler = injector.getInstance(identity.handler());
        } else {
            String identityQualifier = entityConfiguration.getString(IDENTITY_HANDLER_QUALIFIER_PROPS);

            if (StringUtils.isNotBlank(identityQualifier)) {
                identityHandler = injector.getInstance(Key.get(identity.handler(), Names.named(identityQualifier)));
            } else {
                SeedException.createNew(IdentityErrorCodes.QUALIFIER_FOR_IDENTITY_HANDLER_NOT_FOUND_FOR_ENTITY)
                        .put(HANDLER_CLASS, identity.handler())
                        .put(ENTITY_CLASS, entityClass.getName()).thenThrows();
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
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Identity.class)) {
                return field;
            }
        }
        throw SeedException.createNew(IdentityErrorCodes.NO_IDENTITY_HANDLER_DEFINE_FOR_ENTITY_ID)
                .put(ENTITY_CLASS, entity.getClass().getName());
    }

}
