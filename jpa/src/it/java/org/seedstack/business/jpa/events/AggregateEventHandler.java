/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.events;

import org.seedstack.business.api.EventHandler;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.domain.events.AggregatePersistedEvent;
import org.seedstack.business.jpa.samples.domain.base.SampleBaseJpaAggregateRoot;
import org.seedstack.business.jpa.samples.domain.tinyaggregate.TinyAggRoot;
import org.seedstack.seed.core.utils.SeedCheckUtils;
import org.seedstack.seed.persistence.jpa.api.Jpa;

import javax.inject.Inject;

/**
 * Sample of EventHandler used for test.
 *
 * @author pierre.thirouin@ext.mpsa.com
 */
public class AggregateEventHandler implements EventHandler<AggregatePersistedEvent> {

    @Inject @Jpa
    private Repository<TinyAggRoot, String> repository;

    static int counter;

    static int autoRepoCounter;

    @Override
    public void handle(AggregatePersistedEvent event) {
        // Event handler should be able to inject domain elements
        SeedCheckUtils.checkIfNotNull(repository);

        if (SampleBaseJpaAggregateRoot.class.equals(event.getAggregateRoot())) {
            if ("fail".equals(event.getContext().getArgs()[0])) {
                // when an exception is thrown the transaction should be rolled back
                throw new RuntimeException("I don't want you to persist that!");
            } else {
                EventTransactionIT.aopWorks = true; // listen persist event on user's repo
            }
        } else if (TinyAggRoot.class.equals(event.getAggregateRoot())) {
            EventTransactionIT.aopWorksOnDefaultRepo = true; // listen persist event on default repo
        }
    }
}
