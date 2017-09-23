/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.seedstack.business.Service;

/**
 * The domain registry provides programmatic access to domain objects.
 *
 * <p> Usage for a class without generic parameter: </p>
 * <pre>
 * MyPolicy policy = domainRegistry.getPolicy(MyPolicy.class,"qualifier");
 * </pre>
 *
 * <p> Usage for a class with generic parameter(s): </p>
 * <pre>
 * AnotherPolicy&lt;MyClient&lt;Long&gt;&gt; policy = domainRegistry.getPolicy(
 *     new TypeOf&lt;AnotherPolicy&lt;MyClient&lt;Long&gt;&gt;&gt;(){},
 *     "qualifier"
 * );
 * </pre>
 *
 * <p> Note that generics are specified with a {@link Type} instance. In the example above,
 * SeedStack {@code TypeOf} is used to capture generics and provide a {@link Type} instance but any
 * other implementation can be used. </p>
 */
public interface DomainRegistry {

  /**
   * Get the {@link Repository} for an aggregate root.
   *
   * @param <AggregateRootT>   the type of the aggregate root.
   * @param <IdT>              the type of the aggregate root identifier.
   * @param aggregateRootClass the aggregate root class.
   * @param idClass            the aggregate root identifier class.
   * @return an instance of the repository.
   */
  <AggregateRootT extends AggregateRoot<IdT>, IdT> Repository<AggregateRootT, IdT> getRepository(
    Class<AggregateRootT> aggregateRootClass,
    Class<IdT> idClass);

  /**
   * Get the {@link Repository} for an aggregate root and a qualifier.
   *
   * @param <AggregateRootT>   the type of the aggregate root.
   * @param <I>                the type of the aggregate root identifier.
   * @param aggregateRootClass the aggregate root class.
   * @param idClass            the aggregate root identifier class.
   * @param qualifier          the repository qualifier.
   * @return an instance of the repository.
   */
  <AggregateRootT extends AggregateRoot<I>, I> Repository<AggregateRootT, I> getRepository(
    Class<AggregateRootT> aggregateRootClass,
    Class<I> idClass, Class<? extends Annotation> qualifier);

  /**
   * Get the {@link Repository} for an aggregate root and a qualifier.
   *
   * @param <AggregateRootT>   the type of the aggregate root.
   * @param <I>                the type of the aggregate root identifier.
   * @param aggregateRootClass the aggregate root class.
   * @param idClass            the aggregate root identifier class.
   * @param qualifier          the repository qualifier.
   * @return an instance of the repository.
   */
  <AggregateRootT extends AggregateRoot<I>, I> Repository<AggregateRootT, I> getRepository(
    Class<AggregateRootT> aggregateRootClass,
    Class<I> idClass, String qualifier);

  /**
   * Get a {@link Repository} from the domain.
   *
   * @param <RepositoryT>    the type of the repository.
   * @param <AggregateRootT> the type of the aggregate root.
   * @param <IdT>            the type of the aggregate root identifier.
   * @param repositoryType   the full generic type.
   * @return an instance of the repository.
   */
  <RepositoryT extends Repository<AggregateRootT, IdT>,
    AggregateRootT extends AggregateRoot<IdT>,
    IdT> RepositoryT getRepository(
    Type repositoryType);

  /**
   * Get a {@link Repository} from the domain.
   *
   * @param <RepositoryT>    the type of the repository.
   * @param <AggregateRootT> the type of the aggregate root.
   * @param <IdT>            the type of the aggregate root identifier.
   * @param repositoryType   the full generic type.
   * @param qualifier        the repository qualifier.
   * @return an instance of the repository.
   */
  <RepositoryT extends Repository<AggregateRootT, IdT>,
    AggregateRootT extends AggregateRoot<IdT>,
    IdT> RepositoryT getRepository(
    Type repositoryType, Class<? extends Annotation> qualifier);

  /**
   * Get a {@link Repository} from the domain.
   *
   * @param <RepositoryT>    the type of the repository.
   * @param <AggregateRootT> the type of the aggregate root.
   * @param <IdT>            the type of the aggregate root identifier.
   * @param repositoryType   the full generic type.
   * @param qualifier        the repository qualifier.
   * @return an instance of the repository.
   */
  <RepositoryT extends Repository<AggregateRootT, IdT>,
    AggregateRootT extends AggregateRoot<IdT>,
    IdT> RepositoryT getRepository(
    Type repositoryType, String qualifier);

  /**
   * Get the {@link Factory} for an aggregate root.
   *
   * @param <ProducibleT>   the type of the producible object.
   * @param producibleClass the producible class.
   * @return an instance of the factory.
   */
  <ProducibleT extends Producible> Factory<ProducibleT> getFactory(
    Class<ProducibleT> producibleClass);

  /**
   * Get the {@link Factory} with a qualifier for an aggregate root.
   *
   * @param <ProducibleT>   the type of the producible object.
   * @param producibleClass the producible class.
   * @param qualifier       the factory qualifier.
   * @return an instance of the factory.
   */
  <ProducibleT extends Producible> Factory<ProducibleT> getFactory(
    Class<ProducibleT> producibleClass,
    Class<? extends Annotation> qualifier);

  /**
   * Get the {@link Factory} with a qualifier for an aggregate root.
   *
   * @param <ProducibleT>   the type of the producible object.
   * @param producibleClass the producible class.
   * @param qualifier       the factory qualifier.
   * @return an instance of the factory.
   */
  <ProducibleT extends Producible> Factory<ProducibleT> getFactory(
    Class<ProducibleT> producibleClass, String qualifier);

  /**
   * Get a {@link Factory} from the domain.
   *
   * @param <FactoryT>    the type of the factory.
   * @param <ProducibleT> the type of the producible object.
   * @param factoryType   the capture of the full generic type.
   * @return an instance of the factory.
   */
  <FactoryT extends Factory<ProducibleT>, ProducibleT extends Producible> FactoryT getFactory(
    Type factoryType);

  /**
   * Get a {@link Factory} from the domain.
   *
   * @param <FactoryT>    the type of the factory.
   * @param <ProducibleT> the type of the producible object.
   * @param factoryType   the full generic type.
   * @param qualifier     the factory qualifier.
   * @return an instance of the factory.
   */
  <FactoryT extends Factory<ProducibleT>, ProducibleT extends Producible> FactoryT getFactory(
    Type factoryType,
    Class<? extends Annotation> qualifier);

  /**
   * Get a {@link Factory} from the domain.
   *
   * @param <FactoryT>    the type of the factory.
   * @param <ProducibleT> the type of the producible object.
   * @param factoryType   the full generic type.
   * @param qualifier     the factory qualifier.
   * @return an instance of the factory.
   */
  <FactoryT extends Factory<ProducibleT>, ProducibleT extends Producible> FactoryT getFactory(
    Type factoryType, String qualifier);

  /**
   * Get a {@link Service} from the domain.
   *
   * @param <ServiceT>   the type of the service.
   * @param serviceClass the class of the service interface.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Class<ServiceT> serviceClass);

  /**
   * Get a {@link Service} with a qualifier from the domain.
   *
   * @param <ServiceT>   the type of the service.
   * @param serviceClass the class of the service interface.
   * @param qualifier    the service qualifier.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Class<ServiceT> serviceClass,
    Class<? extends Annotation> qualifier);

  /**
   * Get a {@link Service} with a qualifier from the domain.
   *
   * @param <ServiceT>   the type of the service.
   * @param serviceClass the class of the service interface.
   * @param qualifier    the service qualifier.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Class<ServiceT> serviceClass, String qualifier);

  /**
   * Get a {@link Service} from the domain.
   *
   * @param <ServiceT>  the type of the service.
   * @param serviceType the full generic type.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Type serviceType);

  /**
   * Get a {@link Service} from the domain.
   *
   * @param <ServiceT>  the type of the service.
   * @param serviceType the full generic type.
   * @param qualifier   the service qualifier.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Type serviceType, Class<? extends Annotation> qualifier);

  /**
   * Get a {@link Service} from the domain.
   *
   * @param <ServiceT>  the type of the service.
   * @param serviceType the full generic type.
   * @param qualifier   the service qualifier.
   * @return an instance of the service.
   */
  <ServiceT> ServiceT getService(Type serviceType, String qualifier);

  /**
   * Get a {@link DomainPolicy} from the domain.
   *
   * @param <PolicyT>   the type of the policy.
   * @param policyClass the policy class.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Class<PolicyT> policyClass);

  /**
   * Get a {@link DomainPolicy} with a qualifier from the domain.
   *
   * @param <PolicyT>   the type of the policy.
   * @param policyClass the policy class.
   * @param qualifier   the policy qualifier.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Class<PolicyT> policyClass, Class<? extends Annotation> qualifier);

  /**
   * Get a {@link DomainPolicy} with a qualifier from the domain.
   *
   * @param <PolicyT>   the type of the policy.
   * @param policyClass the policy class.
   * @param qualifier   the policy qualifier.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Class<PolicyT> policyClass, String qualifier);

  /**
   * Get a {@link DomainPolicy} from the domain.
   *
   * @param <PolicyT>  the type of the policy.
   * @param policyType the full generic type.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Type policyType);

  /**
   * Get a {@link DomainPolicy} from the domain.
   *
   * @param <PolicyT>  the type of the policy.
   * @param policyType the full generic type.
   * @param qualifier  the policy qualifier.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Type policyType, Class<? extends Annotation> qualifier);

  /**
   * Get a {@link DomainPolicy} from the domain.
   *
   * @param <PolicyT>  the type of the policy.
   * @param policyType the full generic type.
   * @param qualifier  the policy qualifier.
   * @return an instance of the domain policy.
   */
  <PolicyT> PolicyT getPolicy(Type policyType, String qualifier);
}
