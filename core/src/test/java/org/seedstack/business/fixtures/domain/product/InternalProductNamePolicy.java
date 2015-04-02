/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.domain.product;

import java.text.MessageFormat;


public class InternalProductNamePolicy implements ProductNamePolicy {

	private static final String EAN13 = "ean13-";

	public InternalProductNamePolicy() {
	}

	@Override
	public String transform(Short productCode) {
		return MessageFormat.format("{0}{1}", EAN13, productCode);
	}
	
	@Override
	public Short extractNumber(String productCode) {
		return   Short.valueOf( productCode.split("-")[1] );
	}

}
