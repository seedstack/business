/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * This registry allows to access domain objects programmatically.
 *
 * Usage for a class without generic parameter:
 * <pre>
 * <code>MyPolicy policy = domainRegistry.getPolicy(MyPolicy.class,"qualifier");
 * </code>
 * </pre>
 *
 * Usage for a class with generic parameter(s):
 * <pre>
 * <code>AnotherPolicy&lt;MyClient&lt;Long&gt;&gt; policy = domainRegistry.getPolicy(
 *     new TypeOf&lt;AnotherPolicy&lt;MyClient&lt;Long&gt;&gt;&gt;(){},
 *     "qualifier"
 * );
 * </code>
 * </pre>
 */
public interface DomainRegistry {

    /**
     * Get the {@link Repository} for an aggregate root.
     *
     * @param <A>                the type of the aggregate root.
     * @param <ID>               the type of the aggregate root identifier.
     * @param aggregateRootClass the aggregate root class.
     * @param idClass            the aggregate root identifier class.
     * @return an instance of the repository.
     */
    <A extends AggregateRoot<ID>, ID> Repository<A, ID> getRepository(Class<A> aggregateRootClass, Class<ID> idClass);

    /**
     * Get the {@link Repository} for an aggregate root and a qualifier.
     *
     * @param <A>                the type of the aggregate root.
     * @param <ID>               the type of the aggregate root identifier.
     * @param aggregateRootClass the aggregate root class.
     * @param idClass            the aggregate root identifier class.
     * @param qualifier          the repository qualifier.
     * @return an instance of the repository.
     */
    <A extends AggregateRoot<ID>, ID> Repository<A, ID> getRepository(Class<A> aggregateRootClass, Class<ID> idClass, Class<? extends Annotation> qualifier);

    /**
     * Get the {@link Repository} for an aggregate root and a qualifier.
     *
     * @param <A>                the type of the aggregate root.
     * @param <ID>               the type of the aggregate root identifier.
     * @param aggregateRootClass the aggregate root class.
     * @param idClass            the aggregate root identifier class.
     * @param qualifier          the repository qualifier.
     * @return an instance of the repository.
     */
    <A extends AggregateRoot<ID>, ID> Repository<A, ID> getRepository(Class<A> aggregateRootClass, Class<ID> idClass, String qualifier);

    /**
     * Get a {@link Repository} from the domain.
     *
     * @param <R>            the type of the repository.
     * @param <A>            the type of the aggregate root.
     * @param <ID>           the type of the aggregate root identifier.
     * @param repositoryType the full generic type.
     * @return an instance of the repository.
     */
    <R extends Repository<A, ID>, A extends AggregateRoot<ID>, ID> R getRepository(Type repositoryType);

    /**
     * Get a {@link Repository} from the domain.
     *
     * @param <R>            the type of the repository.
     * @param <A>            the type of the aggregate root.
     * @param <ID>           the type of the aggregate root identifier.
     * @param repositoryType the full generic type.
     * @param qualifier      the repository qualifier.
     * @return an instance of the repository.
     */
    <R extends Repository<A, ID>, A extends AggregateRoot<ID>, ID> R getRepository(Type repositoryType, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link Repository} from the domain.
     *
     * @param <R>            the type of the repository.
     * @param <A>            the type of the aggregate root.
     * @param <ID>           the type of the aggregate root identifier.
     * @param repositoryType the full generic type.
     * @param qualifier      the repository qualifier.
     * @return an instance of the repository.
     */
    <R extends Repository<A, ID>, A extends AggregateRoot<ID>, ID> R getRepository(Type repositoryType, String qualifier);

    /**
     * Get the {@link Factory} for an aggregate root.
     *
     * @param <P>             the type of the producible object.
     * @param producibleClass the producible class.
     * @return an instance of the factory.
     */
    <P extends Producible> Factory<P> getFactory(Class<P> producibleClass);

    /**
     * Get the {@link Factory} with a qualifier for an aggregate root.
     *
     * @param <P>             the type of the producible object.
     * @param producibleClass the producible class.
     * @param qualifier       the factory qualifier.
     * @return an instance of the factory.
     */
    <P extends Producible> Factory<P> getFactory(Class<P> producibleClass, Class<? extends Annotation> qualifier);

    /**
     * Get the {@link Factory} with a qualifier for an aggregate root.
     *
     * @param <P>             the type of the producible object.
     * @param producibleClass the producible class.
     * @param qualifier       the factory qualifier.
     * @return an instance of the factory.
     */
    <P extends Producible> Factory<P> getFactory(Class<P> producibleClass, String qualifier);

    /**
     * Get a {@link Factory} from the domain.
     *
     * @param <F>         the type of the factory.
     * @param <P>         the type of the producible object.
     * @param factoryType the capture of the full generic type.
     * @return an instance of the factory.
     */
    <F extends Factory<P>, P extends Producible> F getFactory(Type factoryType);

    /**
     * Get a {@link Factory} from the domain.
     *
     * @param <F>         the type of the factory.
     * @param <P>         the type of the producible object.
     * @param factoryType the full generic type.
     * @param qualifier   the factory qualifier.
     * @return an instance of the factory.
     */
    <F extends Factory<P>, P extends Producible> F getFactory(Type factoryType, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link Factory} from the domain.
     *
     * @param <F>         the type of the factory.
     * @param <P>         the type of the producible object.
     * @param factoryType the full generic type.
     * @param qualifier   the factory qualifier.
     * @return an instance of the factory.
     */
    <F extends Factory<P>, P extends Producible> F getFactory(Type factoryType, String qualifier);

    /**
     * Get a {@link Service} from the domain.
     *
     * @param <S>          the type of the service.
     * @param serviceClass the class of the service interface.
     * @return an instance of the service.
     */
    <S> S getService(Class<S> serviceClass);

    /**
     * Get a {@link Service} with a qualifier from the domain.
     *
     * @param <S>          the type of the service.
     * @param serviceClass the class of the service interface.
     * @param qualifier    the service qualifier.
     * @return an instance of the service.
     */
    <S> S getService(Class<S> serviceClass, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link Service} with a qualifier from the domain.
     *
     * @param <S>          the type of the service.
     * @param serviceClass the class of the service interface.
     * @param qualifier    the service qualifier.
     * @return an instance of the service.
     */
    <S> S getService(Class<S> serviceClass, String qualifier);

    /**
     * Get a {@link Service} from the domain.
     *
     * @param <S>         the type of the service.
     * @param serviceType the full generic type.
     * @return an instance of the service.
     */
    <S> S getService(Type serviceType);

    /**
     * Get a {@link Service} from the domain.
     *
     * @param <S>         the type of the service.
     * @param serviceType the full generic type.
     * @param qualifier   the service qualifier.
     * @return an instance of the service.
     */
    <S> S getService(Type serviceType, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link Service} from the domain.
     *
     * @param <S>         the type of the service.
     * @param serviceType the full generic type.
     * @param qualifier   the service qualifier.
     * @return an instance of the service.
     */
    <S> S getService(Type serviceType, String qualifier);

    /**
     * Get a {@link DomainPolicy} from the domain.
     *
     * @param <P>         the type of the policy.
     * @param policyClass the policy class.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Class<P> policyClass);

    /**
     * Get a {@link DomainPolicy} with a qualifier from the domain.
     *
     * @param <P>         the type of the policy.
     * @param policyClass the policy class.
     * @param qualifier   the policy qualifier.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Class<P> policyClass, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link DomainPolicy} with a qualifier from the domain.
     *
     * @param <P>         the type of the policy.
     * @param policyClass the policy class.
     * @param qualifier   the policy qualifier.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Class<P> policyClass, String qualifier);

    /**
     * Get a {@link DomainPolicy} from the domain.
     *
     * @param <P>        the type of the policy.
     * @param policyType the full generic type.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Type policyType);

    /**
     * Get a {@link DomainPolicy} from the domain.
     *
     * @param <P>        the type of the policy.
     * @param policyType the full generic type.
     * @param qualifier  the policy qualifier.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Type policyType, Class<? extends Annotation> qualifier);

    /**
     * Get a {@link DomainPolicy} from the domain.
     *
     * @param <P>        the type of the policy.
     * @param policyType the full generic type.
     * @param qualifier  the policy qualifier.
     * @return an instance of the domain policy.
     */
    <P> P getPolicy(Type policyType, String qualifier);
}
