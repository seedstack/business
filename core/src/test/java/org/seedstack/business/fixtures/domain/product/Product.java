/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.product;


import org.seedstack.business.domain.BaseAggregateRoot;


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
