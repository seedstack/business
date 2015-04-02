/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.usecase1;

import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class UseCase1Representation {
	
	
	private String activation;
	private String customer;
	private String order;
	private String productDescription;
	private String activationDescription;
	private String customerFirstName;
	private String customerLastName;
	private String orderDescription;
	private Short productStoreId;
	private String productCode;
	

	public UseCase1Representation() {
	}
	
	public UseCase1Representation( //
			String activation, String activationDescription,  //
			String customer, String customerFirstName, String customerLastName, // 
			String order, String orderDescription, // 
			Short productStoreId, String productCode, String productDescription) { //
		this.activation = activation;
		this.activationDescription = activationDescription;
		this.customer = customer;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.order = order;
		this.orderDescription = orderDescription;
		this.productStoreId = productStoreId;
		this.productCode = productCode;		
		this.productDescription = productDescription;
	
	}

	@MatchingEntityId(typeIndex=0)
	@MatchingFactoryParameter(typeIndex=0,index=0)
	public String getActivation() {
		return activation;
	}

	@MatchingEntityId(typeIndex=1)
	@MatchingFactoryParameter(typeIndex=1,index=0)
	public String getCustomer() {
		return customer;
	}

	@MatchingEntityId(typeIndex=2)
	@MatchingFactoryParameter(typeIndex=2,index=0)
	public String getOrder() {
		return order;
	}

	@MatchingEntityId( typeIndex=3)
	@MatchingFactoryParameter(typeIndex=3 , index = 0)
	public Short getProductStoreId() {
		return productStoreId;
	}
	
	@MatchingEntityId( typeIndex=3)	
	public String getProductCode() {
		return productCode;
	}
	
	@MatchingFactoryParameter(typeIndex=3 , index=1)
	public Short getProductCodeShort() {
		return Short.valueOf( productCode.split("-")[1] );
	}
	
	public String getProductDescription() {
		return productDescription;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	@MatchingFactoryParameter(typeIndex=0,index=1)
	public String getActivationDescription() {
		return activationDescription;
	}
	@MatchingFactoryParameter(typeIndex=1,index=1)
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	@MatchingFactoryParameter(typeIndex=1,index=2)
	public String getCustomerLastName() {
		return customerLastName;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setActivationDescription(String activationDescription) {
		this.activationDescription = activationDescription;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public void setProductStoreId(Short productStoreId) {
		this.productStoreId = productStoreId;
	}

}
