/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.util.inmemory;

import java.util.concurrent.atomic.AtomicLong;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.util.SequenceGenerator;

/**
 * Identity generator that generates {@link Long} numbers by using an every-incrementing {@link
 * AtomicLong}.
 */
@InMemory
public class InMemorySequenceGenerator implements SequenceGenerator {
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public <E extends Entity<Long>> Long generate(Class<E> entityClass) {
        return sequence.incrementAndGet();
    }
}
