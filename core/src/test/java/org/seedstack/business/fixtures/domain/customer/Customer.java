/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer;

import org.seedstack.business.domain.BaseAggregateRoot;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Customer extends BaseAggregateRoot<CustomerId> {
	
	@Inject
	transient CustomerSampleService service;
	
	private String firstName;
	private String lastName;
	private String primaryAccountNumber;
	
	private CustomerId entityId;

	
	private Map<String,Address> addresses = new HashMap<String, Address>();


	protected Customer() {
	}

	@Override
	public CustomerId getEntityId() {
		return entityId;
	}
	
	void setEntityId(CustomerId entityId)	{
		this.entityId = entityId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return lastName + ", " + firstName;
	}
	
	public void addAddress(Address.AddressType addressType , Address address)
	{
		addresses.put(addressType.toString(), address);
	}
	
	public Address getAddress(Address.AddressType addressType)
	{
		return addresses.get(addressType.toString());
	}
	public Map<String, Address> getAddresses()
	{
		return addresses;
	}
	
	public Collection<String> getAddressTypes()
	{
		return addresses.keySet();
	}
	
	/** entities may have method */
	public void relayInformation(String information)
	{
		service.transfer(this, this);
	}

	public String getPrimaryAccountNumber() {
		return primaryAccountNumber;
	}

	public void setPrimaryAccountNumber(String primaryAccountNumber) {
		this.primaryAccountNumber = primaryAccountNumber;
	}


}
