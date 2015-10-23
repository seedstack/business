/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.registry.repository;

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.BaseRepository;
import org.seedstack.business.spi.GenericImplementation;

/**
 * Dummy class.
 * @author thierry.bouvet@mpsa.com
 *
 */
@GenericImplementation
@JpaQualifier
public class ClientRepository<A extends AggregateRoot<K>, K> extends BaseRepository<A, K>{

	@Override
	protected A doLoad(K id) {
		return null;
	}

	@Override
	protected void doDelete(K id) {
	}

	@Override
	protected void doDelete(A aggregate) {
	}

	@Override
	protected void doPersist(A aggregate) {
	}

	@Override
	protected A doSave(A aggregate) {
		return null;
	}

}
