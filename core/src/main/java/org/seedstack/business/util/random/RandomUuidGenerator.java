/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.util.random;

import java.util.UUID;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.util.UuidGenerator;

/**
 * Identity generator that generates {@link UUID} by using {@link UUID#randomUUID()}.
 */
@Random
public class RandomUuidGenerator implements UuidGenerator {
    @Override
    public <E extends Entity<UUID>> UUID generate(Class<E> entityClass) {
        return UUID.randomUUID();
    }
}
