/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.interfaces.usecase1;

import org.seedstack.business.api.interfaces.assembler.BaseTupleAssembler;
import org.seedstack.business.sample.domain.activation.Activation;
import org.seedstack.business.sample.domain.customer.Customer;
import org.seedstack.business.sample.domain.order.Order;
import org.seedstack.business.sample.domain.product.Product;
import org.javatuples.Quartet;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class UseCase1Assembler extends BaseTupleAssembler<Quartet<Activation, Customer, Order, Product>, UseCase1Representation> {

	@Override
	protected void doAssembleDtoFromAggregate(UseCase1Representation targetDto, Quartet<Activation, Customer, Order, Product> sourceEntity) {
		targetDto.setActivationDescription(sourceEntity.getValue0().getDescription());
		targetDto.setCustomerFirstName(sourceEntity.getValue1().getFirstName());
		targetDto.setCustomerLastName(sourceEntity.getValue1().getLastName());
		targetDto.setOrder(sourceEntity.getValue2().getDescription());
		targetDto.setProductDescription(sourceEntity.getValue3().getDescription());
	}

	@Override
	protected void doMergeAggregateWithDto( Quartet<Activation, Customer, Order, Product> targetEntity,UseCase1Representation sourceDto) {
		targetEntity.getValue0().setDescription(sourceDto.getActivationDescription());
		targetEntity.getValue1().setFirstName(sourceDto.getCustomerFirstName());
		targetEntity.getValue1().setLastName(sourceDto.getCustomerLastName());
		targetEntity.getValue2().setDescription(sourceDto.getOrderDescription());
		targetEntity.getValue3().setDescription(sourceDto.getProductDescription());
	}

}
