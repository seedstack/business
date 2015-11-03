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
package org.seedstack.business.factories.fixtures;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * 
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 * 
 */
public class MyFactoryAggregate extends BaseAggregateRoot<String> {
	private String id;
	
	
	public MyFactoryAggregate(String id) {
		super();
		this.id = id;
	}


	public MyFactoryAggregate() {
		super();
	}


	@Override
	public String getEntityId() {
		return id;
	}
}
