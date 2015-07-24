/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler;

import org.seedstack.business.api.domain.AggregateRoot;
import org.seedstack.seed.core.utils.SeedReflectionUtils;

import java.lang.reflect.ParameterizedType;

/**
 * This class is the class to be extended by the users in order to create an Assembler.
 * <p>
 * User must implements
 * {@link BaseAssembler#doAssembleDtoFromAggregate(Object, org.seedstack.business.api.domain.AggregateRoot)} and
 * {@link BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.api.domain.AggregateRoot, Object)} to provide
 * implementation of the copy.
 * </p>
 * For instance:
 * <pre>
 * public class ProductAssembler extends BaseAssembler&lt;Product,ProductRepresentation&gt; {
 *
 *     {@literal @}Override
 *     protected void doAssembleDtoFromAggregate(ProductRepresentation targetDto, Product sourceAggregate) {
 *     	  targetDto.fillProductId(sourceAggregate.getEntityId().getStoreId(),
 *     	      sourceAggregate.getEntityId().getProductCode());
 *     	  targetDto.setName(sourceAggregate.getName());
 *     	  targetDto.setDescription(sourceAggregate.getDescription());
 *     }
 *
 *     {@literal @}Override
 *     protected void doMergeAggregateWithDto(Product targetAggregate, ProductRepresentation sourceDto) {
 *     	  targetAggregate.setName(sourceDto.getName());
 *     	  targetAggregate.setDescription(sourceDto.getDescription());
 *     }
 * }
 * </pre>
 * Then the assembler can be used via:
 * <pre>
 * {@literal @}Inject
 * ProductAssembler productAssembler;
 * </pre>
 * And used like this:
 * <pre>
 * ProductRepresentation productRepresentation = productAssembler.assembleDtoFromAggregate(productFromRepo);
 * </pre>
 * or
 * <pre>
 * productAssembler.mergeAggregateWithDto(productToMerge, productRepresentationSource);
 * </pre>
 *
 * @param <A> the aggregate root type
 * @param <D> the dto type
 * @author epo.jemba@ext.mpsa.com
 */
public abstract class BaseAssembler<A extends AggregateRoot<?>, D>
        extends AbstractBaseAssembler<A, D> {

    /**
     * Default needed constructor.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public BaseAssembler() {
        Class<? extends BaseAssembler> class1 = (Class<? extends BaseAssembler>) SeedReflectionUtils.cleanProxy(getClass());
        dtoClass = (Class<D>) ((ParameterizedType) class1.getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * This method is used by developers or by the DSL to assemble a new DTO from the given aggregate.
     * <ul>
     * <li>It calls {@link AbstractBaseAssembler#newDto()} for the DTO creation
     * <li>and {@link #doAssembleDtoFromAggregate(Object, org.seedstack.business.api.domain.AggregateRoot)}
     * for the assembly algorithm.
     * </ul>
     *
     * @param sourceAggregate The aggregate from which create the DTO.
     * @return the assembled DTO
     * @see Assembler#assembleDtoFromAggregate(Object)
     */
    @Override
    public D assembleDtoFromAggregate(A sourceAggregate) {
        D newDto = newDto();
        doAssembleDtoFromAggregate(newDto, sourceAggregate);

        return newDto;
    }

    @Override
    public void assembleDtoFromAggregate(D sourceDto, A sourceAggregate) {
        doAssembleDtoFromAggregate(sourceDto, sourceAggregate);
    }

    /**
     * This method has to be overridden by users to actually assemble the DTO from the aggregate.
     * <pre>
     * targetDto.fillProductId(sourceAggregate.getEntityId().getStoreId(),
     *     sourceAggregate.getEntityId().getProductCode());
     * targetDto.setName(sourceAggregate.getName());
     * targetDto.setDescription(sourceAggregate.getDescription());
     * </pre>
     * This method will be called by the public method
     * {@link #assembleDtoFromAggregate(org.seedstack.business.api.domain.AggregateRoot)}
     *
     * @param targetDto       the target dto
     * @param sourceAggregate the source aggregate
     */
    protected abstract void doAssembleDtoFromAggregate(D targetDto, A sourceAggregate);

    /**
     * This method is used by developers or by the DSL to actually merge the aggregate.
     * <p>
     * It will call {@link #doMergeAggregateWithDto(org.seedstack.business.api.domain.AggregateRoot, Object)}, which
     * is overridden by developers.
     * </p>
     *
     * @param targetAggregate the target aggregate
     * @param sourceDto       the source dto
     * @see Assembler#mergeAggregateWithDto(Object, Object)
     */
    @Override
    public void mergeAggregateWithDto(A targetAggregate, D sourceDto) {
        doMergeAggregateWithDto(targetAggregate, sourceDto);
    }

    /**
     * This method has to be overridden by users to actually merge an aggregate with the DTO.
     * <pre>
     * ...
     * targetAggregate.setName(sourceDto.getName());
     * targetAggregate.setDescription(sourceDto.getDescription());
     * ...
     * </pre>
     * This method will be called by the public method
     * {@link #mergeAggregateWithDto(org.seedstack.business.api.domain.AggregateRoot, Object)}.
     *
     * @param sourceDto       the source dto
     * @param targetAggregate the target aggregate
     */
    protected abstract void doMergeAggregateWithDto(A targetAggregate, D sourceDto);

}
