/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.seedstack.business.fixtures.inmemory.CustomerInMemoryRepository;
import org.seedstack.business.fixtures.repositories.DefaultRepoSample;
import org.seedstack.business.fixtures.repositories.DefaultRepoSample2;
import org.seedstack.business.fixtures.repositories.DefaultRepoSample3;
import org.seedstack.business.fixtures.repositories.TestAggregate;
import org.seedstack.business.fixtures.repositories.TestAggregate2;
import org.seedstack.business.fixtures.repositories.TestAggregate3Repository;
import org.seedstack.business.fixtures.repositories.TestAggregate3RepositoryImpl;
import org.seedstack.business.fixtures.repositories.TestAggregate4;
import org.seedstack.business.fixtures.repositories.TestAggregate4Repository;
import org.seedstack.business.util.inmemory.DefaultInMemoryRepository;
import org.seedstack.business.util.inmemory.InMemory;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class RepositoryIT {
    @Inject
    @Named("DefaultRepo")
    private Repository<TestAggregate, String> testRepo;

    @Inject
    @Named("mock")
    private Repository<TestAggregate, String> testRepo2;

    @Inject
    private Repository<Customer, CustomerId> customerRepo;

    @Inject
    private Repository<TestAggregate, String> testRepo3;

    @Inject
    private Repository<TestAggregate2, String> testRepo4;

    @Inject
    private TestAggregate3Repository testRepo5;

    @Inject
    @InMemory
    private TestAggregate4Repository testRepo6;

    @Inject
    private TestAggregate4Repository testRepo7;

    @Test
    public void testDefaultImplementations() {
        assertThat(testRepo).isInstanceOf(DefaultRepoSample.class);
        assertThat(testRepo2).isInstanceOf(DefaultRepoSample2.class);
        assertThat(customerRepo).isInstanceOf(CustomerInMemoryRepository.class);
        assertThat(testRepo3).isInstanceOf(DefaultRepoSample.class);
        assertThat(testRepo4).isInstanceOf(DefaultRepoSample3.class);
    }

    @Test
    public void testExplicitImplementations() {
        assertThat(testRepo5).isInstanceOf(TestAggregate3RepositoryImpl.class);
    }

    @Test
    public void testGeneratedImplementations() {
        assertThat(testRepo6).isInstanceOf(DefaultInMemoryRepository.class);
        testRepo6.add(new TestAggregate4("someValue"));
        assertThat(testRepo6.someMethod("someValue")).containsExactly(new TestAggregate4("someValue"));

        assertThat(testRepo7).isInstanceOf(DefaultRepoSample.class);
        assertThat(testRepo7.someMethod("someValue")).isEmpty();
    }
}
