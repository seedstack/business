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
package org.seedstack.business.fixtures.domain.multi;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Dummy aggregate for test
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class Multi extends BaseAggregateRoot<String> {

	String id; 

	@Override
	public String getEntityId() {
		return "";
	}

	/**
	 * Getter id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter id
	 * 
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
}
