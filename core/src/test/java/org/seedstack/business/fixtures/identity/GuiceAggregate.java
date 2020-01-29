/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.fixtures.identity;

import com.google.inject.name.Named;
import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.util.SequenceGenerator;

public class GuiceAggregate extends BaseAggregateRoot<Long> {
    @Identity(generator = SequenceGenerator.class)
    @Named("guice")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}