/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.identity.embedded;

import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.IdentityGenerator;

public class AddOneGenerator implements IdentityGenerator<Long> {

    private static long actualValue=0;

    @Override
    public <E extends Entity<Long>> Long generate(Class<E> entityClass) {
        return getActualAndIncrement();
    }

    private static Long getActualAndIncrement(){
        return actualValue++;
    }
}
