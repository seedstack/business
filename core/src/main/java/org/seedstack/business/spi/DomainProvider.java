/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.spi;

import io.nuun.kernel.api.annotations.Facet;
import java.util.Collection;

/**
 * Plugin facet that can be used to retrieve domain objects.
 */
@Facet
public interface DomainProvider {
    /**
     * Returns a collection of all aggregate root classes.
     *
     * @return the aggregate root class collection.
     */
    Collection<Class<?>> aggregateRoots();

    /**
     * Returns a collection of all entity classes (including aggregate roots).
     *
     * @return the entity class collection.
     */
    Collection<Class<?>> entities();

    /**
     * Returns a collection of all value object classes.
     *
     * @return the value object class collection.
     */
    Collection<Class<?>> valueObjects();

}
