/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer;

import org.seedstack.business.domain.DomainErrorCodes;
import org.seedstack.business.domain.BaseFactory;
import org.seedstack.seed.SeedException;

public class CustomerFactoryDefault  extends BaseFactory<Customer> implements CustomerFactory {

	@Override
	public Customer createNewCustomer(String entityId, String firstName,String lastName) {
		
		CustomerId id = new CustomerId(entityId);
		
		Customer customer = new Customer();
		customer.setEntityId(id);
		customer.setFirstName( firstName );
		customer.setLastName(  lastName);
	    
		return customer;
	}
	
	@Override
	public Customer createNewCustomer(String entityId, String firstName, String lastName, String addressType, String line1, String line2,String zipCode,String country) {
		Customer customer = createNewCustomer(entityId, firstName, lastName);
		
		Address address = new Address(line1, line2, zipCode,country);
		Address.AddressType addressTypeType = Address.AddressType.valueOf(addressType.toLowerCase());

        if (null == addressTypeType) {
            throw SeedException.createNew(DomainErrorCodes.AGGREGATE_ROOT_CREATION_ISSUE)
                    .put("aggregateRoot", Customer.class.getSimpleName());
        }

		customer.addAddress(addressTypeType, address);
		
		return customer;
	}
	
}

