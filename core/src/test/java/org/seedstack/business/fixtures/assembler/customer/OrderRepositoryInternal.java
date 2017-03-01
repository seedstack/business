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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


public class OrderRepositoryInternal extends BaseRepository<Order, String> implements OrderRepository {
    private static Map<String, Order> orderMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Order> get(String id) {
        return Optional.ofNullable(orderMap.get(id));
    }

    @Override
    public Stream<Order> get(Specification<Order> specification, RepositoryOptions... options) {
        return orderMap.values().stream().filter(specification.asPredicate());
    }

    @Override
    public boolean contains(String id) {
        return orderMap.containsKey(id);
    }

    @Override
    public long count(Specification<Order> specification) {
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
    public long remove(Specification<Order> specification) {
        return 0;
    }

    @Override
    public void remove(Order order) {
        for (Order order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    public void add(Order order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    public Order update(Order order) {
        return orderMap.put(order.getEntityId(), order);
    }
}
