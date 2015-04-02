/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.interfaces.activation;

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.fixtures.domain.activation.Activation;
import org.seedstack.business.fixtures.domain.customer.CustomerId;

public class ActivationAssembler extends BaseAssembler<Activation,ActivationRepresentation> {

	@Override
	protected void doAssembleDtoFromAggregate(ActivationRepresentation targetDto, Activation sourceEntity) {
		
	}

	@Override
	protected void doMergeAggregateWithDto(Activation targetEntity,ActivationRepresentation sourceDto) {
		String customerId = sourceDto.getCustomerId();
		targetEntity.setCustomerId(new CustomerId(customerId));
	}
}
