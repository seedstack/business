/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import org.seedstack.business.api.interfaces.query.view.page.Page;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class BasePaginationServiceTest {
	
	BasePaginationService basePaginationService;
	
	@Before
	public void init ()
	{
		basePaginationService = new BasePaginationService();
	}

	@Test
	public void testGetLastPage() {
		
		Page page = new Page(2,15,15,4996);
		
		Page lastPage = basePaginationService.getLastPage(page);
		
		assertThat(lastPage.getIndex()).isEqualTo(333); // page 334
		assertThat(lastPage.getCapacity()).isEqualTo(15); 
		assertThat(lastPage.getNumberOfElements()).isEqualTo(1); // last page contains only 1 elements
		
		
		
		
	}

}
