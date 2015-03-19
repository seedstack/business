/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.infrastructure.identity;

import java.util.concurrent.atomic.AtomicLong;

/**
 * An in memory sequence.
 */
public class InMemorySequence {

	private AtomicLong sequence;

    /**
     * Constructor.
     */
	public InMemorySequence() {
		super();
		this.sequence = new AtomicLong(1L);
	}

	/**
	 * Gets the sequence.
	 * 
	 * @return the sequence
	 */
	public Long getSequence() {
		return sequence.get();
	}

    /**
     * Gets the next value.
     *
     * @return the a sequence
     */
	public Long next(){
		return sequence.incrementAndGet();
	}
}