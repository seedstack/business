/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.fixtures.repositories;

import java.util.stream.Stream;
import org.seedstack.business.domain.Repository;

public interface TestAggregate4Repository extends Repository<TestAggregate4, String> {
    default Stream<TestAggregate4> someMethod(String identity) {
        return get(getSpecificationBuilder().ofAggregate(TestAggregate4.class)
                .identity().is(identity)
                .build()
        );
    }
}
