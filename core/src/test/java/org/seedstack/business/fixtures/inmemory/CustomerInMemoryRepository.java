/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory;

import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.seedstack.business.fixtures.domain.customer.CustomerRepository;
import org.seedstack.business.util.inmemory.BaseInMemoryRepository;
import org.seedstack.business.specification.Specification;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerInMemoryRepository extends BaseInMemoryRepository<Customer, CustomerId> implements CustomerRepository {
    @Override
    public Collection<Customer> findAll() {
        return get(Specification.any()).collect(Collectors.toList());
    }
}

