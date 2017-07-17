/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import org.seedstack.seed.ClassConfiguration;

import javax.inject.Named;
import java.util.UUID;

/**
 * Simple UUID generator which uses {@link UUID#randomUUID()}.
 */
@Named("simpleUUID")
public class SimpleUUIDGenerator implements UUIDGenerator {
    @Override
    public UUID handle(Entity<UUID> entity, ClassConfiguration<Entity<UUID>> entityConfiguration) {
        return UUID.randomUUID();
    }
}
