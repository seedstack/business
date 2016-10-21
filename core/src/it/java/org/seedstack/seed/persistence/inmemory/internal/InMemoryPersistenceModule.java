/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.persistence.inmemory.internal;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.seedstack.seed.Install;


@Install
class InMemoryPersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        InMemoryTransactionLink inMemoryTransactionLink = new InMemoryTransactionLink();
        requestInjection(inMemoryTransactionLink);
        InMemoryTransactionHandler transactionHandler = new InMemoryTransactionHandler(inMemoryTransactionLink);
        requestInjection(transactionHandler);
        bind(InMemoryTransactionHandler.class).toInstance(transactionHandler);
        bindListener(Matchers.any(), new InMemoryTypeListener(inMemoryTransactionLink));
    }
}