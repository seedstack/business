/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;

import org.seedstack.business.internal.datasecurity.Restriction;

public class MyCustomerRepresentation {

	private String id;
	private String name;
	
	@Restriction("${ hasRole('jedi') && hasPermission('academy:learn')  }")
	private String primaryAccountNumber;

    @MatchingEntityId
    @MatchingFactoryParameter(index=0)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@MatchingFactoryParameter(index=1)
	public String factoryFirstName()
	{
		if ( name != null )
		{
			return name.split(" ")[0];
		}
		return null;
	}

	@MatchingFactoryParameter(index=2)
	public String factoryLastName()
	{
		if ( name != null )
		{
			return name.split(" ")[1];
		}
		return null;
		
	}

	public String getPrimaryAccountNumber() {
		return primaryAccountNumber;
	}

	public void setPrimaryAccountNumber(String primaryAccountNumber) {
		this.primaryAccountNumber = primaryAccountNumber;
	}

}

