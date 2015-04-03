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

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.fixtures.domain.product.Product;
import org.seedstack.business.fixtures.domain.product.ProductNamePolicy;

import javax.inject.Inject;

public class ProductSimple1Assembler extends BaseAssembler<Product, ProductSimple1Representation> {

	
	@Inject
	ProductNamePolicy productNamePolicy;
	
	@Override
	protected void doAssembleDtoFromAggregate(ProductSimple1Representation targetDto , Product sourceEntity)
	{
		targetDto.fillProductId(sourceEntity.getEntityId().getStoreId() ,   productNamePolicy.extractNumber( sourceEntity.getEntityId().getProductCode())  );
		targetDto.setName(sourceEntity.getName());
		targetDto.setDescription(sourceEntity.getDescription());
	}

	@Override
	protected void doMergeAggregateWithDto(Product targetEntity , ProductSimple1Representation sourceDto)
	{
		targetEntity.setName(sourceDto.getName());
		targetEntity.setDescription(sourceDto.getDescription());
	}

}
