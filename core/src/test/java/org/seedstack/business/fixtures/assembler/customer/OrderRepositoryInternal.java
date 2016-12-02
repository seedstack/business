/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.assembler.customer;

import org.seedstack.business.domain.BaseRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class OrderRepositoryInternal extends BaseRepository<Order, String> implements OrderRepository {

    private static Map<String, Order> orderMap = new ConcurrentHashMap<>();

    @Override
    public Order load(String id) {
        return orderMap.get(id);
    }

    @Override
    public boolean exists(String id) {
        return orderMap.containsKey(id);
    }

    @Override
    public long count() {
        return orderMap.size();
    }

    public void clear() {
        orderMap.clear();
    }

    @Override
    public void delete(String id) {
        orderMap.remove(id);
    }

    @Override
    public void delete(Order order) {
        for (Order order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    public void persist(Order order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    public Order save(Order order) {
        return orderMap.put(order.getEntityId(), order);
    }
}
