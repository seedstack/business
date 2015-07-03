/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.order;

import org.seedstack.business.api.domain.BaseValueObject;


public class OrderId extends BaseValueObject {

	
	private static final long serialVersionUID = 8814444144705792621L;
	
	
	private String value;
    
     OrderId()
    {
    }
	
	public OrderId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
