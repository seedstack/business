/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.pagination.Page;

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
