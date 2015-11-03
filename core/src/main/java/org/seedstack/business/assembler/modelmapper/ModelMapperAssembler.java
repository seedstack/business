/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assembler.modelmapper;

import net.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.seedstack.business.assembler.AbstractBaseAssembler;
import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.business.domain.AggregateRoot;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

/**
 * This assembler automatically assembles aggregates in DTO and vice versa.
 *
 * @param <A> the aggregate root
 * @param <D> the dto
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public abstract class ModelMapperAssembler<A extends AggregateRoot<?>, D> extends AbstractBaseAssembler<A, D> {

    protected Class<D> dtoClass;
    private ModelMapper assembleModelMapper;
    private ModelMapper mergeModelMapper;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ModelMapperAssembler() {
        initModelMappers();
        Class<? extends BaseAssembler> class1 = (Class<? extends BaseAssembler>) SeedReflectionUtils.cleanProxy(getClass());
        dtoClass = (Class<D>) TypeResolver.resolveRawArguments(class1.getGenericSuperclass(), class1)[1];
    }

    public ModelMapperAssembler(Class<D> dtoClass) {
        initModelMappers();
        this.dtoClass = dtoClass;
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

    void initModelMappers() {
        this.assembleModelMapper = new ModelMapper();
        configureAssembly(assembleModelMapper);

        this.mergeModelMapper = new ModelMapper();
        configureMerge(mergeModelMapper);
    }

    protected abstract void configureAssembly(ModelMapper modelMapper);

    protected abstract void configureMerge(ModelMapper modelMapper);
}
