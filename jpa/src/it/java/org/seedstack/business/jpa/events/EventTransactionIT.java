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

import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.jpa.samples.domain.base.SampleBaseJpaFactory;
import org.seedstack.business.jpa.samples.domain.base.SampleBaseRepository;
import org.seedstack.business.jpa.samples.domain.tinyaggregate.TinyAggRoot;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.api.Propagation;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;
import java.util.UUID;

/**
 * This class tests that events are fired when repository methods are called.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 13/06/2014
 */
@RunWith(SeedITRunner.class)
public class EventTransactionIT {

    private static final String ID = "id";
    private static final String FAIL = "fail";

    @Inject
    private SampleBaseRepository sampleBaseRepository;

    @Inject
    private SampleBaseJpaFactory sampleBaseJpaFactory;

    @Inject
    private Repository<TinyAggRoot, String> tinyRepo;

    @Inject
    private Factory<TinyAggRoot> factory;

    static boolean aopWorks = false;

    static boolean aopWorksOnDefaultRepo = false;

    @Test
    @Transactional
    public void aop_on_repo_should_works() {
        sampleBaseRepository.persist(sampleBaseJpaFactory.create(UUID.randomUUID().toString()));
        Assertions.assertThat(aopWorks).isTrue();
    }

    @Test
    @Transactional
    public void aop_on_generic_repo_should_work() {
        tinyRepo.persist(factory.create(UUID.randomUUID().toString()));
        Assertions.assertThat(aopWorksOnDefaultRepo).isTrue();
    }

    @Test
    public void event_handler_should_rollback_the_repo_transaction() {
        try {
            // EventHandler throw an exception
            persist_failed();
        } catch (Exception e) { /* do nothing */ }
        // the repository transaction have been rolled back
        check_data_was_not_inserted();

        // Event handler succeed
        persist_succeeded();
        // the transaction is committed
        check_data_was_inserted();
    }

    @Transactional
    public void check_data_was_inserted() {
        Assertions.assertThat(sampleBaseRepository.load(ID)).isNotNull();
    }

    @Transactional
    public void check_data_was_not_inserted() {
        Assertions.assertThat(sampleBaseRepository.load(FAIL)).isNull();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persist_failed() {
        sampleBaseRepository.persist(sampleBaseJpaFactory.create(FAIL));
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persist_succeeded() {
        sampleBaseRepository.persist(sampleBaseJpaFactory.create(ID));
    }

}
