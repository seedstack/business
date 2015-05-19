/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.assembler.dsl;


import org.javatuples.Tuple;
import org.seedstack.business.api.domain.AggregateRoot;

import java.util.List;

/**
 * A part of the assembler DSL defining the source object(s) used to assemble.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public interface Assemble {

    /**
     * Assembles a list of dtos.
     *
     * @param dto the list of dtos
     * @return an AggsAssemblerProvider
     */
    <D> AggsAssemblerProvider<D> dtos(List<D> dto);

    /**
     * Assembles a dto.
     *
     * @param dto the dto
     * @return an AggAssemblerProvider
     */
    <D> AggAssemblerProvider<D> dto(D dto);

    /**
     * Assembles an aggregate root.
     *
     * @param aggregateRoot the aggregate root
     * @return a DtoAssemblerProvider
     */
    DtoAssemblerProvider aggregate(AggregateRoot<?> aggregateRoot);

    /**
     * Assembles a list of aggregates.
     *
     * @param aggregateRoots the list of aggregate roots
     * @return a DtosAssemblerProvider
     */
    DtosAssemblerProvider aggregates(List<? extends AggregateRoot<?>> aggregateRoots);

    /**
     * Assembles a tuple of aggregates.
     *
     * @param aggregateRoots the tuple of aggregate roots
     * @return a DtoAssemblerProvider
     */
    DtoAssemblerProvider tuple(Tuple aggregateRoots);

    /**
     * Assembles a list of tuple of aggregates.
     *
     * @param aggregateRoots the list of tuple of aggregate roots
     * @return a DtosAssemblerProvider
     */
    DtosAssemblerProvider tuples(List<? extends Tuple> aggregateRoots);

}
