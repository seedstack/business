/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;


import org.javatuples.Tuple;
import org.seedstack.business.internal.utils.BusinessUtils;

/**
 * This base class can be extended to create an assembler between a tuple of aggregates and a DTO.
 *
 * @param <T> the tuple of aggregates type.
 * @param <D> the dto type.
 */
public abstract class BaseTupleAssembler<T extends Tuple, D> implements Assembler<T, D> {
    private final Class<D> dtoClass;

    /**
     * Creates an assembler with automatic resolution of its DTO class.
     */
    @SuppressWarnings("unchecked")
    public BaseTupleAssembler() {
        this.dtoClass = (Class<D>) BusinessUtils.resolveGenerics(BaseTupleAssembler.class, getClass())[1];
    }

    /**
     * Creates an assembler with the DTO class explicitly specified.
     *
     * @param dtoClass the DTO class.
     */
    protected BaseTupleAssembler(Class<D> dtoClass) {
        this.dtoClass = dtoClass;
    }

    /**
     * @return the DTO class this assembler handles.
     */
    @Override
    public Class<D> getDtoClass() {
        return this.dtoClass;
    }
}
