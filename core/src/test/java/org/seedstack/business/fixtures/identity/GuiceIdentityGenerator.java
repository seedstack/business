/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.identity;

import javax.inject.Named;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.util.SequenceGenerator;

@Named("guice")
public class GuiceIdentityGenerator implements SequenceGenerator {
    @Override
    public <E extends Entity<Long>> Long generate(Class<E> entityClass) {
        return (long) Math.random();
    }
}
