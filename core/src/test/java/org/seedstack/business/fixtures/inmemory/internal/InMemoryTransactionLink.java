/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.inmemory.internal;

import org.seedstack.seed.transaction.spi.TransactionalLink;

import java.util.ArrayDeque;
import java.util.Deque;


class InMemoryTransactionLink implements TransactionalLink<String> {
    private final ThreadLocal<Deque<String>> perThreadObjectContainer = ThreadLocal.withInitial(ArrayDeque::new);

    @Override
    public String get() {
        String entityManager = this.perThreadObjectContainer.get().peek();

        if (entityManager == null) {
            throw new IllegalStateException("A store must be specified with @Store before accessing in memory map");
        }

        return entityManager;
    }

    void push(String entityManager) {
        perThreadObjectContainer.get().push(entityManager);
    }

    String pop() {
        return perThreadObjectContainer.get().pop();
    }
}