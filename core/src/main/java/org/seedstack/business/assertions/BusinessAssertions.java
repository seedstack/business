/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assertions;

/**
 * This class provides assertions for business core.
 *
 * @author epo.jemba@ext.mpsa.com
 */
public final class BusinessAssertions {

	private BusinessAssertions() {
	}
	
	/**
	 * @param actual class to check
	 * @return the actual Assert
	 */
	public static BusinessClassAssert assertThat(Class<?> actual) {
		return new BusinessClassAssert(actual);
	}
}