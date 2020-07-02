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
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.GenericFactory;
import org.seedstack.business.fixtures.OtherAggregate;
import org.seedstack.business.fixtures.OtherAggregateFactory;
import org.seedstack.business.fixtures.OtherAggregateFactoryImpl;
import org.seedstack.business.fixtures.SomeAggregate;
import org.seedstack.business.internal.domain.DefaultFactory;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class LegacyFactoryIT {
    @Inject
    private Factory<SomeAggregate> someAggregateFactory;
    @Inject
    private GenericFactory<SomeAggregate> someAggregateGenericFactory;
    @Inject
    private OtherAggregateFactory otherAggregateFactory1;
    @Inject
    private Factory<OtherAggregate> otherAggregateFactory2;
    @Inject
    private GenericFactory<OtherAggregate> otherAggregateGenericFactory;

    @Test
    public void factoriesAreInjected() {
        assertThat(someAggregateFactory).isInstanceOf(DefaultFactory.class);
        assertThat(someAggregateGenericFactory).isInstanceOf(GenericFactoryAdapter.class);

        assertThat(otherAggregateFactory1).isInstanceOf(OtherAggregateFactoryImpl.class);
        assertThat(otherAggregateFactory2).isInstanceOf(OtherAggregateFactoryImpl.class);
        assertThat(otherAggregateGenericFactory).isInstanceOf(GenericFactoryAdapter.class);
    }

    @Test
    public void factoriesAreWorking() {
        assertThat(someAggregateFactory.create(1L).getEntityId()).isEqualTo(1L);
        assertThat(someAggregateGenericFactory.create(1L).getEntityId()).isEqualTo(1L);

        assertThat(otherAggregateFactory1.createOtherAggregate("1").getEntityId()).isEqualTo("1");
        assertThat(otherAggregateFactory2.create("1").getEntityId()).isEqualTo("1");
        assertThat(otherAggregateGenericFactory.create("1").getEntityId()).isEqualTo("1");
    }
}
