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
import org.seedstack.business.api.interfaces.query.view.page.PaginationService;



/**
 *
 * @author epo.jemba@ext.mpsa.com
 */
public class BasePaginationService implements PaginationService{

	@Override
	public Page getLastPage(Page page) {
		long totalNumberOfElements = page.getTotalNumberOfElements();
		
		int pageCapacity = page.getCapacity();
		
		int numOfPage = (int) Math.ceil( (double)totalNumberOfElements / (double)pageCapacity );
		
		int lastIndex = numOfPage - 1;
		int numberOfElements = (int)(totalNumberOfElements % pageCapacity);
		return new Page(lastIndex,pageCapacity,numberOfElements,totalNumberOfElements);
	}

}
