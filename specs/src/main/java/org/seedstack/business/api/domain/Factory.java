/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.domain;

import org.seedstack.business.api.Producible;
import org.seedstack.business.api.domain.annotations.stereotypes.Create;

/**
 * Factory allows creation of {@link DomainObject} that are {@link org.seedstack.business.api.Producible} object.
 * 
 * @author redouane.loulou@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 * @param <DO> Created {@link DomainObject} type.
 * 
 */
public interface Factory<DO extends DomainObject & Producible> extends GenericFactory<DO> {

	/**
	 * creates a domain object.
	 * @param args arguments
	 * @return an instance of DomainObject
	 */
	@Create
	DO create(Object... args);
	
}