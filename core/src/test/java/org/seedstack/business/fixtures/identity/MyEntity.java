/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.fixtures.identity;

import java.util.UUID;
import org.seedstack.business.domain.BaseEntity;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.util.UuidGenerator;
import org.seedstack.business.util.random.Random;

public class MyEntity extends BaseEntity<UUID> {

    @Identity(generator = UuidGenerator.class)
    @Random
    private UUID id;
}
