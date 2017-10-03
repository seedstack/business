/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.assembler.customer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.specification.Specification;

public class CustomerRepositoryInternal extends BaseRepository<Customer, String> implements CustomerRepository {

    private static Map<String, Customer> orderMap = new ConcurrentHashMap<>();

    @Override
    public void add(Customer order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public Stream<Customer> get(Specification<Customer> specification, Repository.Option... options) {
        return orderMap.values()
                .stream()
                .filter(specification.asPredicate());
    }

    @Override
    public long remove(Specification<Customer> specification) {
        Iterator<Map.Entry<String, Customer>> iterator = orderMap.entrySet()
                .iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Customer> next = iterator.next();
            if (specification.isSatisfiedBy(next.getValue())) {
                iterator.remove();
                count++;
            }
        }
        return count;
    }
}
