/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.api.domain.identity;

import com.google.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	
	@Test
	public void test(){
		MyAggregate myAggregate = myAggregateFactory.createMyAggregate("test");
		Assertions.assertThat(myAggregate.getEntityId()).isNotNull();
		Assertions.assertThat(myAggregate.getMySubEntity().getEntityId()).isNotNull();
		for (MyEntity entity : myAggregate.getMySubEntities()) {
			Assertions.assertThat(entity.getEntityId()).isNotNull();

		}
	}
}
