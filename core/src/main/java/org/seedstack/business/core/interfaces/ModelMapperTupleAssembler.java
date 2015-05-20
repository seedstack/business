/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.core.interfaces;

import net.jodah.typetools.TypeResolver;
import org.javatuples.Tuple;
import org.modelmapper.ModelMapper;
import org.seedstack.business.api.interfaces.assembler.AbstractBaseAssembler;
import org.seedstack.business.api.interfaces.assembler.BaseTupleAssembler;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

/**
 * This assembler automatically assembles aggregates in DTO and vice versa.
 *
 * @param <T> the tuple
 * @param <D> the dto
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public abstract class ModelMapperTupleAssembler<T extends Tuple, D> extends AbstractBaseAssembler<T, D> {

    protected Class<D> dtoClass;
    private ModelMapper assembleModelMapper;
    private ModelMapper mergeModelMapper;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ModelMapperTupleAssembler() {
        assembleModelMapper = configureAssembly();
        mergeModelMapper = configureMerge();
        // TODO <pith> : check modelmappers are not null

        Class<? extends BaseTupleAssembler> class1 = (Class<? extends BaseTupleAssembler>) SeedReflectionUtils.cleanProxy(getClass());
        dtoClass = (Class<D>) TypeResolver.resolveRawArguments(class1.getGenericSuperclass(), class1)[1];
    }

    public ModelMapperTupleAssembler(Class<D> dtoClass) {
        this.dtoClass = dtoClass;

        assembleModelMapper = configureAssembly();
        mergeModelMapper = configureMerge();
    }

    @Override
    public D assembleDtoFromAggregate(T sourceAggregate) {
        D sourceDto = null;

        for (Object o : sourceAggregate) {
            if (sourceDto == null) {
                sourceDto = assembleModelMapper.map(o, dtoClass);
            }
            assembleModelMapper.map(o, sourceDto);

        }
        return sourceDto;
    }

    @Override
    public void updateDtoFromAggregate(D sourceDto, T sourceAggregate) {
        for (Object o : sourceAggregate) {
            assembleModelMapper.map(o, sourceDto);
        }
    }

    @Override
    public void mergeAggregateWithDto(T targetAggregate, D sourceDto) {
        for (Object o : targetAggregate) {
            mergeModelMapper.map(sourceDto, o);
        }
    }

    protected abstract ModelMapper configureAssembly();

    protected abstract ModelMapper configureMerge();
}
