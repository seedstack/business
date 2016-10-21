/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.domain;

import org.seedstack.business.Producible;

/**
 * Factory allows creation of {@link DomainObject} that are {@link Producible} object.
 *
 * @param <DO> Created {@link DomainObject} type.
 */
public interface Factory<DO extends DomainObject & Producible> extends GenericFactory<DO> {

    /**
     * Creates a domain object.
     *
     * @param args arguments
     * @return an instance of DomainObject
     */
    DO create(Object... args);

}