/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.export;

import org.seedstack.business.api.domain.base.BaseAggregateRoot;

import java.util.Date;


public class Export extends BaseAggregateRoot<String> {
	
	Date creationDate;
	Date activationDate;
	private String description;
	
	private String entityId;

	Export() {	
	}
	
	Export(String entityId)
	{
		this.entityId = entityId;
	}
	
	@Override
	public String getEntityId() {
		return this.entityId;
	}
	 
	void setEntityId(String entityId)
	{
		this.entityId = entityId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
