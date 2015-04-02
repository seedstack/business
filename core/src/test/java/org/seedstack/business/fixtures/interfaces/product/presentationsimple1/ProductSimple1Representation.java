/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.product.presentationsimple1;

import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;

public class ProductSimple1Representation {

	private Short storeId;
	private Short productCode;
	private String name;
	private String description;

	public ProductSimple1Representation() {
	}
	
	public ProductSimple1Representation(Short storeId , Short productCode , String name , String description) {
		this.storeId = storeId;
		this.productCode = productCode;
		this.name = name;
		this.description = description;
	}

	
	/**
	 * method does not start with get so it won't be serialized in json by default.
	 * 
	 * @return the store
	 */
	@MatchingEntityId (index=0)
	@MatchingFactoryParameter(index=0)
	public Short getStoreId() {
		return storeId;
	}
	
	/**
	 * 
	 * @return
	 */
	@MatchingEntityId (index=1)
	@MatchingFactoryParameter(index=1)
	public Short getProductCode() {
		return productCode;
	}
	
	public String getId() {
		return "" + storeId +  "-" + productCode;
	}

	public void fillProductId(short storeId , Short productCode) {
		this.storeId = storeId;
		this.productCode = productCode;
	}
	
	@MatchingFactoryParameter(index=2)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@MatchingFactoryParameter(index=3)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	

}
