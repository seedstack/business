/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

/**
 * An element of the {@link FluentAssembler} DSL allowing to assemble to a single DTO.
 */
public interface AssembleSingle {

    /**
     * Assembles to a DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the DTO.
     */
    <D> D to(Class<D> dtoClass);
}
