/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.fixtures.identity.GuiceAggregate;
import org.seedstack.business.fixtures.identity.MyAggregate;
import org.seedstack.business.fixtures.identity.MyAggregateFactory;
import org.seedstack.business.fixtures.identity.MyEntity;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class IdentityGeneratorIT {
    @Inject
    private MyAggregateFactory myAggregateFactory;
    @Inject
    private Factory<MyAggregate> factory1;
    @Inject
    private Factory<GuiceAggregate> factory2;

    @Test
    public void sameClass() {
        assertThat(myAggregateFactory).isInstanceOf(MyAggregateFactory.class);
        assertThat(factory1).isInstanceOf(MyAggregateFactory.class);
        assertThat(factory1.getClass()).isSameAs(myAggregateFactory.getClass());
    }

    @Test
    public void testCustomFactory() {
        MyAggregate myAggregate = myAggregateFactory.createMyAggregate("test");
        assertThat(myAggregate.getId()).isNotNull();
        assertThat(myAggregate.getMySubEntity()
                .getId()).isNotNull();
        for (MyEntity entity : myAggregate.getMySubEntities()) {
            assertThat(entity.getId()).isNotNull();
        }
    }

    @Test
    public void testDefaultFactory() {
        MyAggregate myAggregate = factory1.create();
        assertThat(myAggregate.getId()).isNotNull();
    }

    @Test
    public void testGuiceQualifier() {
        GuiceAggregate guiceAggregate = factory2.create();
        assertThat(guiceAggregate.getId()).isNotNull();
    }
}
