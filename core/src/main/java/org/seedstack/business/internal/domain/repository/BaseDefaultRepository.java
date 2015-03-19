/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.domain.repository;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.core.domain.base.BaseRepository;

import javax.inject.Inject;

/**
 * A base default repository. It forces subclasses to implements an assisted constructor.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 30/09/2014
 * @param <A> 
 * @param <K> 
 */
public abstract class BaseDefaultRepository<A extends AggregateRoot<K>, K> extends BaseRepository<A,K> {

    /**
     * Constructor.
     *
     * @param aggregateRootClass the aggregate root class
     * @param keyClass the aggregate key class
     */
    @Inject
    public BaseDefaultRepository(@Assisted("aggregateRootClass") Class<A> aggregateRootClass, @Assisted("keyClass") Class<K> keyClass) {
        super(aggregateRootClass, keyClass);
    }
}