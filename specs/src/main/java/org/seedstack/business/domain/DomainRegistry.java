/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import java.lang.annotation.Annotation;

import org.seedstack.business.Producible;
import org.seedstack.business.Service;
import org.seedstack.shed.reflect.TypeOf;

/**
 * Registry to access to all domain objects.
 * 
 * <pre>
 * To use it, just inject it :
 * 
 * {@code @Inject}
 * DomainRegistry domainRegistry
 * </pre>
 * 
 * <pre>
 * Example for a class without generic parameter:
 * <code>
 * 	   MyPolicy policy = domainRegistry.getPolicy(MyPolicy.class,"qualifier");
 * </code>
 * 
 * Example for a class with generic parameter:
 * <code>
 * 	   AnotherPolicy&lt;MyClient&lt;Long&gt;&gt; policy = domainRegistry.getPolicy(new TypeOf&lt;AnotherPolicy&lt;MyClient&lt;Long&gt;&gt;&gt;(){},"qualifier");
 * </code>
 * </pre>
 */
public interface DomainRegistry {

	/**
	 * Get a {@link Repository} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier repository qualifier
	 * @return a {@link Repository} found in the domain.
	 */
	<T extends Repository<A,K>,A extends AggregateRoot<K>,K> T getRepository(TypeOf<T> typeOf, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link Repository} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier repository qualifier
	 * @return a {@link Repository} found in the domain.
	 */
	<T extends Repository<A,K>,A extends AggregateRoot<K>,K> T getRepository(TypeOf<T> typeOf, String qualifier);

	/**
	 * Get the {@link Repository} for an aggregate root and a qualifier.
	 * @param aggregateRoot the aggregate root linked to the repository.
	 * @param key the aggregate root's key.
	 * @param qualifier repository qualifier (example: JPA, ...).
	 * @return an instance of the {@link Repository}
	 */
	<A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRoot, Class<K> key, Class<? extends Annotation> qualifier);
	
	/**
	 * Get the {@link Repository} for an aggregate root and a qualifier.
	 * @param aggregateRoot the aggregate root linked to the repository.
	 * @param key the aggregate root's key.
	 * @param qualifier repository qualifier (example: JPA, ...).
	 * @return an instance of the {@link Repository}
	 */
	<A extends AggregateRoot<K>, K> Repository<A, K> getRepository(Class<A> aggregateRoot, Class<K> key, String qualifier);

	/**
	 * Get the {@link Factory} for an aggregate root.
	 * @param aggregateRoot the aggregate root linked to the factoy.
	 * @return an instance of the {@link Factory}
	 */
	<T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot);
	
	/**
	 * Get the {@link Factory} with a qualifier for an aggregate root.
	 * @param aggregateRoot the aggregate root linked to the factoy.
	 * @param qualifier factory qualifier.
	 * @return an instance of the {@link Factory}
	 */
	<T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot, Class<? extends Annotation> qualifier);

	/**
	 * Get the {@link Factory} with a qualifier for an aggregate root.
	 * @param aggregateRoot the aggregate root linked to the factoy.
	 * @param qualifier factory qualifier.
	 * @return an instance of the {@link Factory}
	 */
	<T extends DomainObject & Producible> Factory<T> getFactory(Class<T> aggregateRoot, String qualifier);

	/**
	 * Get a {@link Factory} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @return a {@link Factory} found in the domain.
	 */
	<T extends Factory<A>,A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf);

	/**
	 * Get a {@link Factory} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier factory qualifier
	 * @return a {@link Factory} found in the domain.
	 */
	<T extends Factory<A>,A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link Factory} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier factory qualifier
	 * @return a {@link Factory} found in the domain.
	 */
	<T extends Factory<A>,A extends DomainObject & Producible> T getFactory(TypeOf<T> typeOf, String qualifier);

	/**
	 * Get a {@link Service} from the domain.
	 * @param rawType the service class.
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(Class<T> rawType);

	/**
	 * Get a {@link Service} with a qualifier from the domain.
	 * @param rawType the service class.
	 * @param qualifier service qualifier
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(Class<T> rawType, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link Service} with a qualifier from the domain.
	 * @param rawType the service class.
	 * @param qualifier service qualifier
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(Class<T> rawType, String qualifier);

	/**
	 * Get a {@link Service} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(TypeOf<T> typeOf);
	
	/**
	 * Get a {@link Service} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier service qualifier
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(TypeOf<T> typeOf, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link Service} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier service qualifier
	 * @return a {@link Service} found in the domain.
	 */
	<T> T getService(TypeOf<T> typeOf, String qualifier);

	/**
	 * Get a {@link DomainPolicy} from the domain.
	 * @param rawType the policy class.
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(Class<T> rawType);

	/**
	 * Get a {@link DomainPolicy} with a qualifier from the domain.
	 * @param rawType the policy class.
	 * @param qualifier policy qualifier
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(Class<T> rawType, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link DomainPolicy} with a qualifier from the domain.
	 * @param rawType the policy class.
	 * @param qualifier policy qualifier
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(Class<T> rawType, String qualifier);

	/**
	 * Get a {@link DomainPolicy} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(TypeOf<T> typeOf);
	
	/**
	 * Get a {@link DomainPolicy} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier policy qualifier
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(TypeOf<T> typeOf, Class<? extends Annotation> qualifier);

	/**
	 * Get a {@link DomainPolicy} from the domain.
	 * @param typeOf the {@link TypeOf} to define all generic pattern.
	 * @param qualifier policy qualifier
	 * @return a {@link DomainPolicy} found in the domain.
	 */
	<T> T getPolicy(TypeOf<T> typeOf, String qualifier);
}
