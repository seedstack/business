/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory.test;

import org.seedstack.business.domain.BaseAggregateRoot;

public class VehicleType extends BaseAggregateRoot<VehicleTypeId> {
    /**
     * The id.
     */
    private VehicleTypeId id;

    public VehicleType(VehicleTypeId id) {
        this.id = id;
    }

    @Override
    public VehicleTypeId getId() {
        return id;
    }
}