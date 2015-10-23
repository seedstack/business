/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.repositories.fixtures;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.spi.GenericImplementation;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author pierre.thirouin@ext.mpsa.com
 */
@Named("DefaultRepo")
@GenericImplementation
public class DefaultRepoSample<A extends AggregateRoot<K>, K> extends AbstractDefaultRepoSample<A,K>  {

    @Inject
    public DefaultRepoSample(@Assisted Object[] genericClasses) {
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
