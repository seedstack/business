/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

/**
 * An element of the {@link SpecificationBuilder} DSL to pick the options for a {@link String}-specific specification.
 *
 * @param <T>        the type of the object the specification applies to.
 * @param <SELECTOR> the type of the selector.
 */
public interface StringOptionPicker<T, SELECTOR extends BaseSelector> extends OperatorPicker<T, SELECTOR> {
    /**
     * Specifies that the value should be leading and trailing trimmed before comparison.
     *
     * @return the next operation of the builder DSL, allowing to specify more options or compose a new specification.
     */
    StringOptionPicker<T, SELECTOR> trimming();

    /**
     * Specifies that the value should be leading-trimmed before comparison.
     *
     * @return the next operation of the builder DSL, allowing to specify more options or compose a new specification.
     */
    StringOptionPicker<T, SELECTOR> trimmingLead();

    /**
     * Specifies that the value should be trailing-trimmed before comparison.
     *
     * @return the next operation of the builder DSL, allowing to specify more options or compose a new specification.
     */
    StringOptionPicker<T, SELECTOR> trimmingTrail();

    /**
     * Specifies that the comparison should be done ignoring case.
     *
     * @return the next operation of the builder DSL, allowing to specify more options or compose a new specification.
     */
    StringOptionPicker<T, SELECTOR> ignoringCase();
}
