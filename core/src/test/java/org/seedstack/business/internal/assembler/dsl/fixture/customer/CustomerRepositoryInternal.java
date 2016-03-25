/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.assembler.dsl.fixture.customer;

import org.seedstack.business.domain.BaseRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class CustomerRepositoryInternal extends BaseRepository<Customer, String> implements CustomerRepository {

    private static Map<String, Customer> orderMap = new ConcurrentHashMap<String, Customer>();

    @Override
    public Customer load(String id) {
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
    public void delete(Customer order) {
        for (Customer order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    public void persist(Customer order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    public Customer save(Customer order) {
        return orderMap.put(order.getEntityId(), order);
    }
}
