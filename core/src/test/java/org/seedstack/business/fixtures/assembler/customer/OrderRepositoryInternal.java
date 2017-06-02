/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.specification.Specification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class OrderRepositoryInternal extends BaseRepository<Order, String> implements OrderRepository {
    private static Map<String, Order> orderMap = new ConcurrentHashMap<>();

    @Override
    public void add(Order order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public Stream<Order> get(Specification<Order> specification, Repository.Options... options) {
        return orderMap.values().stream().filter(specification.asPredicate());
    }


    @Override
    public long remove(Specification<Order> specification) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Order order1 : orderMap.values()) {
            if (specification.isSatisfiedBy(order1)) {
                if (orderMap.remove(order1.getId()) != null) {
                    atomicInteger.incrementAndGet();
                }
            }
        }
        return atomicInteger.get();
    }
}
