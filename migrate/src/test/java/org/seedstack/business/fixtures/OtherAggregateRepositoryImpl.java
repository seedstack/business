/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures;

import org.seedstack.business.util.inmemory.BaseInMemoryRepository;

public class OtherAggregateRepositoryImpl extends BaseInMemoryRepository<OtherAggregate, String>
        implements OtherAggregateRepository {

    @Override
    public OtherAggregate loadSomething(String id) {
        return get(id).orElse(null);
    }
}
