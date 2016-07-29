/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.test;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.identity.SequenceHandler;

import javax.inject.Named;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author redouane.loulou@ext.mpsa.com
 */
@Named("inmemory-sequence")
public class InMemorySequenceHandler implements SequenceHandler<Entity<Long>, Long> {
    private static final AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Long handle(Entity<Long> entity, Configuration entityConfiguration) {
        return sequence.incrementAndGet();
    }
}
