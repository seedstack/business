/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import org.kametic.specifications.Specification;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Producible;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.shed.exception.ErrorCode;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Registry to access to all domain objects (repository, factory, service, policy).
 */
class DomainRegistryImpl implements DomainRegistry {
    @Inject
    private Injector injector;

    @Override
    public <T extends Repository<A, K>, A extends AggregateRoot<K>, K> T getRepository(Type repositoryType) {
        checkType(repositoryType, BusinessSpecifications.REPOSITORY, BusinessErrorCode.ILLEGAL_REPOSITORY);
        return getInstance(getKey(repositoryType));
    }

    @Override
    public <T extends Repository<A, K>, A extends AggregateRoot<K>, K> T getRepository(Type repositoryType, Class<? extends Annotation> qualifier) {
        checkType(repositoryType, BusinessSpecifications.REPOSITORY, BusinessErrorCode.ILLEGAL_REPOSITORY);
        return getInstance(getKey(repositoryType, qualifier));
    }

    @Override
    public <T extends Repository<A, K>, A extends AggregateRoot<K>, K> T getRepository(Type repositoryType, String qualifier) {
        checkType(repositoryType, BusinessSpecifications.REPOSITORY, BusinessErrorCode.ILLEGAL_REPOSITORY);
        return getInstance(getKey(repositoryType, qualifier));
    }

    @Override
    public <A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRootClass, Class<K> idClass) {
        return getInstance(getKey(getType(Repository.class, aggregateRootClass, idClass)));
    }

    @Override
    public <A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRootClass, Class<K> idClass, Class<? extends Annotation> qualifier) {
        return getInstance(getKey(getType(Repository.class, aggregateRootClass, idClass), qualifier));
    }

    @Override
    public <A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRootClass, Class<K> idClass, String qualifier) {
        return getInstance(getKey(getType(Repository.class, aggregateRootClass, idClass), qualifier));
    }

    @Override
    public <T extends Factory<A>, A extends Producible> T getFactory(Type factoryType) {
        checkType(factoryType, BusinessSpecifications.FACTORY, BusinessErrorCode.ILLEGAL_FACTORY);
        return getInstance(getKey(factoryType));
    }

    @Override
    public <T extends Factory<A>, A extends Producible> T getFactory(Type factoryType, Class<? extends Annotation> qualifier) {
        checkType(factoryType, BusinessSpecifications.FACTORY, BusinessErrorCode.ILLEGAL_FACTORY);
        return getInstance(getKey(factoryType, qualifier));
    }

    @Override
    public <T extends Factory<A>, A extends Producible> T getFactory(Type factoryType, String qualifier) {
        checkType(factoryType, BusinessSpecifications.FACTORY, BusinessErrorCode.ILLEGAL_FACTORY);
        return getInstance(getKey(factoryType, qualifier));
    }

    @Override
    public <T extends Producible> Factory<T> getFactory(Class<T> producibleClass) {
        return getInstance(getKey(getType(Factory.class, producibleClass)));
    }

    @Override
    public <T extends Producible> Factory<T> getFactory(Class<T> producibleClass,
                                                        Class<? extends Annotation> qualifier) {
        return getInstance(getKey(getType(Factory.class, producibleClass), qualifier));
    }

    @Override
    public <T extends Producible> Factory<T> getFactory(Class<T> producibleClass, String qualifier) {
        return getInstance(getKey(getType(Factory.class, producibleClass), qualifier));
    }

    @Override
    public <T> T getService(Type serviceType) {
        checkType(serviceType, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(serviceType));
    }

    @Override
    public <T> T getService(Type serviceType, Class<? extends Annotation> qualifier) {
        checkType(serviceType, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(serviceType, qualifier));
    }

    @Override
    public <T> T getService(Type serviceType, String qualifier) {
        checkType(serviceType, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(serviceType, qualifier));
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        checkType(serviceClass, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(getType(serviceClass)));
    }

    @Override
    public <T> T getService(Class<T> serviceClass, Class<? extends Annotation> qualifier) {
        checkType(serviceClass, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(serviceClass, qualifier));
    }


    @Override
    public <T> T getService(Class<T> serviceClass, String qualifier) {
        checkType(serviceClass, BusinessSpecifications.SERVICE, BusinessErrorCode.ILLEGAL_SERVICE);
        return getInstance(getKey(getType(serviceClass), qualifier));
    }

    @Override
    public <T> T getPolicy(Class<T> policyClass) {
        checkType(policyClass, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(getType(policyClass)));
    }

    @Override
    public <T> T getPolicy(Class<T> policyClass, Class<? extends Annotation> qualifier) {
        checkType(policyClass, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(policyClass, qualifier));
    }

    @Override
    public <T> T getPolicy(Class<T> policyClass, String qualifier) {
        checkType(policyClass, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(getType(policyClass), qualifier));
    }

    @Override
    public <T> T getPolicy(Type policyType) {
        checkType(policyType, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(policyType));
    }

    @Override
    public <T> T getPolicy(Type policyType, Class<? extends Annotation> qualifier) {
        checkType(policyType, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(policyType, qualifier));
    }

    @Override
    public <T> T getPolicy(Type policyType, String qualifier) {
        checkType(policyType, BusinessSpecifications.POLICY, BusinessErrorCode.ILLEGAL_POLICY);
        return getInstance(getKey(policyType, qualifier));
    }

    /**
     * Get an instance for a defined {@link Key}.
     *
     * @param key key to find in {@link Injector}
     * @return an instance bound to the {@link Key}.
     */
    @SuppressWarnings("unchecked")
    private <T> T getInstance(Key<?> key) {
        return (T) injector.getInstance(key);
    }

    /**
     * Get a {@link Key} for a defined class and a qualifier.
     *
     * @param type      class
     * @param qualifier Optional Key {@link Qualifier}.
     * @return the {@link Key}.
     */
    private Key<?> getKey(Type type, Class<? extends Annotation> qualifier) {
        return Key.get(type, qualifier);
    }

    /**
     * Get a {@link Key} for a defined class and a qualifier.
     *
     * @param type class
     * @return the {@link Key}.
     */
    private Key<?> getKey(Type type, String qualifier) {
        return Key.get(type, Names.named(qualifier));
    }

    /**
     * Get a {@link Key} for a defined class and a qualifier.
     *
     * @param type class
     * @return the {@link Key}.
     */
    private Key<?> getKey(Type type) {
        return Key.get(type);
    }

    /**
     * Get a {@link Type} for a defined class.
     *
     * @param rawType       class
     * @param typeArguments parameterized types
     * @return the {@link Type}
     */
    private Type getType(Type rawType, Type... typeArguments) {
        if (typeArguments.length == 0) {
            return rawType;
        }
        return Types.newParameterizedType(rawType, typeArguments);
    }

    /**
     * @param type      type to check
     * @param spec      {@link Specification} to check
     * @param errorCode {@link ErrorCode} to throw if the {@link Specification} is not satisfied.
     */
    private void checkType(Type type, Specification<Class<?>> spec, ErrorCode errorCode) {
        Class<?> rawClass = org.seedstack.shed.reflect.Types.rawClassOf(type);
        if (!spec.isSatisfiedBy(rawClass)) {
            throw BusinessException.createNew(errorCode).put("class", rawClass);
        }
    }
}
