/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.BindingAnnotation;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;
import org.seedstack.business.Service;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.BusinessException;

/**
 * Unit test for {@link DomainRegistryImpl}
 */
public class DomainRegistryImplTest {
    @Mocked
    private Injector injector;

    /**
     * Create a {@link DomainRegistry} with a mocker {@link Injector}.
     *
     * @return a new {@link DomainRegistry}
     */
    private DomainRegistry createDomainRegistry() {
        DomainRegistry domainRegistry = new DomainRegistryImpl();
        Deencapsulation.setField(domainRegistry, "injector", injector);
        return domainRegistry;
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetRepositoryTypeOf(final @Mocked Repository<AggregateRoot<Long>, Long> repository1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository1;
            }
        };

        Repository<AggregateRoot<Long>, Long> repository2 = domainRegistry.getRepository(
                new TypeLiteral<Repository<AggregateRoot<Long>, Long>>() {}.getType());
        assertThat(repository2).isEqualTo(repository1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetRepositoryTypeOfOfTClassOfQextendsAnnotation(
            final @Mocked Repository<AggregateRoot<Long>, Long> repository1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository1;
            }
        };

        Repository<AggregateRoot<Long>, Long> repository2 = domainRegistry.getRepository(
                new TypeLiteral<Repository<AggregateRoot<Long>, Long>>() {}.getType(), MockedAnnotation.class);
        assertThat(repository2).isEqualTo(repository1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetRepositoryTypeOfOfTString(final @Mocked Repository<AggregateRoot<Long>, Long> repository1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository1;
            }
        };

        Repository<AggregateRoot<Long>, Long> repository2 = domainRegistry.getRepository(
                new TypeLiteral<Repository<AggregateRoot<Long>, Long>>() {}.getType(), "dummyAnnotation");
        assertThat(repository2).isEqualTo(repository1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRepositoryClassOfAClassOfKClassOfQextendsAnnotation(
            final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        assertThat(domainRegistry.getRepository(AggregateRoot.class, Long.class, MockedAnnotation.class)).isEqualTo(
                repository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRepositoryClassOfAClassOfKString(
            final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        assertThat(domainRegistry.getRepository(AggregateRoot.class, Long.class, "dummyAnnotation")).isEqualTo(
                repository);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfT(final @Mocked Factory<?> factory1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory1;
            }
        };

        Factory<AggregateRoot<Long>> factory2 = domainRegistry.getFactory(
                new TypeLiteral<Factory<AggregateRoot<Long>>>() {}.getType());
        assertThat(factory2).isEqualTo(factory1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfTClassOfQextendsAnnotation(final @Mocked Factory<?> factory1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory1;
            }
        };

        Factory<AggregateRoot<Long>> factory2 = domainRegistry.getFactory(
                new TypeLiteral<Factory<AggregateRoot<Long>>>() {}.getType(), MockedAnnotation.class);
        assertThat(factory2).isEqualTo(factory1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfTString(final @Mocked Factory<?> factory1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory1;
            }
        };

        Factory<AggregateRoot<Long>> factory2 = domainRegistry.getFactory(
                new TypeLiteral<Factory<AggregateRoot<Long>>>() {}.getType(), "dummyAnnotation");
        assertThat(factory2).isEqualTo(factory1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfT(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        assertThat(domainRegistry.getFactory(AggregateRoot.class)).isEqualTo(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfTClassOfQextendsAnnotation(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        assertThat(domainRegistry.getFactory(AggregateRoot.class, MockedAnnotation.class)).isEqualTo(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfTString(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        assertThat(domainRegistry.getFactory(AggregateRoot.class, "dummyAnnotation")).isEqualTo(factory);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfT(final @Mocked MockedServiceParameterized<Long> service1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service1;
            }
        };

        MockedServiceParameterized<Long> service2 = domainRegistry.getService(
                new TypeLiteral<MockedServiceParameterized<Long>>() {}.getType());
        assertThat(service2).isEqualTo(service1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfTClassOfQextendsAnnotation(
            final @Mocked MockedServiceParameterized<Long> service1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service1;
            }
        };

        MockedServiceParameterized<Long> service2 = domainRegistry.getService(
                new TypeLiteral<MockedServiceParameterized<Long>>() {}.getType(), MockedAnnotation.class);
        assertThat(service2).isEqualTo(service1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfTString(final @Mocked MockedServiceParameterized<Long> service1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service1;
            }
        };

        MockedServiceParameterized<Long> service2 = domainRegistry.getService(
                new TypeLiteral<MockedServiceParameterized<Long>>() {}.getType(), "dummyAnnotation");
        assertThat(service2).isEqualTo(service1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfT(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        assertThat(domainRegistry.getService(MockedService.class)).isEqualTo(service);
    }

    @Test(expected = BusinessException.class)
    public void testGetServiceBad() {
        DomainRegistry domainRegistry = createDomainRegistry();

        domainRegistry.getService(MockedBadService.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfTClassOfQextendsAnnotation(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        assertThat(domainRegistry.getService(MockedService.class, MockedAnnotation.class)).isEqualTo(service);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfTString(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        assertThat(domainRegistry.getService(MockedService.class, "dummyAnnotation")).isEqualTo(service);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfT(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        assertThat(domainRegistry.getPolicy(MockedPolicy.class)).isEqualTo(policy);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfTClassOfQextendsAnnotation(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        assertThat(domainRegistry.getPolicy(MockedPolicy.class, MockedAnnotation.class)).isEqualTo(policy);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfTString(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        assertThat(domainRegistry.getPolicy(MockedPolicy.class, "dummyAnnotation")).isEqualTo(policy);
    }

    @Test(expected = BusinessException.class)
    public void testGetPolicyBad() {
        DomainRegistry domainRegistry = createDomainRegistry();

        domainRegistry.getPolicy(MockedBadPolicy.class);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfT(final @Mocked MockedPolicyParameterized<Long> policy1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy1;
            }
        };

        MockedPolicyParameterized<Long> policy2 = domainRegistry.getPolicy(
                new TypeLiteral<MockedPolicyParameterized<Long>>() {}.getType());
        assertThat(policy2).isEqualTo(policy1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfTClassOfQextendsAnnotation(final @Mocked MockedPolicyParameterized<Long> policy1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy1;
            }
        };

        MockedPolicyParameterized<Long> policy2 = domainRegistry.getPolicy(
                new TypeLiteral<MockedPolicyParameterized<Long>>() {}.getType(), MockedAnnotation.class);
        assertThat(policy2).isEqualTo(policy1);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfTString(final @Mocked MockedPolicyParameterized<Long> policy1) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new Expectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy1;
            }
        };

        MockedPolicyParameterized<Long> policy2 = domainRegistry.getPolicy(
                new TypeLiteral<MockedPolicyParameterized<Long>>() {}.getType(), "dummyAnnotation");
        assertThat(policy2).isEqualTo(policy1);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @BindingAnnotation
    private @interface MockedAnnotation {

    }

    @Service
    private interface MockedService {

    }

    private interface MockedBadService {

    }

    @Service
    private interface MockedServiceParameterized<T> {

    }

    private interface MockedBadPolicy {

    }

    @DomainPolicy
    private interface MockedPolicy {

    }

    @DomainPolicy
    private interface MockedPolicyParameterized<T> {

    }

}
