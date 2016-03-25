/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.modelmapper;

import org.modelmapper.ModelMapper;
import org.seedstack.business.assembler.AbstractBaseAssembler;
import org.seedstack.business.domain.AggregateRoot;

/**
 * This assembler automatically assembles aggregates in DTO and vice versa.
 *
 * @param <A> the aggregate root
 * @param <D> the dto
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public abstract class ModelMapperAssembler<A extends AggregateRoot<?>, D> extends AbstractBaseAssembler<A, D> {
    private ModelMapper assembleModelMapper;
    private ModelMapper mergeModelMapper;

    public ModelMapperAssembler() {
        super();
        initModelMappers();
    }

    public ModelMapperAssembler(Class<D> dtoClass) {
        super(dtoClass);
        initModelMappers();
    }

    @Override
    public D assembleDtoFromAggregate(A sourceAggregate) {
        return assembleModelMapper.map(sourceAggregate, dtoClass);
    }

    @Override
    public void assembleDtoFromAggregate(D sourceDto, A sourceAggregate) {
        assembleModelMapper.map(sourceAggregate, sourceDto);
    }

    @Override
    public void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        mergeModelMapper.map(sourceDto, targetAggregate);
    }

    private void initModelMappers() {
        this.assembleModelMapper = new ModelMapper();
        configureAssembly(assembleModelMapper);

        this.mergeModelMapper = new ModelMapper();
        configureMerge(mergeModelMapper);
    }

    protected abstract void configureAssembly(ModelMapper modelMapper);

    protected abstract void configureMerge(ModelMapper modelMapper);
}
