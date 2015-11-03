/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.activation;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.fixtures.domain.customer.CustomerId;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;


public class Activation extends BaseAggregateRoot<String>
{
	
	Date creationDate;
	Date activationDate;

    @NotBlank
	private String description;
	
	private String entityId;
	
	CustomerId customerId;
	
	Activation() {	
	}
	
	Activation(String entityId)
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

	public CustomerId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(CustomerId customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
