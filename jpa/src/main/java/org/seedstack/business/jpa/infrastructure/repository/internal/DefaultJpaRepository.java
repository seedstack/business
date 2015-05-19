/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.jpa.infrastructure.repository.internal;

import com.google.inject.assistedinject.Assisted;
import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.business.api.domain.annotations.DomainRepositoryImpl;
import org.seedstack.business.jpa.infrastructure.repository.BaseJpaRepository;
import org.seedstack.seed.core.utils.SeedCheckUtils;

import javax.inject.Inject;

/**
 * Default Jpa implementation for Repository. Used only when no implementation is provided for an aggregate.
 *
 * To inject this implementation you have to use {@link org.seedstack.business.api.domain.Repository} as follows:
 * <pre>
 * {@literal @}Inject
 * Repository{@literal <MyAggregateRoot, MyKey>} myAggregateRepository;
 * </pre>
 *
 * @param <AGGREGATE> the aggregate root
 * @param <KEY>       the aggregate key
 * @author pierre.thirouin@ext.mpsa.com

 * @see org.seedstack.business.api.domain.Repository
 * @see org.seedstack.business.jpa.infrastructure.repository.BaseJpaRepository
 */
@DomainRepositoryImpl
public class DefaultJpaRepository<AGGREGATE extends AggregateRoot<KEY>, KEY> extends BaseJpaRepository<AGGREGATE, KEY> {

    /**
     * Constructs a DefaultJpaRepository.
     *
     * @param genericClasses the resolved generics for the aggregate root class and the key class
     */
    @SuppressWarnings("unchecked")
    @Inject
    public DefaultJpaRepository(@Assisted Object[] genericClasses) {
        Object[] clonedClasses = genericClasses.clone();
        SeedCheckUtils.checkIfNotNull(clonedClasses);
        SeedCheckUtils.checkIf(clonedClasses.length == 2);
        this.aggregateRootClass = (Class) clonedClasses[0];
        this.keyClass = (Class) clonedClasses[1];
    }
}
