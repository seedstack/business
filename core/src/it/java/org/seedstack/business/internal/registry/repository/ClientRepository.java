/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.registry.repository;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.BaseRepository;
import org.seedstack.business.spi.GenericImplementation;

/**
 * Dummy class.
 */
@GenericImplementation
@JpaQualifier
public class ClientRepository<A extends AggregateRoot<K>, K> extends BaseRepository<A, K>{

	@Override
	public A load(K id) {
		return null;
	}

	@Override
	public boolean exists(K id) {
		return false;
	}

	@Override
	public long count() {
		return 0L;
	}

	@Override
	public void clear() {
	}

	@Override
	public void delete(K id) {
	}

	@Override
	public void delete(A aggregate) {
	}

	@Override
	public void persist(A aggregate) {
	}

	@Override
	public A save(A aggregate) {
		return null;
	}

}
