/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.spi;

import io.nuun.kernel.api.annotations.Facet;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.domain.ValueObject;

import java.util.Collection;

@Facet
public interface DomainProvider {
    /**
     * @return the aggregate roots classes of the domain.
     */
    Collection<Class<? extends AggregateRoot<?>>> aggregateRoots();

    /**
     * @return the value object classes of the domain.
     */
    Collection<Class<? extends ValueObject>> valueObjects();
}
