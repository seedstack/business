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

import org.seedstack.business.core.domain.base.BaseFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author redouane.loulou@ext.mpsa.com
 * 
 */
public class MyAggregateFactoryDefault extends BaseFactory<MyAggregate>
		implements MyAggregateFactory {

	@Override
	public MyAggregate createMyAggregate(String name) {
		MyAggregate myAggregate = new MyAggregate();
		myAggregate.setName(name);
		MyEntity mySubAggregate = createMySubAggregate();
		myAggregate.setMySubEntity(mySubAggregate);
		Set<MyEntity> mySubAggregates = new HashSet<MyEntity>();
		mySubAggregates.add(createMySubAggregate());
		mySubAggregates.add(mySubAggregate);
		mySubAggregates.add(createMySubAggregate());
		mySubAggregates.add(createMySubAggregate());
		myAggregate.setMySubAggregates(mySubAggregates);
		return myAggregate;
	}

	MyEntity createMySubAggregate() {
		return new MyEntity();
	}
}
