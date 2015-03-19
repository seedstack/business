/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.api.domain.identity;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.api.domain.Entity;
import org.seedstack.business.api.domain.base.BaseEntity;

/**
 * MyIdentityHandler
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class RandomIdentityHandler implements IdentityHandler<BaseEntity<Double>, Double>{

	/**
	 * {@inheritDoc}
	 * 
	 * @see IdentityHandler#handle(Entity, Configuration)
     * @param entity
     * @param entityConfiguration
	 */
	@Override
	public Double handle(Entity entity,
			Configuration entityConfiguration) {
		return Math.random();
	}

}
