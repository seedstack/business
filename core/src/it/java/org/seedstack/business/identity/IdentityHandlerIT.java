/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.identity;

import com.google.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.identity.fixtures.MyAggregate;
import org.seedstack.business.identity.fixtures.MyAggregateFactory;
import org.seedstack.business.identity.fixtures.MyEntity;
import org.seedstack.seed.it.SeedITRunner;

/**
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
@RunWith(SeedITRunner.class)
public class IdentityHandlerIT {

	@Inject
	private MyAggregateFactory myAggregateFactory;

    @Inject
    private Factory<MyAggregate> factory;
	
	@Test
	public void testCustomFactory(){
		MyAggregate myAggregate = myAggregateFactory.createMyAggregate("test");
        Assertions.assertThat(myAggregate.getEntityId()).isNotNull();
        Assertions.assertThat(myAggregate.getMySubEntity().getEntityId()).isNotNull();
        for (MyEntity entity : myAggregate.getMySubEntities()) {
            Assertions.assertThat(entity.getEntityId()).isNotNull();
        }
	}

    @Test
    public void testDefaultFactory() {
        MyAggregate myAggregate = factory.create();
        Assertions.assertThat(myAggregate.getEntityId()).isNotNull();
    }

}
