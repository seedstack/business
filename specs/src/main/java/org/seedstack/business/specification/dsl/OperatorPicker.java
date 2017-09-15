/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

/**
 * An element of the {@link SpecificationBuilder} DSL to compose the current specification with another one.
 *
 * @param <T>        the type of the object the specification applies to.
 * @param <SELECTOR> the type of the selector.
 */
public interface OperatorPicker<T, SELECTOR extends BaseSelector> extends TerminalOperation<T> {
    /**
     * Compose the current specification with a new one using a logical AND.
     *
     * @return the next operation of the builder DSL, allowing to further compose a new specification.
     */
    SELECTOR and();

    /**
     * Compose the current specification with a new one using a logical OR.
     *
     * @return the next operation of the builder DSL, allowing to further compose a new specification.
     */
    SELECTOR or();
}
