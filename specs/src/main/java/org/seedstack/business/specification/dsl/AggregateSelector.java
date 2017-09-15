/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

import org.seedstack.business.domain.AggregateRoot;

/**
 * An element of the {@link SpecificationBuilder} DSL to select the identity of the aggregate a clause will apply to.
 *
 * @param <A>        the type of the aggregate
 * @param <ID>       the type of the identifier of the aggregate.
 * @param <SELECTOR> the type of the selector.
 */
public interface AggregateSelector<A extends AggregateRoot<ID>, ID, SELECTOR extends AggregateSelector<A, ID, SELECTOR>> extends PropertySelector<A, SELECTOR> {
    /**
     * Selects the identity of the aggregate to be the subject of a specification.
     *
     * @return the next operation of the builder DSL, allowing to choose the specification.
     */
    IdentityPicker<A, ID, SELECTOR> identity();
}
