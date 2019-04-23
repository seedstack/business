/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.repositories;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import org.seedstack.business.domain.AggregateRoot;

public abstract class AbstractDefaultRepoSample<A extends AggregateRoot<K>, K> extends DummyRepository<A, K> {

    @Inject
    @SuppressWarnings("unchecked")
    public AbstractDefaultRepoSample(@Assisted("aggregateRootClass") Object aggregateRootClass,
            @Assisted("keyClass") Object keyClass) {
        super((Class) aggregateRootClass, (Class) keyClass);
    }
}
