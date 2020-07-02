/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.assembler;

import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.business.internal.BusinessErrorCode;
import org.seedstack.business.internal.BusinessException;
import org.seedstack.business.internal.utils.BusinessUtils;
import org.seedstack.shed.reflect.Classes;

/**
 * An helper base class that can be extended to create an assembler between an aggregate and a DTO.
 *
 * @param <A> the aggregate root type.
 * @param <D> the dto type.
 */
public abstract class BaseAssembler<A extends AggregateRoot<?>, D> implements Assembler<A, D> {

    private final Class<D> dtoClass;

    /**
     * Creates a base assembler. Actual classes handled by the assembler are determined by
     * reflection.
     */
    @SuppressWarnings("unchecked")
    public BaseAssembler() {
        this.dtoClass = (Class<D>) BusinessUtils.resolveGenerics(BaseAssembler.class, getClass())[1];
    }

    /**
     * Creates a base assembler. Actual classes handled by the assembler are specified explicitly.
     * This can be used to create a dynamic implementation of an assembler.
     *
     * @param dtoClass the DTO class.
     */
    protected BaseAssembler(Class<D> dtoClass) {
        this.dtoClass = dtoClass;
    }

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        throw BusinessException.createNew(BusinessErrorCode.ASSEMBLE_NOT_IMPLEMENTED)
                .put("className", this.dtoClass.getName());

    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
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
