/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.identity;

import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.IdentityGenerator;

public class RandomIdentityGenerator implements IdentityGenerator<Double> {
    @Override
    public <E extends Entity<Double>> Double generate(Class<E> entityClass) {
        return Math.random();
    }
}
