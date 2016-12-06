/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.fixtures.identity;

import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.BaseAggregateRoot;

import java.util.Set;
import java.util.UUID;

/**
 * MyAggregateWithBadEntityManagement
 */
public class MyAggregateWithBadIdentityManagement extends
		BaseAggregateRoot<UUID> {

	@Identity(handler = RandomIdentityHandler.class)
	private UUID id;

	private String name;

	private MyEntity mySubAggregate;

	private Set<MyEntity> mySubAggregates;

	@Override
	public UUID getEntityId() {
		return id;
	}

	/**
	 * Setter id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Getter name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter mySubAggregate
	 * 
	 * @return the mySubAggregate
	 */
	public MyEntity getMySubAggregate() {
		return mySubAggregate;
	}

	/**
	 * Setter mySubAggregate
	 * 
	 * @param mySubAggregate
	 *            the mySubAggregate to set
	 */
	public void setMySubAggregate(MyEntity mySubAggregate) {
		this.mySubAggregate = mySubAggregate;
	}

	/**
	 * Getter mySubAggregates
	 * 
	 * @return the mySubAggregates
	 */
	public Set<MyEntity> getMySubAggregates() {
		return mySubAggregates;
	}

	/**
	 * Setter mySubAggregates
	 * 
	 * @param mySubAggregates
	 *            the mySubAggregates to set
	 */
	public void setMySubAggregates(Set<MyEntity> mySubAggregates) {
		this.mySubAggregates = mySubAggregates;
	}

}
