/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.fixture.customer;

import org.assertj.core.util.Maps;
import org.seedstack.business.domain.BaseRepository;

import java.util.Map;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class OrderRepositoryInternal extends BaseRepository<Order, String> implements OrderRepository {

    private static Map<String, Order> orderMap = Maps.newConcurrentHashMap();

    @Override
    protected Order doLoad(String id) {
        return orderMap.get(id);
    }

    @Override
    protected void doDelete(String id) {
        orderMap.remove(id);
    }

    @Override
    protected void doDelete(Order order) {
        for (Order order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    protected void doPersist(Order order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    protected Order doSave(Order order) {
        return orderMap.put(order.getEntityId(), order);
    }

    public void clear() {
        orderMap.clear();
    }
}
