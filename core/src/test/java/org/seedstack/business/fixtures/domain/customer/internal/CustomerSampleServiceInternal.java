/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.customer.internal;

import org.seedstack.business.fixtures.domain.customer.Customer;
import org.seedstack.business.fixtures.domain.customer.CustomerSampleService;
import org.seedstack.seed.core.api.Configuration;
import org.seedstack.seed.core.api.Logging;
import org.slf4j.Logger;

public class CustomerSampleServiceInternal implements CustomerSampleService {

	@Logging
	Logger logger;
	
	@Configuration("org.seedstack.toto")
	String property;
	
	@Override
	public String property ()
	{
		return property;
	}
	
	public CustomerSampleServiceInternal() {
	}

	@Override
	public String transfer(Customer source, Customer target) {
		logger.info("Transfering customer " + source.getEntityId() + " to customer " + target.getEntityId());
		return property;
	}

}

