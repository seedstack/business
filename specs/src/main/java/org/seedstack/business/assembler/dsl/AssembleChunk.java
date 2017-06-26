/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.dsl;

import org.seedstack.business.pagination.Chunk;

public interface AssembleChunk {

    /**
     * Returns a Chunk with a list of dtos.
     *
     * @param dtoClass the dto class to assemble
     * @param <D>      the dto type
     * @return the Chunk containing the list of dtos
     */
    <D> Chunk<D> to(Class<D> dtoClass);
}
