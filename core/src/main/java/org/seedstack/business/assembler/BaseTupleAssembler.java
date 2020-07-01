/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler;

import org.javatuples.Tuple;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.shed.reflect.Classes;

/**
 * An helper base class that can be extended to create an assembler between a tuple of aggregates
 * and a DTO.
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

    @Override
    public void mergeAggregateIntoDto(T sourceAggregate, D targetDto) {
        throw BusinessException.createNew(BusinessErrorCode.ASSEMBLE_NOT_IMPLEMENTED)
                .put("className", this.dtoClass.getName());

    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, T targetAggregate) {
        throw BusinessException.createNew(BusinessErrorCode.MERGE_NOT_IMPLEMENTED)
                .put("className", getClass().getName());

    }

    @Override
    public Class<D> getDtoClass() {
        return this.dtoClass;
    }

    @Override
    public D createDto() {
        return Classes.instantiateDefault(getDtoClass());
    }
}
