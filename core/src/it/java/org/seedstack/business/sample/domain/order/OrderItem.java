/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.domain.order;

import org.seedstack.business.api.domain.base.BaseEntity;
import org.seedstack.business.sample.domain.product.ProductId;


public class OrderItem extends BaseEntity<Long>
{

	private int quantity;
	
	private ProductId productId;

	private Long entityId;

	public OrderItem()
	{
	}
	

	public OrderItem(int quantity , ProductId productId)
	{
		this.quantity = quantity;
		this.productId = productId;
	}
	
	void setEntityId (Long entityId)
	{
		this.entityId = entityId;
	}
	
	@Override
	public Long getEntityId() {		
		return this.entityId;
	}

	public ProductId getProductId() {
		return productId;
	}	

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setProductId(ProductId productId) {
		this.productId = productId;
	}
	private static final long serialVersionUID = -7687748254687098576L;


}
