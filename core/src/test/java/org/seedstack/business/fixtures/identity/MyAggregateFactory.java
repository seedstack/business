/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.fixtures.identity;

import org.seedstack.business.domain.Create;
import org.seedstack.business.domain.Factory;

@Create
public interface MyAggregateFactory extends Factory<MyAggregate> {

    MyAggregate createMyAggregate(String name);

    MyAggregate createMyAggregate();

}
