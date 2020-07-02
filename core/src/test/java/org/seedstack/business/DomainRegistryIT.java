/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import com.google.inject.TypeLiteral;
import java.lang.reflect.Type;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.DomainPolicy;
import org.seedstack.business.domain.DomainRegistry;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.registry.TestDefaultRepository;
import org.seedstack.business.fixtures.registry.TestJpaQualifier;
import org.seedstack.business.fixtures.registry.domain.Client;
import org.seedstack.business.fixtures.registry.domain.Composite;
import org.seedstack.business.fixtures.registry.domain.Product;
import org.seedstack.business.fixtures.registry.policy.PolicyQualifier;
import org.seedstack.business.fixtures.registry.policy.RebatePolicy;
import org.seedstack.business.fixtures.registry.policy.RebatePolicyInternalWithQualifier;
import org.seedstack.business.fixtures.registry.service.MyService;
import org.seedstack.business.fixtures.registry.service.MyServiceInternal;
import org.seedstack.business.fixtures.registry.service.MyServiceInternalWithQualiferNamed;
import org.seedstack.business.fixtures.registry.service.RebateService;
import org.seedstack.business.fixtures.registry.service.RebateServiceInternal;
import org.seedstack.business.fixtures.registry.service.RebateServiceInternalWithQualifierComposite;
import org.seedstack.business.fixtures.registry.service.RebateServiceInternalWithQualifierNamed;
import org.seedstack.business.fixtures.registry.service.ServiceQualifier;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.seed.testing.junit4.SeedITRunner;

/**
 * Integration test for {@link DomainRegistry}.
 */
@RunWith(SeedITRunner.class)
public class DomainRegistryIT {
    @Inject
    private DomainRegistry domainRegistry;

    /**
     * Test to get a {@link Service} from the {@link DomainRegistry}.
     */
    @Test
    public void testServiceBase() {
        MyService service = this.domainRegistry.getService(MyService.class);
        Assertions.assertThat(service)
                .isInstanceOf(MyServiceInternal.class);
    }

    /**
     * Test a bad service from the {@link DomainRegistry}.
     */
    @Test(expected = BusinessException.class)
    public void testBadService() {
        this.domainRegistry.getService(String.class);
    }

    /**
     * Test to get a {@link Service} from the {@link DomainRegistry}.
     */
    @Test
    public void testService() {
        Type type = new TypeLiteral<RebateService<Client>>() {}.getType();

        RebateService<Client> service = this.domainRegistry.getService(type);
        Assertions.assertThat(service)
                .isInstanceOf(RebateServiceInternal.class);
    }

    /**
     * Test to get a {@link Service} from the {@link DomainRegistry}.
     */
    @Test
    public void testServiceWithStringQualifierFromTypeOf() {
        Type type = new TypeLiteral<RebateService<Client>>() {}.getType();

        RebateService<Client> service = this.domainRegistry.getService(type, "Dummy");
        Assertions.assertThat(service)
                .isInstanceOf(RebateServiceInternalWithQualifierNamed.class);
    }

    /**
     * Test to get a {@link Service} from the {@link DomainRegistry}.
     */
    @Test
    public void testServiceWithStringQualifier() {
        MyService service = this.domainRegistry.getService(MyService.class, "Dummy");
        Assertions.assertThat(service)
                .isInstanceOf(MyServiceInternalWithQualiferNamed.class);
    }

    /**
     * Test to get a {@link Service} with a qualifier from the {@link DomainRegistry}.
     */
    @Test
    public void testServiceWithQualifierAndComposite() {
        Type type = new TypeLiteral<RebateService<Composite<Long>>>() {}.getType();
        RebateService<Composite<Long>> service = this.domainRegistry.getService(type, ServiceQualifier.class);
        Assertions.assertThat(service)
                .isInstanceOf(RebateServiceInternalWithQualifierComposite.class);

    }

    /**
     * Test to get a {@link DomainPolicy} from the {@link DomainRegistry}.
     */
    @Test
    public void testPolicy() {
        RebatePolicy policy = this.domainRegistry.getPolicy(RebatePolicy.class);
        final int rebateExpected = 10;
        Assertions.assertThat(policy.calculateRebate(new Product(), 10000))
                .isEqualTo(rebateExpected);
    }

    /**
     * Test a bad service from the {@link DomainRegistry}.
     */
    @Test(expected = BusinessException.class)
    public void testBadPolicy() {
        this.domainRegistry.getPolicy(String.class);
    }

    /**
     * Test to get a {@link DomainPolicy} with a qualifier from the {@link DomainRegistry}.
     */
    @Test
    public void testPolicyWithQualifier() {
        RebatePolicy policy = this.domainRegistry.getPolicy(RebatePolicy.class, PolicyQualifier.class);
        Assertions.assertThat(policy)
                .isInstanceOf(RebatePolicyInternalWithQualifier.class);
    }

    /**
     * Test to get a {@link Repository} from the {@link DomainRegistry}.
     */
    @Test
    public void testRepository() {
        Repository<Client, Long> repository = this.domainRegistry.getRepository(Client.class, Long.class,
                TestJpaQualifier.class);
        Assertions.assertThat(repository)
                .isInstanceOf(TestDefaultRepository.class);
    }

    /**
     * Test to get a {@link Repository} from the {@link DomainRegistry}.
     */
    @Test
    public void testRepositoryFromTypeOf() {
        Type type = new TypeLiteral<Repository<Client, Long>>() {}.getType();
        Repository<Client, Long> repository = this.domainRegistry.getRepository(type, TestJpaQualifier.class);
        Assertions.assertThat(repository)
                .isInstanceOf(TestDefaultRepository.class);
    }

    /**
     * Test to get a {@link Factory} from the {@link DomainRegistry}.
     */
    @Test
    public void testFactory() {
        Factory<Client> factory = domainRegistry.getFactory(Client.class);
        Assertions.assertThat(factory)
                .isInstanceOf(Factory.class);
    }

    /**
     * Test to get a {@link Factory} from the {@link DomainRegistry}.
     */
    @Test
    public void testFactoryFromTypeOf() {
        Type type = new TypeLiteral<Factory<Client>>() {}.getType();

        Factory<Client> factory = domainRegistry.getFactory(type);
        Assertions.assertThat(factory)
                .isInstanceOf(Factory.class);
    }

}
