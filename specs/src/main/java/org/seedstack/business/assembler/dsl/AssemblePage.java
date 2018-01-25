/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler.dsl;

import org.seedstack.business.pagination.Page;

/**
 * An element of the {@link FluentAssembler} DSL allowing to assemble to a {@link Page} of multiple
 * DTO. This is only available in the DSL if a page of aggregates has been specified as input.
 */
public interface AssemblePage extends AssembleMultiple {

    /**
     * Assembles to a {@link Page} of DTO.
     *
     * @param <D>      the type of the DTO.
     * @param dtoClass the DTO class to assemble.
     * @return the page of DTO.
     */
    <D> Page<D> toPageOf(Class<D> dtoClass);
}
