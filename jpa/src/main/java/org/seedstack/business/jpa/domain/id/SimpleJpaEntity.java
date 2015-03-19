/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.domain.id;

import org.seedstack.business.jpa.domain.BaseJpaEntity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class a inherited base for JPA entity with simple id (ie. not an embedded id).
 *
 * @param <ID> the entity id
 * @author epo.jemba@ext.mpsa.com
 * @Deprecated Entities should not extend classes which depend on the infrastructure. This class will be removed
 * in the next version of the business framework.
 */
@Deprecated
@MappedSuperclass
public abstract class SimpleJpaEntity<ID> extends BaseJpaEntity<ID> {

    private static final long serialVersionUID = 5823856214748983615L;

	@Id
	protected ID entityId;

	public SimpleJpaEntity() {
	}

	@Override
	public ID getEntityId() {
		return entityId;
	}

}
