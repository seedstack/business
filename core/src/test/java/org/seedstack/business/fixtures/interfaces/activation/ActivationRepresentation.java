/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.activation;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;

public class ActivationRepresentation {
	
	String uuid;
	String customerId;
	
	public ActivationRepresentation(String uuid, String customerId) {  
		this.uuid = uuid;
		this.customerId = customerId;
	}
	
	@MatchingEntityId
	@MatchingFactoryParameter(index=0)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@MatchingFactoryParameter(index=1)
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	
	

}
