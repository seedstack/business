/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.product;


import org.seedstack.business.api.domain.BaseAggregateRoot;


public class Product extends BaseAggregateRoot<ProductId> {
	

	private String name;
	private String description;
	private ProductId entityId;
	
	
	Product() {
	}
	
	Product(ProductId productId)
	{
		this.entityId = productId;
	}

	@Override
	public ProductId getEntityId() {
		return this.entityId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
