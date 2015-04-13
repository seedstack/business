/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.repositories.fixtures;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.core.domain.base.BaseRepository;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 22/09/2014
 */

public abstract class AbstractDefaultRepoSample<A extends AggregateRoot<K>, K> extends BaseRepository<A,K> {

    @Inject
    public AbstractDefaultRepoSample(@Assisted("aggregateRootClass") Object aggregateRootClass, @Assisted("keyClass") Object keyClass) {
        super((Class)aggregateRootClass, (Class)keyClass);
    }
}
