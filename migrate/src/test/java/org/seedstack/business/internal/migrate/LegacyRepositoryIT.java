/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.internal.migrate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.LegacyRepository;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.fixtures.OtherAggregate;
import org.seedstack.business.fixtures.OtherAggregateRepository;
import org.seedstack.business.fixtures.OtherAggregateRepositoryImpl;
import org.seedstack.business.fixtures.SomeAggregate;
import org.seedstack.business.util.inmemory.DefaultInMemoryRepository;
import org.seedstack.business.util.inmemory.InMemory;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class LegacyRepositoryIT {
    @Inject
    @InMemory
    private Repository<SomeAggregate, Long> myRepository;
    @Inject
    @InMemory
    private LegacyRepository<SomeAggregate, Long> myLegacyRepository;
    @Inject
    private OtherAggregateRepository otherAggregateRepository;

    @Test
    public void repositoriesAreInjected() {
        assertThat(myRepository).isInstanceOf(DefaultInMemoryRepository.class);
        assertThat(myLegacyRepository).isInstanceOf(LegacyRepositoryAdapter.class);
        assertThat(otherAggregateRepository).isInstanceOf(OtherAggregateRepositoryImpl.class);
    }

    @Test
    public void repositoriesAreWorking() {
        myLegacyRepository.add(new SomeAggregate(1L));
        assertThat(myRepository.get(1L).get().getEntityId()).isEqualTo(1L);

        otherAggregateRepository.add(new OtherAggregate("test"));
        assertThat(otherAggregateRepository.loadSomething("test").getEntityId()).isEqualTo("test");
    }
}
