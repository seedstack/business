/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.finder;

import com.google.common.collect.Lists;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

public class SomeCustomerFinder implements CustomerFinder {
    @Inject
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerRepresentation> findAll() {
        Collection<Customer> customers = customerRepository.findAll();
        List<CustomerRepresentation> customerRepresentations = Lists.newArrayList();

        for (Customer c : customers) {
            String name = c.getFirstName() + ' ' + c.getLastName();
            CustomerRepresentation customerRepresentation = new CustomerRepresentation(name);
            customerRepresentations.add(customerRepresentation);
        }

        return customerRepresentations;
    }
}
