/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.interfaces.multi;

import org.seedstack.business.api.interfaces.assembler.BaseAssembler;
import org.seedstack.business.sample.domain.multi.Multi;

public class MultiAssembler extends BaseAssembler<Multi,MultiRepresentation> {

	@Override
	protected void doAssembleDtoFromAggregate(MultiRepresentation targetDto, Multi sourceAggregate) {
	}

	@Override
	protected void doMergeAggregateWithDto(Multi targetAggregate, MultiRepresentation sourceDto) {
	}
}
