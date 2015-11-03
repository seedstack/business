/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.kametic.specifications.Specification;
import org.seedstack.business.api.DomainSpecifications;
import org.seedstack.business.api.Producible;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.DomainErrorCodes;
import org.seedstack.business.api.domain.DomainObject;
import org.seedstack.business.api.domain.DomainRegistry;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.seed.ErrorCode;
import org.seedstack.seed.SeedException;
import org.seedstack.seed.TypeOf;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.util.Types;

/**
 * Registry to access to all domain objects (repository, factory, service, policy).
 * 
 * @author thierry.bouvet@mpsa.com
 *
 */
public class DomainRegistryImpl implements DomainRegistry {

	@Inject
	private Injector injector;

	@Override
	public <T extends Repository<A, K>, A extends AggregateRoot<K>, K> T getRepository(TypeOf<T> typeOf,
			Class<? extends Annotation> qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.REPOSITORY, DomainErrorCodes.ILLEGAL_REPOSITORY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T extends Repository<A, K>, A extends AggregateRoot<K>, K> T getRepository(TypeOf<T> typeOf,
			String qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.REPOSITORY, DomainErrorCodes.ILLEGAL_REPOSITORY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRoot, Class<K> key,
			Class<? extends Annotation> qualifier) {
		return getInstance(getKey(getType(Repository.class,aggregateRoot, key),qualifier));
	}

	@Override
	public <A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRoot, Class<K> key,
			String qualifier) {
		return getInstance(getKey(getType(Repository.class,aggregateRoot, key),qualifier));
	}

	@Override
	public <T extends Factory<A>,A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf) {
		checkType(typeOf.getRawType(), DomainSpecifications.FACTORY, DomainErrorCodes.ILLEGAL_FACTORY);
		return getInstance(getKey(typeOf.getType()));
	}

	@Override
	public <T extends Factory<A>,A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf,
			Class<? extends Annotation> qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.FACTORY, DomainErrorCodes.ILLEGAL_FACTORY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T extends Factory<A>, A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf, String qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.FACTORY, DomainErrorCodes.ILLEGAL_FACTORY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot) {
		return getInstance(getKey(getType(Factory.class, aggregateRoot)));
	}

	@Override
	public <T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot,
			Class<? extends Annotation> qualifier) {
		return getInstance(getKey(getType(Factory.class, aggregateRoot), qualifier));
	}

	@Override
	public <T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot, String qualifier) {
		return getInstance(getKey(getType(Factory.class, aggregateRoot), qualifier));
	}

	@Override
	public <T> T getService(TypeOf<T> typeOf) {
		checkType(typeOf.getRawType(), DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(typeOf.getType()));
	}

	@Override
	public <T> T getService(TypeOf<T> typeOf, Class<? extends Annotation> qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T> T getService(TypeOf<T> typeOf, String qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T> T getService(Class<T> rawType) {
		checkType(rawType, DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(getType(rawType)));
	}

	@Override
	public <T> T getService(Class<T> rawType, Class<? extends Annotation> qualifier) {
		checkType(rawType, DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(rawType,qualifier));
	}


	@Override
	public <T> T getService(Class<T> rawType, String qualifier) {
		checkType(rawType, DomainSpecifications.SERVICE, DomainErrorCodes.ILLEGAL_SERVICE);
		return getInstance(getKey(getType(rawType),qualifier));
	}

	@Override
	public <T> T getPolicy(Class<T> rawType) {
		checkType(rawType, DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(getType(rawType)));
	}

	@Override
	public <T> T getPolicy(Class<T> rawType, Class<? extends Annotation> qualifier) {
		checkType(rawType, DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(rawType,qualifier));
	}

	@Override
	public <T> T getPolicy(Class<T> rawType, String qualifier) {
		checkType(rawType, DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(getType(rawType),qualifier));
	}

	@Override
	public <T> T getPolicy(TypeOf<T> typeOf) {
		checkType(typeOf.getRawType(), DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(typeOf.getType()));
	}

	@Override
	public <T> T getPolicy(TypeOf<T> typeOf, Class<? extends Annotation> qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	@Override
	public <T> T getPolicy(TypeOf<T> typeOf, String qualifier) {
		checkType(typeOf.getRawType(), DomainSpecifications.POLICY, DomainErrorCodes.ILLEGAL_POLICY);
		return getInstance(getKey(typeOf.getType(), qualifier));
	}

	/**
	 * Get an instance for a defined {@link Key}.
	 * @param key key to find in {@link Injector}
	 * @return an instance bound to the {@link Key}.
	 */
	@SuppressWarnings("unchecked")
	private <T> T getInstance(Key<?> key) {
		return (T) injector.getInstance(key);
	}

	/**
	 * Get a {@link Key} for a defined class and a qualifier.
	 * @param rawType class 
	 * @param qualifier Optional Key {@link Qualifier}.
	 * @return the {@link Key}.
	 */
	private <T> Key<?> getKey(Type type, Class<? extends Annotation> qualifier) {
		return Key.get(type,qualifier);
	}

	/**
	 * Get a {@link Key} for a defined class and a qualifier.
	 * @param rawType class 
	 * @return the {@link Key}.
	 */
	private <T> Key<?> getKey(Type type, String qualifier) {
		return Key.get(type, Names.named(qualifier));
	}

	/**
	 * Get a {@link Key} for a defined class and a qualifier.
	 * @param rawType class 
	 * @param qualifier Optional Key {@link Qualifier}.
	 * @return the {@link Key}.
	 */
	private <T> Key<?> getKey(Type type) {
		return Key.get(type);
	}

	/**
	 * Get a {@link Type} for a defined class.
	 * @param rawType class 
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
	 * @param rawType raw type to check
	 * @param spec {@link Specification} to check
	 * @param errorCode {@link ErrorCode} to throw if the {@link Specification} is not satisfied.
	 */
	private <T> void checkType(Class<T> rawType, Specification<Class<?>> spec, DomainErrorCodes errorCode) {
		if (! spec.isSatisfiedBy(rawType)) {
			throw SeedException.createNew(errorCode).put("class", rawType);
		}
	}
}
