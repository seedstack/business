/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.internal.utils.BusinessUtils;

public abstract class BaseDomainEventHandler<E extends DomainEvent> implements DomainEventHandler<E> {
    private static final int EVENT_INDEX = 0;
    private final Class<E> eventClass;

    @SuppressWarnings("unchecked")
    public BaseDomainEventHandler() {
        this.eventClass = (Class<E>) BusinessUtils.resolveGenerics(DomainEventHandler.class, getClass())[EVENT_INDEX];
    }

    @Override
    public Class<E> getEventClass() {
        return this.eventClass;
    }
}
