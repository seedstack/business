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
package org.seedstack.business.jpa.samples.domain.identity;

import org.seedstack.business.api.domain.annotations.identity.Identity;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;
import org.seedstack.business.api.domain.identity.SequenceHandler;

import java.util.Set;

/**
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class MyAggregate extends BaseAggregateRoot<Long> {

	@Identity(handler = SequenceHandler.class)
	private Long id;
	
	private String name;
	
	private MyEntity mySubAggregate;
	
	private Set<MyEntity> mySubAggregates;
	
	@Override
	public Long getEntityId() {
		return id;
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
	 * @param name the name to set
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
	 * @param mySubAggregate the mySubAggregate to set
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
	 * @param mySubAggregates the mySubAggregates to set
	 */
	public void setMySubAggregates(Set<MyEntity> mySubAggregates) {
		this.mySubAggregates = mySubAggregates;
	}





	
	

}
