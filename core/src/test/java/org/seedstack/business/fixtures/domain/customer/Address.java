/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer;

import org.seedstack.business.domain.BaseValueObject;

public class Address extends BaseValueObject {
	
	public enum AddressType { shipping , billing , contact }
	
    private String address1;
	private String address2;
	private String zipcode;
	private String country;

	
	protected Address()
	{
	}
	
	
	public Address(String address1 , String address2 , String zipcode , String country)
    {
		this.address1 = address1;
		this.address2 = address2;
		this.zipcode = zipcode;
		this.country = country;
    }
	
	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getZipcode() {
		return zipcode;
	}
	
	public String getCountry() {
		return country;
	}

}
