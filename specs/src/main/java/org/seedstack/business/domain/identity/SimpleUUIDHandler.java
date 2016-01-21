/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * 
 */
package org.seedstack.business.domain.identity;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.domain.Entity;
import org.seedstack.business.domain.BaseEntity;

import javax.inject.Named;
import java.util.UUID;

/**
 * Uuid handler
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
@Named("simple-UUID")
public class SimpleUUIDHandler implements UUIDHandler<BaseEntity<UUID>,UUID>{

	@Override
	public UUID handle(Entity entity, Configuration entityConfiguration) {
		return UUID.randomUUID();		
	}

}
