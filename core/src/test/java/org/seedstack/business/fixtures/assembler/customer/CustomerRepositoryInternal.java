/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.RepositoryOptions;
import org.seedstack.business.domain.specification.Specification;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


public class CustomerRepositoryInternal extends BaseRepository<Customer, String> implements CustomerRepository {

    private static Map<String, Customer> orderMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> get(String id) {
        return Optional.ofNullable(orderMap.get(id));
    }

    @Override
    public Stream<Customer> get(Specification<Customer> specification, RepositoryOptions... options) {
        return orderMap.values().stream().filter(specification.asPredicate());
    }

    @Override
    public boolean contains(String id) {
        return orderMap.containsKey(id);
    }

    @Override
    public long count(Specification<Customer> specification) {
        return get(specification).count();
    }

    @Override
    public long count() {
        return orderMap.size();
    }

    public void clear() {
        orderMap.clear();
    }

    @Override
    public void remove(String id) {
        orderMap.remove(id);
    }

    @Override
    public long remove(Specification<Customer> specification) {
        Iterator<Map.Entry<String, Customer>> iterator = orderMap.entrySet().iterator();
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

    @Override
    public void remove(Customer order) {
        for (Customer order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    public void add(Customer order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    public Customer update(Customer order) {
        return orderMap.put(order.getEntityId(), order);
    }
}
