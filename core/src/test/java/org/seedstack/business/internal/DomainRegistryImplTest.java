/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import com.google.inject.BindingAnnotation;
import com.google.inject.Injector;
import com.google.inject.Key;
import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.StrictExpectations;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.business.Service;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.DomainElement;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.internal.DomainRegistryImpl;
import org.seedstack.seed.SeedException;
import org.seedstack.shed.reflect.TypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Unit test for {@link DomainRegistryImpl}
 */
public class DomainRegistryImplTest {
    @Mocked
    private Injector injector;

    @DomainElement
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
    public void testGetRepositoryTypeOfOfTClassOfQextendsAnnotation(final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        Assertions.assertThat(domainRegistry.getRepository(new TypeOf<Repository<AggregateRoot<Long>, Long>>() {
        }, MockedAnnotation.class)).isEqualTo(repository);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetRepositoryTypeOfOfTString(final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        Assertions.assertThat(domainRegistry.getRepository(new TypeOf<Repository<AggregateRoot<Long>, Long>>() {
        }, "dummyAnnotation")).isEqualTo(repository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRepositoryClassOfAClassOfKClassOfQextendsAnnotation(final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        Assertions.assertThat(domainRegistry.getRepository(AggregateRoot.class, Long.class, MockedAnnotation.class)).isEqualTo(repository);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRepositoryClassOfAClassOfKString(final @Mocked Repository<AggregateRoot<Long>, Long> repository) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Repository<AggregateRoot<Long>, Long>>) any);
                result = repository;
            }
        };

        Assertions.assertThat(domainRegistry.getRepository(AggregateRoot.class, Long.class, "dummyAnnotation")).isEqualTo(repository);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfT(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(new TypeOf<Factory<AggregateRoot<Long>>>() {
        })).isEqualTo(factory);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfTClassOfQextendsAnnotation(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(new TypeOf<Factory<AggregateRoot<Long>>>() {
        }, MockedAnnotation.class)).isEqualTo(factory);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetFactoryTypeOfOfTString(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(new TypeOf<Factory<AggregateRoot<Long>>>() {
        }, "dummyAnnotation")).isEqualTo(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfT(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(AggregateRoot.class)).isEqualTo(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfTClassOfQextendsAnnotation(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(AggregateRoot.class, MockedAnnotation.class)).isEqualTo(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetFactoryClassOfTString(final @Mocked Factory<?> factory) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<Factory<AggregateRoot<Long>>>) any);
                result = factory;
            }
        };

        Assertions.assertThat(domainRegistry.getFactory(AggregateRoot.class, "dummyAnnotation")).isEqualTo(factory);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfT(final @Mocked MockedServiceParameterized<Long> service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(new TypeOf<MockedServiceParameterized<Long>>() {
        })).isEqualTo(service);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfTClassOfQextendsAnnotation(final @Mocked MockedServiceParameterized<Long> service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(new TypeOf<MockedServiceParameterized<Long>>() {
        }, MockedAnnotation.class)).isEqualTo(service);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetServiceTypeOfOfTString(final @Mocked MockedServiceParameterized<Long> service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedServiceParameterized<Long>>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(new TypeOf<MockedServiceParameterized<Long>>() {
        }, "dummyAnnotation")).isEqualTo(service);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfT(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(MockedService.class)).isEqualTo(service);
    }

    @Test(expected = SeedException.class)
    public void testGetServiceBad() {
        DomainRegistry domainRegistry = createDomainRegistry();

        domainRegistry.getService(MockedBadService.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfTClassOfQextendsAnnotation(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(MockedService.class, MockedAnnotation.class)).isEqualTo(service);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetServiceClassOfTString(final @Mocked MockedService service) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedService>) any);
                result = service;
            }
        };

        Assertions.assertThat(domainRegistry.getService(MockedService.class, "dummyAnnotation")).isEqualTo(service);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfT(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(MockedPolicy.class)).isEqualTo(policy);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfTClassOfQextendsAnnotation(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(MockedPolicy.class, MockedAnnotation.class)).isEqualTo(policy);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPolicyClassOfTString(final @Mocked MockedPolicy policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicy>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(MockedPolicy.class, "dummyAnnotation")).isEqualTo(policy);
    }

    @Test(expected = SeedException.class)
    public void testGetPolicyBad() {
        DomainRegistry domainRegistry = createDomainRegistry();

        domainRegistry.getPolicy(MockedBadPolicy.class);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfT(final @Mocked MockedPolicyParameterized<Long> policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(new TypeOf<MockedPolicyParameterized<Long>>() {
        })).isEqualTo(policy);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfTClassOfQextendsAnnotation(final @Mocked MockedPolicyParameterized<Long> policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(new TypeOf<MockedPolicyParameterized<Long>>() {
        }, MockedAnnotation.class)).isEqualTo(policy);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testGetPolicyTypeOfOfTString(final @Mocked MockedPolicyParameterized<Long> policy) {
        DomainRegistry domainRegistry = createDomainRegistry();

        new StrictExpectations() {
            {
                injector.getInstance((Key<MockedPolicyParameterized<Long>>) any);
                result = policy;
            }
        };

        Assertions.assertThat(domainRegistry.getPolicy(new TypeOf<MockedPolicyParameterized<Long>>() {
        }, "dummyAnnotation")).isEqualTo(policy);
    }

}
