/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory;

import java.util.Collection;
import java.util.stream.Collectors;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.util.inmemory.BaseInMemoryRepository;

public class CustomerInMemoryRepository extends BaseInMemoryRepository<Customer, CustomerId> implements
        CustomerRepository {

    @Override
    public Collection<Customer> findAll() {
        return get(Specification.any()).collect(Collectors.toList());
    }
}

