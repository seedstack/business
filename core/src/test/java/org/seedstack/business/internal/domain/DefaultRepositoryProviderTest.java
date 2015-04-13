/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain;

import org.seedstack.business.core.domain.InMemoryRepository;
import org.seedstack.business.internal.strategy.api.GenericImplementationProvider;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerId;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 26/09/2014
 */
public class DefaultRepositoryProviderTest {

//    @Test
    public void create_default_repository() {
        GenericImplementationProvider provider = new GenericImplementationProvider(InMemoryRepository.class, Customer.class, CustomerId.class);
       // Repository<Customer, CustomerId> repository = provider.get();
//        Assertions.assertThat(repository).isNotNull();
//        Assertions.assertThat(repository).isInstanceOf(InMemoryRepository.class);
//        Assertions.assertThat(repository.getAggregateRootClass()).isEqualTo(Customer.class);
//        Assertions.assertThat(repository.getKeyClass()).isEqualTo(CustomerId.class);
    }
}
