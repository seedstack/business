/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.registry;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.Service;
import org.seedstack.business.api.domain.DomainPolicy;
import org.seedstack.business.api.domain.DomainRegistry;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.internal.registry.fixtures.domain.Client;
import org.seedstack.business.internal.registry.fixtures.domain.Composite;
import org.seedstack.business.internal.registry.fixtures.domain.Product;
import org.seedstack.business.internal.registry.fixtures.policy.PolicyQualifier;
import org.seedstack.business.internal.registry.fixtures.policy.RebatePolicy;
import org.seedstack.business.internal.registry.fixtures.policy.RebatePolicyInternalWithQualifier;
import org.seedstack.business.internal.registry.fixtures.service.MyService;
import org.seedstack.business.internal.registry.fixtures.service.MyServiceInternal;
import org.seedstack.business.internal.registry.fixtures.service.MyServiceInternalWithQualiferNamed;
import org.seedstack.business.internal.registry.fixtures.service.RebateService;
import org.seedstack.business.internal.registry.fixtures.service.RebateServiceInternal;
import org.seedstack.business.internal.registry.fixtures.service.RebateServiceInternalWithQualifierComposite;
import org.seedstack.business.internal.registry.fixtures.service.RebateServiceInternalWithQualifierNamed;
import org.seedstack.business.internal.registry.fixtures.service.ServiceQualifier;
import org.seedstack.business.internal.registry.repository.ClientRepository;
import org.seedstack.business.internal.registry.repository.JpaQualifier;
import org.seedstack.seed.core.api.TypeOf;
import org.seedstack.seed.it.SeedITRunner;


/**
 * Integration test for {@link DomainRegistry}.
 * @author thierry.bouvet@mpsa.com
 *
 */
@RunWith(SeedITRunner.class)
public class DomainRegistyIT {

	@Inject
	DomainRegistry domainRegistry;
	
	/**
	 * Test to get a {@link Service} from the {@link DomainRegistry}.
	 */
	@Test
	public void testServiceBase() {
		MyService service = this.domainRegistry.getService(MyService.class);
		Assertions.assertThat(service).isInstanceOf(MyServiceInternal.class);
	}
	
	/**
	 * Test to get a {@link Service} from the {@link DomainRegistry}.
	 */
	@Test
	public void testService() {
		TypeOf<RebateService<Client>> type = new TypeOf<RebateService<Client>>(){};

		RebateService<Client> service = this.domainRegistry.getService(type);
		Assertions.assertThat(service).isInstanceOf(RebateServiceInternal.class);
	}

	/**
	 * Test to get a {@link Service} from the {@link DomainRegistry}.
	 */
	@Test
	public void testServiceWithStringQualifierFromTypeOf() {
		TypeOf<RebateService<Client>> type = new TypeOf<RebateService<Client>>(){};

		RebateService<Client> service = this.domainRegistry.getService(type,"Dummy");
		Assertions.assertThat(service).isInstanceOf(RebateServiceInternalWithQualifierNamed.class);
	}

	/**
	 * Test to get a {@link Service} from the {@link DomainRegistry}.
	 */
	@Test
	public void testServiceWithStringQualifier() {
		MyService service = this.domainRegistry.getService(MyService.class,"Dummy");
		Assertions.assertThat(service).isInstanceOf(MyServiceInternalWithQualiferNamed.class);
	}

	/**
	 * Test to get a {@link Service} with a qualifier from the {@link DomainRegistry}.
	 */
	@Test
	public void testServiceWithQualifierAndComposite() {
		TypeOf<RebateService<Composite<Long>>> type = new TypeOf<RebateService<Composite<Long>>>(){};
		RebateService<Composite<Long>> service = this.domainRegistry.getService(type,ServiceQualifier.class);
		Assertions.assertThat(service).isInstanceOf(RebateServiceInternalWithQualifierComposite.class);

	}

	/**
	 * Test to get a {@link DomainPolicy} from the {@link DomainRegistry}.
	 */
	@Test
	public void testPolicy() {
		RebatePolicy policy = this.domainRegistry.getPolicy(RebatePolicy.class);
		final int rebateExpected = 10;
		Assertions.assertThat(policy.calculateRebate(new Product(),10000)).isEqualTo(rebateExpected);
	}

	/**
	 * Test to get a {@link DomainPolicy} with a qualifier from the {@link DomainRegistry}.
	 */
	@Test
	public void testPolicyWithQualifier() {
		RebatePolicy policy = this.domainRegistry.getPolicy(RebatePolicy.class,PolicyQualifier.class);
		Assertions.assertThat(policy).isInstanceOf(RebatePolicyInternalWithQualifier.class);
	}

	/**
	 * Test to get a {@link Repository} from the {@link DomainRegistry}.
	 */
	@Test
	public void testRepository() {
		Repository<Client, Long> repository = this.domainRegistry.getRepository(Client.class, Long.class, JpaQualifier.class);
		Assertions.assertThat(repository).isInstanceOf(ClientRepository.class);
	}

	/**
	 * Test to get a {@link Repository} from the {@link DomainRegistry}.
	 */
	@Test
	public void testRepositoryFromTypeOf() {
		TypeOf<Repository<Client, Long>> typeOf = new TypeOf<Repository<Client, Long>>(){};
		Repository<Client, Long> repository = this.domainRegistry.getRepository(typeOf, JpaQualifier.class);
		Assertions.assertThat(repository).isInstanceOf(ClientRepository.class);
	}

	/**
	 * Test to get a {@link Factory} from the {@link DomainRegistry}.
	 */
	@Test
	public void testFactory() {
		Factory<Client> factory = domainRegistry.getFactory(Client.class);
		Assertions.assertThat(factory).isInstanceOf(Factory.class);
	}

	/**
	 * Test to get a {@link Factory} from the {@link DomainRegistry}.
	 */
	@Test
	public void testFactoryFromTypeOf() {
		TypeOf<Factory<Client>> typeOf = new TypeOf<Factory<Client>>(){};

		Factory<Client> factory = domainRegistry.getFactory(typeOf);
		Assertions.assertThat(factory).isInstanceOf(Factory.class);
	}

}
