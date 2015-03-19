/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.sample.application.internal;

import org.seedstack.business.sample.application.IndexService;
import org.seedstack.business.sample.domain.customer.Customer;
import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

public class IndexServiceInternal implements IndexService {

	@Logging
	Logger logger;
	
	public IndexServiceInternal() {
	}

	@Override
	public void index(Customer customer) {
		logger.info("Indexing " + customer.getEntityId());
//		logger.info("Indexing " + customer.getEntityId().getValue());
	}

}
