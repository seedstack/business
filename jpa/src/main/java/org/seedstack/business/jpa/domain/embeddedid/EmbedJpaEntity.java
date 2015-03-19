/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.domain.embeddedid;

import org.seedstack.business.jpa.domain.BaseJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

/**
 * 
 * 
 * @author epo.jemba@ext.mpsa.com
 *
 * @param <ID> ID has to be annotated with @Embeddable and Serializable.
 * @Deprecated Entities should not extend classes which depend on the infrastructure. This class will be removed
 * in the next version of the business framework.
 */
@Deprecated
@MappedSuperclass
public abstract class EmbedJpaEntity<ID> extends BaseJpaEntity<ID> {

    private static final long serialVersionUID = 5823856214748983615L;

	@EmbeddedId
	protected ID entityId;

	public EmbedJpaEntity() {
	}

	@Override
	public ID getEntityId() {
		return entityId;
	}
}
