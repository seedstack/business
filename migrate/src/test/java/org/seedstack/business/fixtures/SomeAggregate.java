/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures;

import org.seedstack.business.domain.LegacyBaseAggregateRoot;

public class SomeAggregate extends LegacyBaseAggregateRoot<Long> {
    private Long id;

    public SomeAggregate(String id) {
        this.id = Long.parseLong(id);
    }

    public SomeAggregate(Long id) {
        this.id = id;
    }

    @Override
    public Long getEntityId() {
        return id;
    }
}
