/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.domain;

import org.seedstack.business.internal.utils.BusinessUtils;

/**
 * An helper base class that can be extended to create a domain event handler. If extending this
 * base class is not desirable, you can instead implement
 * {@link org.seedstack.business.domain.DomainEventHandler}.
 *
 * @param <E> the type of the handled event.
 */
public abstract class BaseDomainEventHandler<E extends DomainEvent> implements DomainEventHandler<E> {

    private static final int EVENT_INDEX = 0;
    private final Class<E> eventClass;

    /**
     * Creates a base domain event handler. Actual event class is determined by reflection.
     */
    @SuppressWarnings("unchecked")
    protected BaseDomainEventHandler() {
        this.eventClass = (Class<E>) BusinessUtils.resolveGenerics(DomainEventHandler.class, getClass())[EVENT_INDEX];
    }

    /**
     * Creates a base domain event handler. Actual event class is specified explicitly. This can be used to create a
     * dynamic implementation of a domain event handler.
     *
     * @param eventClass the event class.
     */
    protected BaseDomainEventHandler(Class<E> eventClass) {
        this.eventClass = eventClass;
    }

    @Override
    public Class<E> getEventClass() {
        return this.eventClass;
    }
}
