/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.infrastructure.persistence.customer;

import com.google.common.collect.Lists;
import org.seedstack.business.internal.defaults.InMemoryRepository;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;

import java.util.Collection;

public class CustomerInMemoryRepository extends InMemoryRepository <Customer,CustomerId> implements CustomerRepository {

	@Override
	public Collection<Customer> findAll() {
		
		Collection<Customer > customers = Lists.newArrayList();
		
        for (Object object : inMemorySortedMap.values())
        {
        	if (Customer.class.isAssignableFrom(object.getClass()))
        	{
        		customers.add((Customer) object);
        	}
        }
		return customers;
	}
}

