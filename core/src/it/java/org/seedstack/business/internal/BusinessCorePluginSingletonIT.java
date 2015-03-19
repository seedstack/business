/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal;

import org.seedstack.business.sample.application.IndexService;
import org.seedstack.business.sample.application.SingletonService;
import org.seedstack.business.sample.domain.activation.ActivationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;

import javax.inject.Inject;

/**
 * This class check the validation feature of the business suport
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */

public class BusinessCorePluginSingletonIT extends AbstractSeedIT
{

	
	@Inject
	SingletonService singletonService;
	
	@Inject
	SingletonService singletonService2;
	
	
	@Inject
	IndexService indexService;
	
	@Inject
	IndexService indexService2;
	
	
	@Test
	public void singleton_injection_should_work () throws ActivationException
	{
		Assertions.assertThat(singletonService).isEqualTo(singletonService2);
	}

	@Test
	public void default_scope_injection_should_work () throws ActivationException
	{
		Assertions.assertThat(indexService).isNotEqualTo(indexService2);
	}
	
	

}
