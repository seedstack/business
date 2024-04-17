/*
 * Copyright © 2013-2024, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.specification.dsl;

/**
 * An element of the {@link SpecificationBuilder} DSL to select all or a part of the object a clause
 * will apply to.
 *
 * @param <T> the type of the object the specification applies to.
 * @param <S> the type of the selector.
 */
public interface BaseSelector<T, S extends BaseSelector<T, S>> {

    /**
     * Define a specification satisfied by any candidate.
     *
     * @return the terminal operation of the builder DSL, allowing to build the final specification.
     */
    TerminalOperation<T> all();

    /**
     * Define a specification NOT satisfied by any candidate.
     *
     * @return the terminal operation of the builder DSL, allowing to build the final specification.
     */
    TerminalOperation<T> none();

    /**
     * Selects the whole object to be the subject of a specification. For instance, if an equality
     * specification is chosen later in the DSL, the equality check will apply at the global object
     * level.
     *
     * @return the next operation of the builder DSL, allowing to choose the specification that will
     * apply on the whole object.
     */
    SpecificationPicker<T, S> whole();
}
