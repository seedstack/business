/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.interfaces.assembler.dsl.fixture.customer;

import org.assertj.core.util.Maps;
import org.seedstack.business.core.domain.base.BaseRepository;

import java.util.Map;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class CustomerRepositoryInternal extends BaseRepository<Customer, String> implements CustomerRepository {

    private static Map<String, Customer> orderMap = Maps.newConcurrentHashMap();

    @Override
    protected Customer doLoad(String id) {
        return orderMap.get(id);
    }

    @Override
    protected void doDelete(String id) {
        orderMap.remove(id);
    }

    @Override
    protected void doDelete(Customer order) {
        for (Customer order1 : orderMap.values()) {
            if (order1.equals(order)) {
                orderMap.remove(order.getEntityId());
            }
        }
    }

    @Override
    protected void doPersist(Customer order) {
        orderMap.put(order.getEntityId(), order);
    }

    @Override
    protected Customer doSave(Customer order) {
        return orderMap.put(order.getEntityId(), order);
    }

    public void clear() {
        orderMap.clear();
    }
}
