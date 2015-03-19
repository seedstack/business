/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.domain;

import org.seedstack.business.api.domain.base.BaseEntity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * This class a inherited base for JPA entity.
 *
 * @param <ID> the entity id
 * @Deprecated Entities should not extend classes which depend on the infrastructure. This class will be removed
 * in the next version of the business framework.
 */
@Deprecated
@MappedSuperclass
public abstract class BaseJpaEntity<ID> extends BaseEntity<ID> implements Serializable{

    private static final long serialVersionUID = 706874193109859974L;
}
