/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.samples.domain.embed;

import org.seedstack.business.api.domain.BaseValueObject;

import javax.persistence.Embeddable;

/**
 *
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 */

@Embeddable
public class SampleEmbeddedKey extends BaseValueObject {
	
	private Long keyValue;

	SampleEmbeddedKey() {
	}
	
	public SampleEmbeddedKey(Long keyValue) {
		this.keyValue = keyValue;
	}
	public Long getKeyValue() {
		return keyValue;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
