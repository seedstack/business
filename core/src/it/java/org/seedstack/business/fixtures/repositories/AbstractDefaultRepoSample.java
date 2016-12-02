/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.repositories;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;

import javax.inject.Inject;



public abstract class AbstractDefaultRepoSample<A extends AggregateRoot<K>, K> extends BaseRepository<A,K> {

    @Inject
    public AbstractDefaultRepoSample(@Assisted("aggregateRootClass") Object aggregateRootClass, @Assisted("keyClass") Object keyClass) {
        super((Class)aggregateRootClass, (Class)keyClass);
    }
}
