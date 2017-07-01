/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer;

import org.seedstack.business.domain.BaseFactory;

public class CustomerFactoryDefault extends BaseFactory<Customer> implements CustomerFactory {
    @Override
    public Customer createNewCustomer(String entityId, String firstName, String lastName) {
        Customer customer = new Customer(new CustomerId(entityId));
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    @Override
    public Customer createNewCustomer(String entityId, String firstName, String lastName, String addressType, String line1, String line2, String zipCode, String country) {
        Customer customer = createNewCustomer(entityId, firstName, lastName);
        Address address = new Address(line1, line2, zipCode, country);
        Address.AddressType addressTypeType = Address.AddressType.valueOf(addressType.toLowerCase());
        customer.addAddress(addressTypeType, address);
        return customer;
    }
}

