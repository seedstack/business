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
package org.seedstack.business.identity.fixtures;

import org.seedstack.business.api.domain.Identity;
import org.seedstack.business.api.domain.BaseEntity;
import org.seedstack.business.api.domain.identity.UUIDHandler;

import java.util.UUID;

/**
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class MyEntity extends BaseEntity<UUID> {

	@Identity(handler = UUIDHandler.class)
	private UUID id;
	
	@Override
	public UUID getEntityId() {
		return id;
	}

	
}
