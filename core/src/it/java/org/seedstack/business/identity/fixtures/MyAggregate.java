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
package org.seedstack.business.identity.fixtures;

import org.seedstack.business.api.domain.annotations.identity.Identity;
import org.seedstack.business.api.domain.base.BaseAggregateRoot;
import org.seedstack.business.api.domain.identity.UUIDHandler;

import java.util.Set;
import java.util.UUID;

/** 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class MyAggregate extends BaseAggregateRoot<UUID> {

	@Identity(handler = UUIDHandler.class)
	private UUID id;
	
	private String name;
	
	private MyEntity mySubEntity;
	
	private Set<MyEntity> mySubEntities;
	
	@Override
	public UUID getEntityId() {
		return id;
	}



	/**
	 * Setter id
	 * 
	 * @param id the id to set
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
	public MyEntity getMySubEntity() {
		return mySubEntity;
	}


	/**
	 * Setter mySubAggregate
	 * 
	 * @param mySubEntity the mySubAggregate to set
	 */
	public void setMySubEntity(MyEntity mySubEntity) {
		this.mySubEntity = mySubEntity;
	}


	/**
	 * Getter mySubAggregates
	 * 
	 * @return the mySubAggregates
	 */
	public Set<MyEntity> getMySubEntities() {
		return mySubEntities;
	}


	/**
	 * Setter mySubAggregates
	 * 
	 * @param mySubEntities the mySubAggregates to set
	 */
	public void setMySubAggregates(Set<MyEntity> mySubEntities) {
		this.mySubEntities = mySubEntities;
	}





	
	

}
