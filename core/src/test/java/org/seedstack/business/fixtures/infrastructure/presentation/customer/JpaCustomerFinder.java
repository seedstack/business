/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.infrastructure.presentation.customer;

import com.google.common.collect.Lists;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;
import org.seedstack.business.fixtures.interfaces.customer.presentation1.CustomerFinder;
import org.seedstack.business.fixtures.interfaces.customer.presentation1.CustomerRepresentation;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

public class JpaCustomerFinder implements CustomerFinder {

	@Inject
	CustomerRepository customerRepository;
	
	@Override
	public List<CustomerRepresentation> findAll() {
		
		
		
		Collection<Customer> customers = customerRepository.findAll();
		List<CustomerRepresentation> customerRepresentations = Lists.newArrayList();
		
		for(Customer c : customers)
		{
			String name = c.getFirstName() + ' ' + c.getLastName();
			CustomerRepresentation customerRepresentation = new CustomerRepresentation(name );
			customerRepresentations.add(customerRepresentation);
		}
		
		return customerRepresentations;
		
	}

}
