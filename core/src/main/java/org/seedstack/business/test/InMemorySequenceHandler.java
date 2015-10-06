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
package org.seedstack.business.test;

import org.apache.commons.configuration.Configuration;
import org.seedstack.business.api.domain.Entity;
import org.seedstack.business.api.domain.BaseEntity;
import org.seedstack.business.api.domain.identity.SequenceHandler;

import javax.inject.Named;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author redouane.loulou@ext.mpsa.com
 */
@Named("inmemory-sequence")
public class InMemorySequenceHandler implements SequenceHandler<BaseEntity<Long>, Long> {
	private static final AtomicLong sequence = new AtomicLong(1L);

	@Override
	public Long handle(Entity entity, Configuration entityConfiguration) {
		return sequence.incrementAndGet();
	}

}
