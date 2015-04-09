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
import org.seedstack.business.api.domain.annotations.DomainRepositoryImpl;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 22/09/2014
 */
@DomainRepositoryImpl
public class DefaultRepoSample<A extends AggregateRoot<K>, K> extends AbstractDefaultRepoSample<A,K>  {

    @Inject
    public DefaultRepoSample(@Assisted Class<?>[] genericClasses) {
        super(genericClasses[0], genericClasses[1]);
    }

    @Override
    protected A doLoad(K id) {
        return null;
    }

    @Override
    protected void doDelete(K id) {

    }

    @Override
    protected void doDelete(A a) {

    }

    @Override
    protected void doPersist(A a) {

    }

    @Override
    protected A doSave(A a) {
        return null;
    }
}
