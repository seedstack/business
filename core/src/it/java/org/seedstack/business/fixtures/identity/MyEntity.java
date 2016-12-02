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
package org.seedstack.business.fixtures.identity;

import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.BaseEntity;
import org.seedstack.business.domain.identity.UUIDHandler;

import java.util.UUID;


public class MyEntity extends BaseEntity<UUID> {

	@Identity(handler = UUIDHandler.class)
	private UUID id;
	
	@Override
	public UUID getEntityId() {
		return id;
	}

	
}
