/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.modelmapper;

import org.modelmapper.ModelMapper;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.domain.AggregateRoot;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This assembler automatically assembles aggregates in DTO and vice versa.
 *
 * @param <A> the aggregate root
 * @param <D> the dto
 */
public abstract class ModelMapperAssembler<A extends AggregateRoot<?>, D> extends BaseAssembler<A, D> {
    private final AtomicBoolean assembleConfigured = new AtomicBoolean(false);
    private final AtomicBoolean mergeConfigured = new AtomicBoolean(false);
    private final ModelMapper assembleModelMapper = new ModelMapper();
    private final ModelMapper mergeModelMapper = new ModelMapper();

    public ModelMapperAssembler() {
        super();
    }

    protected ModelMapperAssembler(Class<D> dtoClass) {
        super(dtoClass);
    }

    @Override
    public D createDtoFromAggregate(A sourceAggregate) {
        if (!assembleConfigured.getAndSet(true)) {
            configureModelMapper(assembleModelMapper);
            configureAssembly(assembleModelMapper);
        }
        return assembleModelMapper.map(sourceAggregate, getDtoClass());
    }

    @Override
    public void mergeAggregateIntoDto(A sourceAggregate, D targetDto) {
        if (!assembleConfigured.getAndSet(true)) {
            configureModelMapper(assembleModelMapper);
            configureAssembly(assembleModelMapper);
        }
        assembleModelMapper.map(sourceAggregate, targetDto);
    }

    @Override
    public void mergeDtoIntoAggregate(D sourceDto, A targetAggregate) {
        if (!mergeConfigured.getAndSet(true)) {
            configureModelMapper(mergeModelMapper);
            configureMerge(mergeModelMapper);
        }
        mergeModelMapper.map(sourceDto, targetAggregate);
    }

    private void configureModelMapper(ModelMapper assembleModelMapper) {

    }

    protected abstract void configureAssembly(ModelMapper modelMapper);

    protected abstract void configureMerge(ModelMapper modelMapper);
}
