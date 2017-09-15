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
 * An element of the {@link SpecificationBuilder} DSL to specify the identity of an aggregate.
 *
 * @param <A>        the type of the aggregate.
 * @param <ID>       the type of the identifier of the aggregate.
 * @param <SELECTOR> the type of the selector.
 */
public interface IdentityPicker<A extends AggregateRoot<ID>, ID, SELECTOR extends BaseSelector> {
    /**
     * Specify that the identity of the aggregate must be equal to the one passed as argument.
     *
     * @param id the identity.
     * @return the next operation of the builder DSL, allowing to compose the just-defined specification with another one.
     */
    OperatorPicker<A, SELECTOR> is(ID id);

    /**
     * Specify that the identity of the aggregate must NOT be equal to the one passed as argument.
     *
     * @param id the identity.
     * @return the next operation of the builder DSL, allowing to compose the just-defined specification with another one.
     */
    OperatorPicker<A, SELECTOR> isNot(ID id);
}
