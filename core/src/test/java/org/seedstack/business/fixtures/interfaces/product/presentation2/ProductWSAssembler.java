/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.product.presentation2;

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.fixtures.domain.product.Product;

public class ProductWSAssembler extends BaseAssembler<Product, ProductWSFacade> {

	@Override
	protected void doAssembleDtoFromAggregate(ProductWSFacade targetDto , Product sourceEntity)
	{
		String productCode = sourceEntity.getEntityId().getProductCode().split("-")[1].trim();
		targetDto.fillProductId(sourceEntity.getEntityId().getStoreId(), Short.valueOf(productCode));
		targetDto.setName(sourceEntity.getName());
		targetDto.setDescription(sourceEntity.getDescription());
	}

	@Override
	protected void doMergeAggregateWithDto(Product targetEntity , ProductWSFacade sourceDto)
	{
		targetEntity.setName(sourceDto.getName());
		targetEntity.setDescription(sourceDto.getDescription());
	}
	
}
