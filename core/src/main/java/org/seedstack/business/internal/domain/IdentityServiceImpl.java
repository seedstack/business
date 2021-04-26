/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.domain;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.IdentityExistsException;
import org.seedstack.business.domain.IdentityGenerator;
import org.seedstack.business.domain.IdentityService;
import org.seedstack.business.domain.ValueObject;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.seed.Application;
import org.seedstack.seed.ClassConfiguration;
import org.seedstack.shed.cache.Cache;
import org.seedstack.shed.cache.CacheParameters;
import org.seedstack.shed.exception.BaseException;
import org.seedstack.shed.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.seedstack.shed.reflect.ReflectUtils.getValue;
import static org.seedstack.shed.reflect.ReflectUtils.setValue;

class IdentityServiceImpl implements IdentityService {
    private static final String GENERATOR_CLASS = "generatorClass";
    private static final String EXPECTED_CLASS = "expectedIdClass";
    private static final String ACTUAL_CLASS = "actualIdClass";
    private static final String CONTAINER_CLASS = "containerClass";
    private static final String IDENTITY_GENERATOR_KEY = "defaultGenerator";
    private static final String FIELD = "field";

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
        //Searching for Identity in current entity
        IdentityInfo identityInfo = getIdentityInfo(entity);
        if(identityInfo.hasIdentity()){
            //Check that the value is not already set
            Object id = getIdentityFieldValue(identityInfo, entity);
            if(id ==null){
                setIdentityFieldValue(identityInfo, entity);
            }else{
                String message="Entity " + String.valueOf(entity) + " already has an identity";
                if(identityInfo.inEmbeddedId){
                    message="Composite identifier "+ String.valueOf(entity.getId()) +" already has an identity";
                }
                throw new IdentityExistsException(message);
            }
        }
        return entity;
    }

    private <E extends Entity<I>, I> Object getIdentityFieldValue(IdentityInfo identityInfo, E entity){
        Object objectToGetValueFrom =entity;
        if(identityInfo.inEmbeddedId){
            checkNotNull(entity.getId(),"Entity's composite Identifier must not be null");
            objectToGetValueFrom=entity.getId();
        }
        identityInfo.identityField.setAccessible(true);
        return getValue(identityInfo.identityField, objectToGetValueFrom);
    }

    @SuppressWarnings("unchecked")
    private <E extends Entity<I>, I> void setIdentityFieldValue(IdentityInfo identityInfo, E entity){
        IdentityGenerator<I> identityGenerator = (IdentityGenerator<I>) injector.getInstance(identityInfo.generatorKey);
        //Setting the value with generator
        Object generatedValue = identityGenerator.generate(entity.getClass());
        Object objectToSetValue =entity;
        if(identityInfo.inEmbeddedId){
            checkNotNull(entity.getId(),"Entity's composite Identifier must not be null");
            objectToSetValue=entity.getId();
        }
        try{
            setValue(identityInfo.identityField, objectToSetValue, generatedValue);
        }catch(IllegalArgumentException e){
            throw BusinessException.wrap(e, BusinessErrorCode.INCOMPATIBLE_IDENTITY_TYPES)
                    .put(GENERATOR_CLASS, identityGenerator.getClass())
                    .put(EXPECTED_CLASS, identityInfo.identityField.getType())
                    .put(ACTUAL_CLASS, generatedValue.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Entity<I>, I> IdentityInfo<?, ?> getIdentityInfo(E entity) {
        //First try in entity itSelf
        IdentityInfo<?,?> result = cache.get(
                new ClassInfo<E>(
                        (Class<E>) entity.getClass(),
                        () -> application.getConfiguration((Class<E>) entity.getClass())
                )
        );
        Class<I> embeddedIdClass=resolveEntityIdClass(entity.getClass());
        if(!result.hasIdentity() && ValueObject.class.isAssignableFrom(embeddedIdClass)){
            //There is no Identity set on the entity class, try to get it from embedded identifier
            result = cache.get(
                    new ClassInfo<I>(embeddedIdClass,
                            ()->application.getConfiguration((Class<I>)embeddedIdClass))
            );
            result.setInEmbeddedId(true);
        }
        return result;
    }

    /**
     * Returns the identifier class for an entity root class.
     */
    @SuppressWarnings("unchecked")
    public static <A extends Entity<I>, I> Class<I> resolveEntityIdClass(Class<A> entityClass) {
        checkNotNull(entityClass, "entity should not be null");
        return (Class<I>) BusinessUtils.resolveGenerics(Entity.class, entityClass)[0];
    }

    @SuppressWarnings("unchecked")
    private static <T extends IdentityGenerator<?>> IdentityInfo<?,?> resolveIdentityInfo(ClassInfo<?> classInfo){
        return getIdentityField(classInfo.containerClass).map(identityField ->{
            Identity identityAnnotation = getIdentityAnnotation(classInfo.containerClass, identityField);
            Class<T> identityGeneratorClass = getIdentityGeneratorClass(identityAnnotation);

            if (IdentityGenerator.class.equals(identityGeneratorClass)) {
                throw BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_GENERATOR_SPECIFIED)
                        .put(CONTAINER_CLASS, classInfo.containerClass);
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
                                classInfo.containerClass,
                                TypeLiteral.get(identityGeneratorClass))
                                .orElse(null));
            }
            if(key==null){
                //Identity generator is not qualified
                throw BusinessException.createNew(BusinessErrorCode.UNQUALIFIED_IDENTITY_GENERATOR)
                        .put(FIELD, identityField.getName())
                        .put(CONTAINER_CLASS, classInfo.containerClass);
            }
            return new IdentityInfo(classInfo.containerClass, identityField, key);
            })
            .orElse(new IdentityInfo(classInfo.containerClass));
    }

    private static Optional<Field> getIdentityField(Class<?> containerClass) {
        return IdentityResolver.INSTANCE.resolveField(containerClass)
                .map(ReflectUtils::makeAccessible);
    }

    /**Provides the Identity annotation standing on a specific field of a class*/
    private static Identity getIdentityAnnotation(Class<?> containerClass, Field identityField) {
        return Optional.ofNullable(identityField.getAnnotation(Identity.class)).<BaseException>orElseThrow(
                () -> BusinessException.createNew(BusinessErrorCode.NO_IDENTITY_FIELD_DECLARED_FOR_CONTAINER)
                        .put(CONTAINER_CLASS, containerClass));
    }

    @SuppressWarnings("unchecked")
    private static <T extends IdentityGenerator<?>> Class<T> getIdentityGeneratorClass(Identity identity) {
        return (Class<T>) identity.generator();
    }


    private static class IdentityInfo<E extends Entity<I>, I> {
        final Class<E> entityClass;
        final Field identityField;
        final Key<? extends IdentityGenerator<I>> generatorKey;
        private boolean inEmbeddedId;

        private IdentityInfo(Class<E> entityClass) {
            this(entityClass, null, null);
        }

        private IdentityInfo(Class<E> entityClass, Field identityField,
                             Key<? extends IdentityGenerator<I>> generatorKey) {
            this.entityClass = entityClass;
            this.generatorKey = generatorKey;
            this.identityField = identityField;
            this.inEmbeddedId=false;
        }

        public void setInEmbeddedId(boolean inEmbeddedId) {
            this.inEmbeddedId = inEmbeddedId;
        }

        public boolean isInEmbeddedId() {
            return inEmbeddedId;
        }

        boolean hasIdentity() {
            return entityClass != null && identityField != null && generatorKey != null;
        }
    }

    private static class ClassInfo<E> {
        final Class<E> containerClass;
        final Supplier<ClassConfiguration<E>> classConfigurationSupplier;

        private ClassInfo(Class<E> containerClass, Supplier<ClassConfiguration<E>> classConfigurationSupplier) {
            this.containerClass = containerClass;
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
            return containerClass.equals(classInfo.containerClass);
        }

        @Override
        public int hashCode() {
            return containerClass.hashCode();
        }
    }
}
